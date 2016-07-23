package com.wj.library.helper;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.wj.library.util.DeviceUtil;
import com.wj.library.util.ScreenUtils;

/**
 * toolbar中常用的方法，统一管理
 * Created by wuj on 2016/6/6.
 * @version 1.0
 */
public class ToolbarHelper {

    /**
     * 初始化toolbar，若要沉浸式，需要在style中开启透明：<item name="android:windowTranslucentStatus">true</item>
     * @param activity
     * @param toolbar
     * @param title   //系统标题
     * @param navigationIcon   //左侧导航图标
     * @param titleColor
     */
    public static void initToolbar(AppCompatActivity activity, Toolbar toolbar, int title, int navigationIcon, int titleColor){
        if(navigationIcon>0)
            toolbar.setNavigationIcon(navigationIcon);//设置导航栏图标
        if(title>0)
            toolbar.setTitle(title);//设置主标题
        else
            toolbar.setTitle("");//设置主标题
        if(titleColor>0)
            toolbar.setTitleTextColor(titleColor);
        else
            toolbar.setTitleTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            toolbar.setPadding(toolbar.getPaddingLeft(),
                    ScreenUtils.getStatusHeight(activity),
                    toolbar.getPaddingRight(),
                    toolbar.getPaddingBottom());
        }
        //toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单,此不支持setSupportActionBar
        activity.setSupportActionBar(toolbar);
    }

    /**
     * 初始化toolbar，若要沉浸式，需要在style中开启透明：<item name="android:windowTranslucentStatus">true</item>
     * @param activity
     * @param toolbar
     * @param title   //系统标题
     * @param subTitle  //系统子标题
     * @param navigationIcon   //左侧导航图标
     * @param titleColor
     */
    public static void initToolbar(AppCompatActivity activity, Toolbar toolbar,int title,int subTitle,  int navigationIcon, int titleColor){
        if(navigationIcon>0)
            toolbar.setNavigationIcon(navigationIcon);//设置导航栏图标
        if(title>0)
            toolbar.setTitle(title);//设置主标题
        else
            toolbar.setTitle("");//设置主标题

        if(subTitle>0)
            toolbar.setSubtitle(subTitle);   //设置子标题
        else
            toolbar.setSubtitle("");

        if(titleColor>0) {
            toolbar.setTitleTextColor(titleColor);
            toolbar.setSubtitleTextColor(titleColor);
        }
        else {
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setSubtitleTextColor(Color.WHITE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            toolbar.setPadding(toolbar.getPaddingLeft(),
                    ScreenUtils.getStatusHeight(activity),
                    toolbar.getPaddingRight(),
                    toolbar.getPaddingBottom());
        }
        //toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单,此不支持setSupportActionBar
        activity.setSupportActionBar(toolbar);
    }

    /**
     * @param activity
     * @param toolbar
     */
    public static void initToolbar(AppCompatActivity activity, Toolbar toolbar,int navigationIcon){
       initToolbar(activity,toolbar,0,navigationIcon,0);
    }
}
