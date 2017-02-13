package com.wj.demo.ui.activity.hongbao;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.ToolbarHelper;

/**
 * 可清空的EditText
 * Created by wuj on 2016/6/26.
 * @version 1.0
 */
public class DemoWeChatGetHongbaoActivity extends BaseActivity {
    private static String TAG = DemoWeChatGetHongbaoActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView tvTitle;
    private Button btStart;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_demo_wechat_get_hongbao);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        btStart = (Button)findViewById(R.id.bt_start);
        btStart.setOnClickListener(this);

        tvTitle.setText("微信自动抢红包");
        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);
    }

    @Override
    protected void myOnClick(View view) {
        super.myOnClick(view);
        switch(view.getId()){
            case R.id.bt_start:
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS); //辅助类的设置
                startActivity(intent);
                break;
        }
    }
}
