package com.wj.library.base;

import android.app.Application;

/**
 * 通用上下文
 * @author idea_wj 2016-06-01
 * @version 1.0
 */
public class MyBaseApplication extends Application {
    private static final String TAG = MyBaseApplication.class.getName();

    private static MyBaseApplication myBaseApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        myBaseApplication = this;
    }

    public static MyBaseApplication getInstance(){
        return myBaseApplication;
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
