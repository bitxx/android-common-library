package com.wj.library.helper;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.wj.library.base.MyBaseAppManager;


/**
 * @version 1.0
 * UI操作的通用管理
 * @author idea_wj 2015-08-04
 */
public class UIHelper {

    public static void activityFinish() {
        MyBaseAppManager.getInstance().finishActivity();
    }

    /**
     * Activity页面之间的简单跳转,跳转后，前一个activity不被finish
     *
     */
    public static void startActivityNoFinishin(AppCompatActivity activity, Intent intent) {
        activity.startActivity(intent);
        MyBaseAppManager.getInstance().addActivity(activity);
    }


    /**
     * Activity页面之间跳转并返回结果
     * 不适用于activity
     *
     */
    public static void startActivityForResult(AppCompatActivity activity,Intent intent, int RequestCode) {
        activity.startActivityForResult(intent, RequestCode);
        MyBaseAppManager.getInstance().addActivity(activity);
    }

    /**
     * 从fragment切换到下一个Activity页面之间跳转并返回结果
     *
     */
    public static void startFragmentActivityForResult(Fragment fragment,AppCompatActivity activity,Intent intent, int RequestCode) {
        fragment.startActivityForResult(intent, RequestCode);
        MyBaseAppManager.getInstance().addActivity(activity);
    }

    /**
     * Activity页面之间的简单跳转,跳转后，前一个activity被finish
     *
     */
    public static void startActivityAndFinishin(AppCompatActivity activity, Intent intent) {
        activity.startActivity(intent);
        MyBaseAppManager.getInstance().addActivity(activity);  //再新增
        MyBaseAppManager.getInstance().finishActivity(activity);  //栈顶先除去


    }
}
