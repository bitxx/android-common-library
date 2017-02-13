package com.wj.library.helper;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.wj.library.util.DeviceUtil;

/**
 * Created by idea_wj on 2016/6/1.
 * view通用的一些方法
 * @version 1.0
 */
public class ViewHelper {

    /**
     * 1. 在activity的Oncreate状态时，需用该方法才可以获取到某个view的长宽
     * 或者在某些特殊情况下，也只有此方法可以最可靠的获取
     * @return
     */
    public static int getViewWidth(final View view){
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return view.getWidth();
    }

    public static int getViewHeight(final View view){
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return view.getHeight();
    }

    /**
     * 屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕长度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 2.
     * @param view
     * @return 返回para后，用para.height即为高度，同理可找到宽度
     */
    public static ViewGroup.LayoutParams getView(View view){
        return view.getLayoutParams();
    }

    /**
     * 3.修改控件的长宽
     * @param width
     * @param height
     * @param view
     * @return para 返回值传入控件的setLayoutParams方法中即可
     */
    public static ViewGroup.LayoutParams setViewSize(int width,int height,View view){
        ViewGroup.LayoutParams para = view.getLayoutParams();
        para.height = height;
        para.width = width;
        return para;
    }



}
