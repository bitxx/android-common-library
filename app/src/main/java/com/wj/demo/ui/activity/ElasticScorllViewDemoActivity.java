package com.wj.demo.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.ToolbarHelper;

/**
 * 有弹性的ScrollView
 * Created by wuj on 2016/6/3.
 * @version 1.0
 */
public class ElasticScorllViewDemoActivity extends BaseActivity {
    private static String TAG = ElasticScorllViewDemoActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView tvTitle;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_elastic_srocllview);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTitle.setText("有弹性的ScrollView");
        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);
    }

    @Override
    protected void myOnClick(View view) {
        super.myOnClick(view);
        switch(view.getId()){

        }
    }
}
