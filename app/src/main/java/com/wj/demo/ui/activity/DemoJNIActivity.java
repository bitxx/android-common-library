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
public class DemoJNIActivity extends BaseActivity {
    private static String TAG = DemoJNIActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvJNI;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_demo_jni);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvJNI = (TextView) findViewById(R.id.tv_jni);
        tvTitle.setText("JNI测试");
        tvJNI.setText(stringFromJNI());
        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);
    }

    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();
}
