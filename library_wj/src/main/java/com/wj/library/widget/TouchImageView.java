package com.wj.library.widget;

/**
 * Created by idea_wj on 16/5/23.
 * 可以手势缩放的imageview
 *
 * 备注：该控件是网络资源中找到的，计算比较麻烦，自己也没看懂，经过调整，删减了100多行代码，
 * 这控件只能作为基本的缩放、移动使用，
 * 主要是用来更清晰的研究缩放移动的核心原理，其余的需要自己扩充，
 * zoom()方法中的计算过程，我没理解了，要是有童鞋知道这个计算是怎么回事，麻烦请告知一下，感激不尽
 * 该控件使用了观察者模式，很受启发，打算借用这一模式重新实现一个缩放组件（继承ImageView）
 * 欢迎关注我的主页：https://github.com/jason-wj/android-common-library
 */

import android.view.View;


import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TouchImageView extends View implements Observer {
    private static final String TAG = TouchImageView.class.getSimpleName();

    private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG); //抗锯齿画笔
    private final Rect mRectSrc = new Rect();   //要绘制的bitmap 区域(bitmat中,指定地坐标子范围),bitmap左上角为(0,0)
    private final Rect mRectDst = new Rect();   // 代表的是要将bitmap 绘制在屏幕的什么地方
    private Bitmap mBitmap;  //图片源
    private ZoomState mState;   //缩放管理
    private BasicZoomControl mZoomControl;
    private BasicZoomListener mZoomListener;

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mZoomControl = new BasicZoomControl();
        mZoomListener = new BasicZoomListener();
        mZoomListener.setZoomControl(mZoomControl);
        setZoomState(mZoomControl.getZoomState());//将TouchImageView这观察者添加到状态类(被观察者)中
        setOnTouchListener(mZoomListener);
    }

    public void setImage(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    private void setZoomState(ZoomState state) {
        if (mState != null)
            mState.deleteObserver(this);
        mState = state;
        mState.addObserver(this);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mZoomControl.limitPan();   //初始化参数,只有此处初始化有效
        mZoomControl.limitZoom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null && mState != null) {
            final float zoom = mState.getZoom();

            final int bitmapWidth = mBitmap.getWidth(); //该值不变
            final int bitmapHeight = mBitmap.getHeight();

            final float panX = mState.getPanX();
            final float panY = mState.getPanY();

            //panX * bitmapWidth表示图片可视区域的中心
            mRectSrc.left = (int) (panX * bitmapWidth - bitmapWidth / (zoom * 2)); //相对于bitmap的坐标
            mRectSrc.top = (int) (panY * bitmapHeight - bitmapHeight / (zoom * 2));
            mRectSrc.right = (int) (mRectSrc.left + bitmapWidth / zoom);
            mRectSrc.bottom = (int) (mRectSrc.top + bitmapHeight / zoom);

            mRectDst.left = 0;
            mRectDst.top = 0;
            mRectDst.right = getWidth();
            mRectDst.bottom = getHeight();

            canvas.drawBitmap(mBitmap, mRectSrc, mRectDst, mPaint); //第一个Rect 代表要绘制的bitmap 区域，第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        invalidate();
    }

    private class BasicZoomListener implements OnTouchListener {
        private BasicZoomControl mZoomControl;

        private float mFirstX = -1; //第一个触控点坐标
        private float mFirstY = -1;  //第二个触控点坐标
        private float mSecondX = -1;
        private float mSecondY = -1;

        private int mOldCounts = 0;  //记录上一次操作，有几个触控点

        /**
         * 当有多个触控点的时候，此处有Bug,建议只保留两个触控点
         * @param control Zoom control
         */
        public void setZoomControl(BasicZoomControl control) {
            mZoomControl = control;
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mOldCounts = 1;
                    mFirstX = event.getX();
                    mFirstY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE: {
                    float fFirstX = event.getX();
                    float fFirstY = event.getY();

                    int nCounts = event.getPointerCount();

                    if (1 == nCounts) {
                        mOldCounts = 1;
                        float dx = (fFirstX - mFirstX) / v.getWidth(); //dx>0，水平方向右移
                        float dy = (fFirstY - mFirstY) / v.getHeight();
                        mZoomControl.pan(-dx, -dy);
                    } else if (1 == mOldCounts) {
                        mSecondX = event.getX(event.getPointerId(nCounts - 1));
                        mSecondY = event.getY(event.getPointerId(nCounts - 1));
                        mOldCounts = nCounts;
                    } else {
                        float fSecondX = event.getX(event.getPointerId(nCounts - 1));
                        float fSecondY = event.getY(event.getPointerId(nCounts - 1));

                        double nLengthOld = getLength(mFirstX, mFirstY, mSecondX, mSecondY); //计算缩放前，两点之间的距离
                        double nLengthNow = getLength(fFirstX, fFirstY, fSecondX, fSecondY);   //计算缩放后，两点之间的距离

                        float d = (float) ((nLengthNow - nLengthOld) / v.getWidth());

                        mZoomControl.zoom((float) Math.pow(20, d),
                                ((fFirstX + fSecondX) / 2 / v.getWidth()),
                                ((fFirstY + fSecondY) / 2 / v.getHeight()));  //后两个参数大意是缩放的中心点x,y分别与视图的比例

                        mSecondX = fSecondX;
                        mSecondY = fSecondY;
                    }
                    mFirstX = fFirstX;
                    mFirstY = fFirstY;
                    break;
                }
            }
            return true;
        }

        private double getLength(float x1, float y1, float x2, float y2) {
            return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }
    }

    /**
     * 缩放控制类
     */
    private class BasicZoomControl {
        private static final float MIN_ZOOM = 1;  //最小缩放比
        private static final float MAX_ZOOM = 16;  //最大缩放比
        private final ZoomState mState = new ZoomState();  //缩放状态

        public ZoomState getZoomState() {
            return mState;
        }

        /**
         * 缩放操作
         * @param f Factor of zoom to apply
         * @param x X-coordinate of invariant position
         * @param y Y-coordinate of invariant position
         */
        public void zoom(float f, float x, float y) {

            final float preZoom = mState.getZoom();

            mState.setZoom(preZoom * f);
            limitZoom();

            final float newZoom = mState.getZoom();
            //Log.e(TAG,((x - .5f) * (1f / preZoom - 1f / newZoom))+"");

            /**
             * 自己理解：
             * 中心可视位置：代表屏幕的正中间，panX和panY初始就已经设置为0.5了，代表该意思
             * 需要完整的看待公式，完整公式应该是这样（以x方向为例）：
             * (mState.getPanX()* bitmapWidth + (x - .5f) * ((1f / preZoom - 1f / newZoom)* bitmapWidth)) - bitmapWidth / (zoom * 2)
             * 其中：
             * mState.getPanX()* bitmapWidth表示：上一次滑动位置，可视范围的中心，距离bitmap左边界的距离,假设为S
             * (x - .5f) * (1f / preZoom - 1f / newZoom)* bitmapWidth)表示：这次滑动后的总的可视范围的中心与bitmap左边界的距离S1-S的差
             * 所以，当前滑动状态下，中心可视位置距离bitmap左边界的距离为S+S1-S = S1;
             *
             */
            //下面这两行咋计算的？
            mState.setPanX(mState.getPanX() + (x - .5f) * (1f / preZoom - 1f / newZoom));
            mState.setPanY(mState.getPanY() + (y - .5f) * (1f / preZoom - 1f / newZoom));

            limitPan();
            mState.notifyObservers();
        }

        /**
         * 单指移动操作
         * @param dx Amount to pan in x-dimension
         * @param dy Amount to pan in y-dimension
         */
        public void pan(float dx, float dy) {
            final float zoom = mState.getZoom();

            mState.setPanX(mState.getPanX() + dx / zoom);
            mState.setPanY(mState.getPanY() + dy / zoom);
            limitPan();
            mState.notifyObservers();
        }

        /**
         * 从中心最大移动位置
         * @param zoom 缩放值
         * @return
         */
        private float getMaxPanDelta(float zoom) {
            return .5f * ((zoom - 1) / zoom);
        }

        /**
         * 控制缩放值，不要超出限制的范围
         */
        private void limitZoom() {
            if (mState.getZoom() < MIN_ZOOM)
                mState.setZoom(MIN_ZOOM);
            else if (mState.getZoom() > MAX_ZOOM)
                mState.setZoom(MAX_ZOOM);
        }

        /**
         * 平移限制
         */
        private void limitPan() {
            final float zoom = mState.getZoom();

            final float panMinX = .5f - getMaxPanDelta(zoom); //最小最大移动变化幅度,最小值在0~0.5,最大值0.5~1
            final float panMaxX = .5f + getMaxPanDelta(zoom);
            final float panMinY = .5f - getMaxPanDelta(zoom);
            final float panMaxY = .5f + getMaxPanDelta(zoom);

            if (mState.getPanX() < panMinX)
                mState.setPanX(panMinX);
            if (mState.getPanX() > panMaxX)
                mState.setPanX(panMaxX);
            if (mState.getPanY() < panMinY)
                mState.setPanY(panMinY);
            if (mState.getPanY() > panMaxY)
                mState.setPanY(panMaxY);
        }
    }

    /**
     * 被观察者，添加主类TouchImageView观察者
     */
    private class ZoomState extends Observable {

        private float mZoom;  //缩放等级1~16 有小数

        /**
         * x平移
         * Pan position x-coordinate X-coordinate of zoom window center
         * position, relative to the width of the content.
         */
        private float mPanX;

        /**
         * y平移位移，保证平滑
         * Pan position y-coordinate Y-coordinate of zoom window center
         * position, relative to the height of the content.
         */
        private float mPanY;

        /**
         * Get current x-pan
         * @return current x-pan
         */
        public float getPanX() {
            return mPanX;
        }

        /**
         * Get current y-pan
         * @return Current y-pan
         */
        public float getPanY() {
            return mPanY;
        }

        /**
         * Get current zoom value
         * @return Current zoom value
         */
        public float getZoom() {
            return mZoom;
        }

        /**
         * Set pan-x
         * @param panX Pan-x value to set
         */
        public void setPanX(float panX) {
            if (panX != mPanX) {
                mPanX = panX;
                setChanged();
            }
        }

        /**
         * Set pan-y
         * @param panY Pan-y value to set
         */
        public void setPanY(float panY) {
            if (panY != mPanY) {
                mPanY = panY;
                setChanged();
            }
        }

        /**
         * Set zoom
         * @param zoom Zoom value to set
         */
        public void setZoom(float zoom) {
            if (zoom != mZoom) {
                mZoom = zoom;
                setChanged();
            }
        }
    }
}