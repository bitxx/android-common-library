package com.wj.library.widget;

/**
 * Created by idea_wj on 16/5/23.
 * 可以手势缩放的imageview
 *
 * 备注：目前该控件是借助网上已有资源，后期将逐步修改与完善
 * 1.该组件使用了设计模式中的-观察者模式，java中已经提供公共接口
 * 2.该组件核心地方主要是单指滑动，以及缩放时，对坐标点、步长的控制
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
import android.util.Log;
import android.view.MotionEvent;

public class TouchImageView extends View implements Observer {
    private static final String TAG = TouchImageView.class.getSimpleName();

    /**
     * Paint object used when drawing bitmap.
     */
    private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);  //抗锯齿

    /**
     * Rectangle used (and re-used) for cropping source image.
     */
    private final Rect mRectSrc = new Rect();  //图像可视范围内的，该坐标以图像view坐标为准，不是以屏幕坐标为准

    /**
     * Rectangle used (and re-used) for specifying drawing area on canvas.
     */
    private final Rect mRectDst = new Rect();

    /**
     * Object holding aspect quotient
     */
    private final AspectQuotient mAspectQuotient = new AspectQuotient();

    /**
     * The bitmap that we're zooming in, and drawing on the screen.
     */
    private Bitmap mBitmap;

    /**
     * State of the zoom.
     */
    private ZoomState mState;

    private BasicZoomControl mZoomControl;
    private BasicZoomListener mZoomListener;

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mZoomControl = new BasicZoomControl();
        mZoomListener = new BasicZoomListener();
        mZoomListener.setZoomControl(mZoomControl);
        setZoomState(mZoomControl.getZoomState());
        setOnTouchListener(mZoomListener);  //将TouchImageView这观察者添加到状态类(被观察者)中
        mZoomControl.setAspectQuotient(mAspectQuotient);   //将缩放控制这观察者添加到比例变化地被观察者中
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
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null && mState != null) {

            final float aspectQuotient = mAspectQuotient.get();

            final int viewWidth = getWidth(); //该值不变
            final int viewHeight = getHeight();
            final int bitmapWidth = mBitmap.getWidth(); //该值不变
            final int bitmapHeight = mBitmap.getHeight();

            final float panX = mState.getPanX();
            final float panY = mState.getPanY();
            final float zoomX = mState.getZoomX(aspectQuotient) * viewWidth
                    / bitmapWidth;  //水平方向是原先的倍数
            final float zoomY = mState.getZoomY(aspectQuotient) * viewHeight
                    / bitmapHeight;

            mRectSrc.left = (int) (panX * bitmapWidth - viewWidth / (zoomX * 2));
            mRectSrc.top = (int) (panY * bitmapHeight - viewHeight
                    / (zoomY * 2));
            mRectSrc.right = (int) (mRectSrc.left + viewWidth / zoomX);  //viewWidth / zoomX,加入当前缩放比为2，也就是为原先两倍，但视图范围内只能显示一倍，因此必须除以
            mRectSrc.bottom = (int) (mRectSrc.top + viewHeight / zoomY);

            mRectDst.left = 0;
            mRectDst.top = 0;
            mRectDst.right = getWidth();
            mRectDst.bottom = getHeight();

            canvas.drawBitmap(mBitmap, mRectSrc, mRectDst, mPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG,"onLayout方法执行");
        mAspectQuotient.updateAspectQuotient(right - left, bottom - top,
                mBitmap.getWidth(), mBitmap.getHeight());
        mAspectQuotient.notifyObservers();
    }

    @Override
    public void update(Observable observable, Object data) {
        invalidate();
    }

    private class BasicZoomListener implements OnTouchListener {

        /**
         * Zoom control to manipulate
         */
        private BasicZoomControl mZoomControl;

        private float mFirstX = -1; //第一个触控点坐标
        private float mFirstY = -1;  //第二个触控点坐标
        private float mSecondX = -1;
        private float mSecondY = -1;

        private int mOldCounts = 0;  //记录上一次操作，有几个触控点

        /**
         * Sets the zoom control to manipulate
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
                        float fSecondX = event
                                .getX(event.getPointerId(nCounts - 1));
                        float fSecondY = event
                                .getY(event.getPointerId(nCounts - 1));

                        double nLengthOld = getLength(mFirstX, mFirstY, mSecondX,
                                mSecondY); //计算缩放前，两点之间的距离
                        double nLengthNow = getLength(fFirstX, fFirstY, fSecondX,
                                fSecondY);   //计算缩放后，两点之间的距离

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
     * 观察者，被添加到AspectQuotient被观察者中
     */
    private class BasicZoomControl implements Observer {

        /**
         * Minimum zoom level limit
         */
        private static final float MIN_ZOOM = 1;

        /**
         * Maximum zoom level limit
         */
        private static final float MAX_ZOOM = 16;

        /**
         * Zoom state under control
         */
        private final ZoomState mState = new ZoomState();

        /**
         * Object holding aspect quotient of view and content
         */
        private AspectQuotient mAspectQuotient;

        /**
         * Set reference object holding aspect quotient
         *
         * @param aspectQuotient Object holding aspect quotient
         */
        public void setAspectQuotient(AspectQuotient aspectQuotient) {
            if (mAspectQuotient != null) {
                mAspectQuotient.deleteObserver(this);
            }

            mAspectQuotient = aspectQuotient;
            mAspectQuotient.addObserver(this);
        }

        /**
         * Get zoom state being controlled
         *
         * @return The zoom state
         */
        public ZoomState getZoomState() {
            return mState;
        }

        /**
         * Zoom
         *
         * @param f Factor of zoom to apply
         * @param x X-coordinate of invariant position
         * @param y Y-coordinate of invariant position
         */
        public void zoom(float f, float x, float y) {

            // Log.d("Zoom", "zoom f = " + f);

            final float aspectQuotient = mAspectQuotient.get();

            final float prevZoomX = mState.getZoomX(aspectQuotient);
            final float prevZoomY = mState.getZoomY(aspectQuotient);

            mState.setZoom(mState.getZoom() * f);
            limitZoom();

            final float newZoomX = mState.getZoomX(aspectQuotient);
            final float newZoomY = mState.getZoomY(aspectQuotient);

            // Pan to keep x and y coordinate invariant
            mState.setPanX(mState.getPanX() + (x - .5f)
                    * (1f / prevZoomX - 1f / newZoomX));
            mState.setPanY(mState.getPanY() + (y - .5f)
                    * (1f / prevZoomY - 1f / newZoomY));

            limitPan();

            mState.notifyObservers();
        }

        /**
         * Pan
         *
         * @param dx Amount to pan in x-dimension
         * @param dy Amount to pan in y-dimension
         */
        public void pan(float dx, float dy) {
            final float aspectQuotient = mAspectQuotient.get();

            mState.setPanX(mState.getPanX() + dx
                    / mState.getZoomX(aspectQuotient));
            mState.setPanY(mState.getPanY() + dy
                    / mState.getZoomY(aspectQuotient));

            limitPan();

            mState.notifyObservers();
        }

        /**
         * Help function to figure out max delta of pan from center position.
         *
         * @param zoom Zoom value
         * @return Max delta of pan
         */
        private float getMaxPanDelta(float zoom) {
            return Math.max(0f, .5f * ((zoom - 1) / zoom));
        }

        /**
         * Force zoom to stay within limits
         */
        private void limitZoom() {
            if (mState.getZoom() < MIN_ZOOM) {
                mState.setZoom(MIN_ZOOM);
            } else if (mState.getZoom() > MAX_ZOOM) {
                mState.setZoom(MAX_ZOOM);
            }
        }

        /**
         * Force pan to stay within limits
         */
        private void limitPan() {
            final float aspectQuotient = mAspectQuotient.get();

            final float zoomX = mState.getZoomX(aspectQuotient);
            final float zoomY = mState.getZoomY(aspectQuotient);

            final float panMinX = .5f - getMaxPanDelta(zoomX); //最小最大移动变化幅度
            final float panMaxX = .5f + getMaxPanDelta(zoomX);
            final float panMinY = .5f - getMaxPanDelta(zoomY);
            final float panMaxY = .5f + getMaxPanDelta(zoomY);

            if (mState.getPanX() < panMinX) {
                mState.setPanX(panMinX);
            }
            if (mState.getPanX() > panMaxX) {
                mState.setPanX(panMaxX);
            }
            if (mState.getPanY() < panMinY) {
                mState.setPanY(panMinY);
            }
            if (mState.getPanY() > panMaxY) {
                mState.setPanY(panMaxY);
            }
        }

        // Observable interface implementation

        public void update(Observable observable, Object data) {
            limitZoom();
            limitPan();
        }
    }

    /**
     * 被观察者
     */
    private class AspectQuotient extends Observable {

        /**
         * Aspect quotient
         * 记录视图与图像的尺寸关系
         */
        private float mAspectQuotient;

        // Public methods

        /**
         * Gets aspect quotient
         *
         * @return The aspect quotient
         */
        public float get() {
            return mAspectQuotient;
        }

        /**
         * Updates and recalculates aspect quotient based on supplied view and
         * content dimensions.
         *
         * @param viewWidth     Width of view
         * @param viewHeight    Height of view
         * @param contentWidth  Width of content
         * @param contentHeight Height of content
         */
        public void updateAspectQuotient(float viewWidth, float viewHeight,
                                         float contentWidth, float contentHeight) {
            final float aspectQuotient = (contentWidth / contentHeight)
                    / (viewWidth / viewHeight);
            Log.e(TAG,"初始，aspectQuotient="+aspectQuotient);
            if (aspectQuotient != mAspectQuotient) {
                mAspectQuotient = aspectQuotient;
                setChanged();  //用于确定数据发生变化，只有为true,通知才会起作用，观察者模式中比较重要的方法
            }
        }
    }

    /**
     * 被观察者，添加主类TouchImageView观察者
     */
    private class ZoomState extends Observable {
        /**
         * Zoom level A value of 1.0 means the content fits the view.
         */
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

        // Public methods

        /**
         * Get current x-pan
         * @return current x-pan
         */
        public float getPanX() {
            return mPanX;
        }

        /**
         * Get current y-pan
         *
         * @return Current y-pan
         */
        public float getPanY() {
            return mPanY;
        }

        /**
         * Get current zoom value
         *
         * @return Current zoom value
         */
        public float getZoom() {
            return mZoom;
        }

        /**
         * Help function for calculating current zoom value in x-dimension
         *
         * @param aspectQuotient (Aspect ratio content) / (Aspect ratio view)
         * @return Current zoom value in x-dimension
         */
        public float getZoomX(float aspectQuotient) {
            return Math.min(mZoom, mZoom * aspectQuotient);
        }

        /**
         * Help function for calculating current zoom value in y-dimension
         *
         * @param aspectQuotient (Aspect ratio content) / (Aspect ratio view)
         * @return Current zoom value in y-dimension
         */
        public float getZoomY(float aspectQuotient) {
            return Math.min(mZoom, mZoom * aspectQuotient);
        }

        /**
         * Set pan-x
         *
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
         *
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
         *
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