package com.wj.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * version 1.0
 * <p/>
 * Created by wuj on 2016/6/7
 * 实现gif动画
 * 已经关闭硬件加速，增强兼容性
 *
 * *功能：
 * 1.为gif动画设置播放次数
 * 2.设置动画是否暂停
 * 3.设置动画恢复初始
 * 4.监听指定次数播放完毕后的操作
 *
 * *原理：
 * 1.大致是，借用了android的Movie类，存储每一帧
 * 2.监听是通过起始时间到播放当前时间之间的时间与gif总时间相除，结果为播放次数
 */
public class GifView extends ImageView {
    private static final String TAG = GifView.class.getSimpleName();

    private long perTime = 0; //动画上一帧时间
    private long nowTime = 0;  //动画当前帧时间
    private Movie movie = null;  //用于存储帧

    private int srcId = -1;   //用于保存重组件src中获取到的图片资源

    private volatile boolean isPaused = false;  //是否暂停，默认否
    private int duration;   //动画总时长
    private int nowRealTime;  //当前播放的帧的时间
    private int times = 0;  //gif动画播放次数，0则代表无限制播放
    private FinishListener finishListener;   //监听播放结束，只有播放次数times>0时候有效

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
        if (srcId > 0) {
            movie = Movie.decodeStream(getResources().openRawResource(srcId));
            if (movie != null)
                duration = movie.duration();//获取gif持续时间
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**此处不需要去继承ImageView父类的onDraw()方法，否则绘制会有异常*/
        if(movie!=null)
            drawGif(canvas);
        else
            super.onDraw(canvas);
    }

    private void drawGif(Canvas canvas) {
        if (!isPaused) {
            nowTime = android.os.SystemClock.uptimeMillis();  //开机到现在的毫秒数，不能使用System.currentTimeMillis，耗时太长，动画会卡
            if (perTime == 0)  //说明动画刚刚开始
                perTime = nowTime;
            long temp = nowTime - perTime;

            if(((temp/duration)<times&&times>0)||times<=0) {  //
                nowRealTime = (int) (temp % duration); //准确计算出当前点时间

                if (movie != null) {
                    drawFrame(canvas);
                    invalidateView(); //清空当前画面
                }
            }else {
                setPaused(true); //播放结束,则暂停
                finishListener.finish();
            }
        } else
            drawFrame(canvas);
    }

    /**
     * 画出当前帧
     *
     * @param canvas
     */
    private void drawFrame(Canvas canvas) {
        movie.setTime(nowRealTime);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.restore();
        movie.draw(canvas, (getWidth()-movie.width())/2, (getHeight()-movie.height())/2);
    }

    /**
     * 重置
     */
    @SuppressLint("NewApi")
    private void invalidateView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            postInvalidateOnAnimation();
        } else {
            invalidate();
        }
    }

    /**
     * 判断是否暂停状态
     *
     * @return
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * 设置暂停
     *
     * @param paused
     */
    public void setPaused(boolean paused) {
        isPaused = paused;
        if (!paused&&movie!=null) {
            perTime = android.os.SystemClock.uptimeMillis() - nowRealTime; //当前时间距离上次暂停帧时候
        }
        invalidateView();
    }

    /**
     * 重置动画
     */
    public void restart() {
        if(movie!=null) {
            perTime = 0;
            invalidateView();
        }
    }

    /**
     * 只有设置的播放次数times>0时候有效
     * @param finishListener
     */
    public void setOnFinishListener(FinishListener finishListener){
        this.finishListener = finishListener;
    }

    public void setTimes(int times) {
        this.times = times;
    }


    public interface FinishListener{
        void finish();
    }
}
