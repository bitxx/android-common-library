package com.wj.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.UIHelper;


/**
 * @version 1.0
 * 启动应用时的加载界面
 * @author idea_wj 2015-11-05
 */
public class LoadingActivity extends BaseActivity {
    private static String TAG = LoadingActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        super.initView();
        final View view = View.inflate(this, R.layout.activity_loading, null);
        setContentView(view);

        UIHelper.startActivityAndFinishin(LoadingActivity.this, new Intent(LoadingActivity.this, MainActivity.class));
    }

    @Override
    protected void initData() {
        super.initData();
        
    }
}