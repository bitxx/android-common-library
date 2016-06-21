package com.wj.library.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
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
public class MagicalImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private Type type = Type.FIT_CENTER;

    public MagicalImageView(Context context) {
        super(context);
    }

    public MagicalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
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


    @Override
    public void onGlobalLayout() {

    }

    /**
     * 图片初始化时候，显示的模式，
     * 由于重写了ImageView缘故，原先的ScaleType不能使用，需要在此使用setType()方法
     */
    public enum Type{
        IT_XY      (1),

        FIT_START   (2),

        FIT_CENTER  (3),

        FIT_END     (4),

        CENTER      (5),

        CENTER_CROP (6),

        CENTER_INSIDE (7);

        Type(int type) {
            this.type = type;
        }
        final int type;
    }

}
