package com.wj.demo.ui.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.ToolbarHelper;
import com.wj.library.widget.ClearEditText;

/**
 * 可清空的EditText
 * Created by wuj on 2016/6/26.
 * @version 1.0
 */
public class DemoClearEdittextActivity extends BaseActivity {
    private static String TAG = DemoClearEdittextActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView tvTitle;
    private ClearEditText cetTxt;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_demo_clear_edittext);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        cetTxt = (ClearEditText)findViewById(R.id.cet_txt);
        tvTitle.setText("可清空的EditText");
        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);
    }
}
