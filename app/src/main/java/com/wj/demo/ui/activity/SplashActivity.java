package com.wj.demo.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.UIHelper;
import com.wj.library.widget.GifView;

/**
 * @version 1.0
 * 启动应用时的加载界面
 * @author idea_wj 2015-11-05
 */
public class SplashActivity extends BaseActivity {
    private static String TAG = SplashActivity.class.getName();
    private GifView gifLodaing;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
        gifLodaing = (GifView) findViewById(R.id.gif_loading);
        gifLodaing.setTimes(1);
        gifLodaing.setOnFinishListener(new GifView.FinishListener() {
            @Override
            public void finish() {
                UIHelper.startActivityAndFinishin(SplashActivity.this, new Intent(SplashActivity.this, MainActivity.class));
            }
        });
    }
}