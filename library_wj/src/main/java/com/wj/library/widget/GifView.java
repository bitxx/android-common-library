package com.wj.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * version 1.0
 * <p/>
 * Created by wuj on 2016/6/7.
 * 实现gif动画
 */
public class GifView extends ImageView {
    private static final String TAG = GifView.class.getSimpleName();

    private long perTime = 0; //动画上一帧时间
    private long nowTime = 0;  //动画当前帧时间
    private Movie movie = null;  //用于存储帧

    private int srcId = -1;   //用于保存重组件src中获取到的图片资源

    public GifView(Context context) {
        super(context);
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initImg(context, attrs);
    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化图片资源
     *
     * @param context
     * @param attrs
     */
    private void initImg(Context context, AttributeSet attrs) {

        //关闭硬件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        srcId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
        if (srcId > 0)
            movie = Movie.decodeStream(getResources().openRawResource(srcId));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**此处不需要去继承ImageView父类的onDraw()方法，否则绘制会有异常*/
        drawGif(canvas);
    }

    public void drawGif(Canvas canvas) {
        nowTime = android.os.SystemClock.uptimeMillis();  //开机到现在的毫秒数，不能使用System.currentTimeMillis，耗时太长，动画会卡
        if (perTime == 0) {  //说明动画刚刚开始
            perTime = nowTime;
        }
        if (movie != null) {
            int duration = movie.duration();//获取gif持续时间
            int nowRealTime = (int) ((nowTime - perTime) % duration); //准确计算出当前点时间
            movie.setTime(nowRealTime);
            canvas.save(Canvas.MATRIX_SAVE_FLAG);
            canvas.restore();
            movie.draw(canvas, 0, 0);
            invalidateView(); //清空当前画面
        }
    }

    @SuppressLint("NewApi")
    private void invalidateView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            postInvalidateOnAnimation();
        } else {
            invalidate();
        }
    }
}
