package com.wj.library.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;


/**
 * 该imageview目前支持功能：
 * 手势缩放
 * 放大后平移
 * 双击放大缩小
 * 初始化显示图片样式
 * Created by wuj on 2016/6/20.
 *
 */
public class MagicalImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener
        ,ScaleGestureDetector.OnScaleGestureListener
        ,View.OnTouchListener {
    private static final String TAG = MagicalImageView.class.getSimpleName();

    /**
     * 图片的最大缩放比
     */
    private float SCALE_MAX = 8f;

    /**
     * 图片显示方式，默认Type.FIT_CENTER
     */
    private Type type = Type.FIT_CENTER;

    /**
     * 是否需要重新布局
     */
    private boolean isInitLayout = true;

    /**
     * 当前手势缩放尺寸,默认为1.0f。不能和初始化时候地缩放比混合,该缩放是以视图初始化结束后地展示为基础
     */
    private float scale = 1.0f;

    /**
     * 图像操作地矩阵,3x3的
     */
    private final Matrix matrix = new Matrix();

    /**
     * 用于将matrix中的9个值存放在其中
     */
    private final float[] matrixValues = new float[9];

    /**
     * 初始化时候，保存图片所在位置的坐标，用于之后操作的参考
     */
    private final RectF initRectF = new RectF();

    /**
     * initView()方法中初始化
     * x方向最小缩放比，需要根据图片大小，以及初始化显示模式Type来决定，默认1.0
     */
    private float minXScale = 1.0f;

    /**
     * initView()方法中初始化
     * y方向最小缩放比，需要根据图片大小，以及初始化显示模式Type来决定，默认1.0
     */
    private float minYScale = 1.0f;

    /**
     * 手势缩放，需要借用onTouchListener
     */
    private ScaleGestureDetector mScaleGestureDetector;

    /**
     * 保存上一次平移前的焦点坐标点
     */
    private float xOldCenter = -1,yOldCenter = -1;

    public MagicalImageView(Context context) {
        super(context,null);
    }

    public MagicalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);  //自定义展示,这样一来,用户无论设置别的类型地,都无效
        mScaleGestureDetector = new ScaleGestureDetector(context,this);
        setOnTouchListener(this);
    }

    public MagicalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置图片类型
     * @param type
     */
    public void setType(Type type){
        this.type = type;
    }

    /**
     * 获取x方向的缩放比
     * 不可被继承
     * @return
     */
    public final float getXScale(){
        matrix.getValues(matrixValues);  //将matrix中的值复制到
        return matrixValues[Matrix.MSCALE_X];
    }

    /**
     * 获取y方向的缩放比
     * 不可被继承
     * @return
     */
    public final float getYScale(){
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_Y];
    }

    /**
     * View和Window绑定时就会调用这个函数，此时将会有一个Surface进行绘图之类的逻辑。
     * 该方法运行在onResume之后；
     * onResume生命周期后就能获取LayoutParam，进而可以设置高度和宽度了
     *
     * 所以在在该方法中设置视图尺寸最合适
     *
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);  //监听view尺寸变化,
    }

    @SuppressWarnings("deprecation")  //忽略废弃,api15时候,需要使用removeGlobalOnLayoutListener,高版本可使用removeOnGlobalLayoutListener
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 若子控件等等，将会多次执行该方法
     */
    @Override
    public void onGlobalLayout() {
        if(isInitLayout) {  //只有第一次时候才调用
            Drawable drawable = getDrawable();
            int viewWidth = getWidth();
            int viewHeight = getHeight();
            int imgWidth = drawable.getIntrinsicWidth();
            int imgHeight = drawable.getIntrinsicHeight();

            switch (type) {
                case FIT_XY:
                    initView(0,0,viewWidth * 1f /imgWidth,viewHeight * 1f /imgHeight,0,0);
                    break;
                case FIT_START:
                    float scaleX = Math.max(viewWidth * 1f /imgWidth,viewHeight * 1f /imgHeight);  //两个方向,以最大缩放比为基础
                    initView(0,0,scaleX,scaleX,0,0);  //两个方向缩放都一致
                    break;
                case FIT_CENTER:
                    initView((viewWidth -imgWidth) / 2,(viewHeight - imgHeight) / 2,viewWidth * 1f /imgWidth,viewWidth * 1f /imgWidth,viewWidth/2,viewHeight/2);//两个方向缩放都一致
                    break;
                case CENTER:
                    initView((viewWidth -imgWidth) / 2,(viewHeight - imgHeight) / 2,1,1,viewWidth/2,viewHeight/2);
                    break;
                case FIT_END:
                    break;
                case CENTER_CROP:
                    break;
                case CENTER_INSIDE:
                    break;
            }
            isInitLayout = false;
        }
    }

    /**
     * 图像view初始化时候,根据不同type,展示不同模式地图像
     *
     * @param dx  x方向移动位置
     * @param dy  y方向移动位置
     * @param minScaleX  x方向缩放尺寸,该方法在此确定了图片的x方向的最小缩放比
     * @param minScaleY  y方向缩放尺寸,该方法在此确定了图片的y方向的最小缩放比
     * @param px  x方向缩放位置
     * @param py  y方向缩放位置
     */
    private void initView(float dx,float dy,float minScaleX,float minScaleY,float px,float py){
        this.minXScale = minScaleX;
        this.minYScale = minScaleY;
        matrix.postTranslate(dx,dy);  //平移,该方法表示,平移地同时立即响应绘制
        matrix.postScale(minXScale,minYScale,px,py);
        initRectF.union(getCoordinate());
        //Log.e(TAG,"initRectF.left="+initRectF.left+"\ninitRectF.top="+initRectF.top+"\ninitRectF.right="+initRectF.right+"\ninitRectF.bottom="+initRectF.bottom);
        setImageMatrix(matrix);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float xScale = getXScale();
        float yScale = getYScale();
        float scaleFactor = detector.getScaleFactor(); //当前图片和上一次缩放前做比较，比例，大于1，则扩大，小于1则缩小
        if(scaleFactor == 1.0f)
            return true;

        float minScale = Math.min(minXScale/xScale,minYScale/yScale);
        float maxScale = Math.max(SCALE_MAX/xScale,SCALE_MAX/yScale);
        if(scaleFactor < minScale)
            scaleFactor = minScale;
        if(scaleFactor>maxScale)
            scaleFactor = maxScale;
        matrix.postScale(scaleFactor,scaleFactor,detector.getFocusX(),detector.getFocusY());
        checkBorder();
        setImageMatrix(matrix);

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.e(TAG,"缩放开始");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.e(TAG,"缩放结束");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        float xNowCenter = -1,yNowCenter = -1;  //用于记录多个触控点的中心坐标
        final int pointCount = event.getPointerCount();

        for(int i=0;i<pointCount;++i){
            xNowCenter += event.getX(i);
            yNowCenter += event.getY(i);
        }

        xNowCenter = xNowCenter / pointCount;
        yNowCenter = yNowCenter / pointCount;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xOldCenter = xNowCenter;
                yOldCenter = xNowCenter;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = xNowCenter - xOldCenter;
                float dy = yNowCenter - yOldCenter;

                /**
                 * 是否正在缩放操作
                 * 通过此判断，可以减少滑动和缩放手势的冲突，尽量避免缩放时候，响应了滑动
                 * 目前并没有完美处理了该问题，图片缩放时候，仍旧有几率触动图片跳动，这就是缩放时候触动了滑动的缘故
                 * 若有更好的方式，请与作者共享下？ ^_^
                 *
                 */
                if( Math.sqrt((dx * dx) + (dy * dy)) >= 10 && mScaleGestureDetector.isInProgress() == false) {
                    Log.e(TAG,"hello");
                    matrix.postTranslate(dx, dy);  //先平移更新，数据
                    checkBorder();
                    setImageMatrix(matrix);
                }
                xOldCenter = xNowCenter;
                yOldCenter = yNowCenter;
                break;
        }
        return true;
    }

    /**
     * 根据当前图片的Matrix获得图片尺寸信息
     * 将矩阵应用到矩形
     * 返回的矩形中，rect.left，rect.right,rect.top,rect.bottom分别就就是当前屏幕离你的图片的边界的距离
     * @return
     */
    private RectF getCoordinate(){
        RectF rectF = new RectF(); //存储坐标
        Drawable drawable = getDrawable();
        if(drawable!=null){
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF); //原先矩阵matrix 缩放等等参数，将rectF的默认坐标转换为实际坐标
        }
        return rectF;
    }

    /**
     * 避免缩放后，图片和view之间出现空白
     * 检查边界问题
     */
    private synchronized void checkBorder() {
        float deltaX = 0f;
        float deltaY = 0f;
        RectF rect = getCoordinate();

        if (rect.left > initRectF.left)   //如果图像左上角当前横坐标x大于初始值
            deltaX = -(rect.left - initRectF.left);

        if (rect.top > initRectF.top)   //如果图像左上角当前纵坐标y大于初始值
            deltaY = -(rect.top - initRectF.top);

        if(rect.right< initRectF.right)   //如果图像右下角当前横坐标x小于初始值
            deltaX = initRectF.right - rect.right;

        if(rect.bottom<initRectF.bottom)    //如果图像右下角当前纵坐标y小于初始值
            deltaY = initRectF.bottom - rect.bottom;

        if(deltaX!=0||deltaY!=0)
            matrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 图片初始化时候，显示的模式，
     * 由于重写了ImageView缘故，原先的ScaleType不能使用，需要在此使用setType()方法
     */
    public enum Type{
        /**
         * 不按比例缩放图片，目标是把图片塞满整个View。
         */
        FIT_XY      (1),

        /**
         * 左上角开始等比例缩放,直到占满整个view
         */
        FIT_START   (2),

        /**
         * 把图片按比例扩大/缩小到View的宽度，居中显示
         */
        FIT_CENTER  (3),

        FIT_END     (4),

        /**
         * 图像地中心放在view中间,其余默认
         */
        CENTER      (5),

        CENTER_CROP (6),

        CENTER_INSIDE (7);

        Type(int type) {
            this.type = type;
        }
        final int type;
    }

}
