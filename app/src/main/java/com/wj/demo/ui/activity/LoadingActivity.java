package com.wj.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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

    private final int CHECK_UPDATE = 1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CHECK_UPDATE:
                    UIHelper.startActivityAndFinishin(LoadingActivity.this, new Intent(LoadingActivity.this, MainActivity.class));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        final View view = View.inflate(this, R.layout.activity_loading, null);
        setContentView(view);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1800);
                    Message msg = new Message();
                    msg.what = CHECK_UPDATE;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void initData() {
        
    }
}