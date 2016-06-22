package com.wj.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.wj.library.helper.ToastHelper;

/**
 * 该imageview目前支持功能：
 * 手势缩放
 * 放大后平移
 * 双击放大缩小
 * 初始化显示图片样式
 * Created by wuj on 2016/6/20.
 *
 */
public class MagicalImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private Type type = Type.FIT_CENTER;
    private boolean isInitLayout = true;  //是否需要重新布局
    private float scale = 1.0f; //当前手势缩放尺寸,默认为1.0f。不能和初始化时候地缩放比混合,该缩放是以视图初始化结束后地展示为基础
    private final Matrix matrix = new Matrix();   //图像操作地矩阵,3x3的

    public MagicalImageView(Context context) {
        super(context,null);
    }

    public MagicalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);  //自定义展示,这样一来,用户无论设置别的类型地,都无效
        setType(Type.FIT_START);
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
        if(isInitLayout) {
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
     * @param scaleX  x方向缩放尺寸
     * @param scaleY  y方向缩放尺寸
     * @param px  x方向缩放位置
     * @param py  y方向缩放位置
     */
    private void initView(float dx,float dy,float scaleX,float scaleY,float px,float py){
        matrix.postTranslate(dx,dy);  //平移,该方法表示,平移地同时立即响应绘制
        matrix.postScale(scaleX,scaleY,px,py);
        setImageMatrix(matrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
