package com.wj.demo.ui.activity.killprocess;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.ToastHelper;
import com.wj.library.helper.ToolbarHelper;

/**
 * 进程（app）不可被杀死
 * Created by wuj on 2016/6/26.
 * @version 1.0
 */
public class DemoNotKillProcessActivity extends BaseActivity {
    private static String TAG = DemoNotKillProcessActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView tvTitle;
    private Button btStart;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_demo_not_kill_process);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        btStart = (Button)findViewById(R.id.bt_start);
        btStart.setOnClickListener(this);

        tvTitle.setText("进程不可被杀死");
        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);
    }

    @Override
    protected void myOnClick(View view) {
        super.myOnClick(view);
        switch(view.getId()){
            case R.id.bt_start:
                this.startService(new Intent(this,LocalService.class));
                this.startService(new Intent(this,RemoteService.class));
                ToastHelper.toastLong(this,"双进程已开启，可前往手机应用中查看");
                break;
        }
    }
}
