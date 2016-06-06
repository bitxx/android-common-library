package com.wj.demo.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.ProfileHelper;
import com.wj.library.helper.ToolbarHelper;
import com.wj.library.util.ImageUtils;

/**
 * @version 1.0
 * 个人信息
 * @author idea_wj 2016-03-13
 */
public class DemoChangeProtraitActivity extends BaseActivity {
    private static final String TAG = DemoChangeProtraitActivity.class.getSimpleName();

    private RelativeLayout rlProfile;
    private ImageView ivProtrait;
    private Toolbar toolbar;
    private TextView tvTitle;

    private ProfileHelper profileHelper;

    public void initView() {
        setContentView(R.layout.activity_demo_change_protrait);
        ivProtrait = (ImageView)findViewById(R.id.iv_portrait);
        rlProfile = (RelativeLayout)findViewById(R.id.rl_profile);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        rlProfile.setOnClickListener(this);

        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);
        tvTitle.setText("替换切换");
    }

    public void initData() {
        profileHelper = new ProfileHelper(this);
    }

    @Override
    protected void myOnClick(View view) {
        super.myOnClick(view);
        switch (view.getId()) {
            case R.id.rl_profile:
                profileHelper.openProfile();
                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        profileHelper.resultSet(requestCode, data, new ProfileHelper.CallbackProfileListener() {

            @Override
            public void callbackProfile(String filePath) {
                ivProtrait.setImageBitmap(ImageUtils.loadResBitmap(filePath,4));
            }
        });
    }
}