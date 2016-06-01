package com.wj.base;

import android.app.Application;

/**
 * 通用上下文
 * @author idea_wj 2016-06-01
 * @version 1.0
 */
public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getName();

    private static BaseApplication baseApplication;

    /**
     * 多线程，双重锁定
     * @return
     */
    public static BaseApplication getInstance(){

        if(baseApplication==null){
            synchronized (BaseApplication.class){
                if(baseApplication==null){   //涉及到异步问题
                    baseApplication = new BaseApplication();
                }
            }
        }

        return baseApplication;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
}
