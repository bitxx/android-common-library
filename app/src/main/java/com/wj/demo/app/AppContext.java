package com.wj.demo.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.wj.library.base.MyBaseApplication;

/**
 * 上下文
 * Created by idea_wj on 2016/6/1.
 * @version 1.0
 */
public class AppContext extends MyBaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

            MultiDex.install(this);
    }
}