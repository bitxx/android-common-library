package com.wj.library.widget;

import android.content.Context;
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
    public MagicalImageView(Context context) {
        super(context);
    }

    public MagicalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MagicalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setType(){}


    @Override
    public void onGlobalLayout() {

    }

}
