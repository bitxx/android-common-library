package com.wj.demo.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;

/**
 * 有弹性的ScrollView
 * Created by wuj on 2016/6/3.
 * @version 1.0
 */
public class ElasticScorllViewDemoActivity extends BaseActivity {
    private static String TAG = ElasticScorllViewDemoActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elastic_srocllview);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void myOnClick(View view) {
        super.myOnClick(view);
        switch(view.getId()){

        }
    }


}
