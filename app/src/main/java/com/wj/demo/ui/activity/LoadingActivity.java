package com.wj.demo.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

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

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    UIHelper.startActivityAndFinishin(LoadingActivity.this, new Intent(LoadingActivity.this, MainActivity.class));
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_loading);

        //暂时休眠1800过渡，后面再处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1800);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}