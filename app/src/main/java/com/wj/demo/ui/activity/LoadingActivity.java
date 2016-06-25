package com.wj.demo.ui.activity;

import android.content.Intent;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.UIHelper;
import com.wj.library.widget.GifView;

/**
 * @version 1.0
 * 启动应用时的加载界面
 * @author idea_wj 2015-11-05
 */
public class LoadingActivity extends BaseActivity {
    private static String TAG = LoadingActivity.class.getName();
    private GifView gifLodaing;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_loading);
        gifLodaing = (GifView) findViewById(R.id.gif_loading);
        gifLodaing.setTimes(1);
        gifLodaing.setOnFinishListener(new GifView.FinishListener() {
            @Override
            public void finish() {
                UIHelper.startActivityAndFinishin(LoadingActivity.this, new Intent(LoadingActivity.this, MainActivity.class));
            }
        });
    }
}