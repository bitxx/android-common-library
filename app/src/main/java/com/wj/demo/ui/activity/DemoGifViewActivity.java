package com.wj.demo.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.ToolbarHelper;

/**
 * Gif动画
 * Created by wuj on 2016/6/7.
 * @version 1.0
 */
public class DemoGifViewActivity extends BaseActivity {
    private static String TAG = DemoGifViewActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView tvTitle;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_demo_gif_view);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTitle.setText("GIF动画");
        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);
    }

}
