package com.wj.demo.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.ToastHelper;
import com.wj.library.helper.ToolbarHelper;
import com.wj.library.widget.GifView;
import com.wj.library.widget.TouchImageView;

/**
 * 图片手势缩放demo
 * @version 1.0
 */
public class DemoZoomImageActivity extends BaseActivity {
    private static String TAG = DemoZoomImageActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView tvTitle;
    private TouchImageView tivImage;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_demo_zoom_img);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);

        tvTitle.setText("图片手势缩放");
        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);

        tivImage = (TouchImageView)findViewById(R.id.tiv_img);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.demo_img_zoom);
        tivImage.setImage(bitmap);
    }

    @Override
    protected void myOnClick(View view) {
        super.myOnClick(view);
        switch (view.getId()){

        }
    }
}