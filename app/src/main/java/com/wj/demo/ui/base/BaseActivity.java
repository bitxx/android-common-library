package com.wj.demo.ui.base;

import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.wj.demo.app.AppContext;
import com.wj.library.base.MyBaseActivity;
import com.wj.library.helper.UIHelper;


/**
 * @version 1.0
 * 注意:
 * @author idea_wj 2015-11-05
 */
public class BaseActivity extends MyBaseActivity {
    private static final String TAG = BaseActivity.class.getName();

    /**
     * 获取上下文
     * @return
     */
    public AppContext getAppContext() {
        return (AppContext)getApplication();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    /**
     * 重写的单击事件
     * @param view
     */
    @Override
    protected void myOnClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //强制竖屏
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            UIHelper.activityFinish(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                UIHelper.activityFinish(this);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}