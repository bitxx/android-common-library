package com.wj.demo.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.ToastHelper;
import com.wj.library.helper.ToolbarHelper;
import com.wj.library.listener.MyOnClickListener;
import com.wj.library.widget.EmptyLayout;

/**
 * EmptyLayout的使用
 * Created by wuj on 2016/6/26.
 * @version 1.0
 */
public class DemoEmptyLayoutActivity extends BaseActivity {
    private static String TAG = DemoEmptyLayoutActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView tvTitle;
    private Button btEmpty;
    private Button btLoading;
    private Button btReset;
    private EmptyLayout llEmptyLayout;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_demo_empty_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        btEmpty = (Button)findViewById(R.id.bt_empty);
        btLoading = (Button)findViewById(R.id.bt_loading);
        btReset = (Button)findViewById(R.id.bt_reset);
        llEmptyLayout = (EmptyLayout)findViewById(R.id.ll_empty);
        llEmptyLayout.setOnClickListener(new MyOnClickListener() {
            @Override
            public void myOnClick(View view) {
                ToastHelper.toastShort(DemoEmptyLayoutActivity.this,"重新加载成功!");
                llEmptyLayout.setImg(R.mipmap.demo_emptylayout_empty);
                llEmptyLayout.setType(EmptyLayout.NODATA,false);
            }
        });

        tvTitle.setText("EmptyLayout");
        btEmpty.setOnClickListener(this);
        btLoading.setOnClickListener(this);
        btReset.setOnClickListener(this);
        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);
    }

    @Override
    protected void myOnClick(View view) {
        super.myOnClick(view);
        switch(view.getId()){
            case R.id.bt_empty:
                llEmptyLayout.setImg(R.mipmap.demo_emptylayout_empty);
                llEmptyLayout.setType(EmptyLayout.NODATA,false);
                break;
            case R.id.bt_loading:
                llEmptyLayout.setType(EmptyLayout.LOADING,false);
                break;
            case R.id.bt_reset:
                llEmptyLayout.setImg(R.mipmap.demo_empty_error);
                llEmptyLayout.setType(EmptyLayout.ERROR,true);
                break;
        }
    }
}
