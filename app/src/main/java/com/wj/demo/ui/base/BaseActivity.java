package com.wj.demo.ui.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.wj.demo.app.AppContext;
import com.wj.library.base.MyBaseActivity;
import com.wj.library.helper.UIHelper;


/**
 * @version 1.0
 * 新增：全局所有界面都保证状态栏半透明，在android4.4以上有效
 * 为同时保证界面布局正常，每个activity的xml布局最外层都要加上：android:fitsSystemWindows="true"
 *
 * 各activity的基类，通用处理方法均放在其中
 * initView();和initData();继承BaseActivity的子类，都建议实现其这两个方法并放在相应位置
 *
 * 注意:
 * @author idea_wj 2015-11-05
 */
public class BaseActivity extends MyBaseActivity {
    private static final String TAG = BaseActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                UIHelper.activityFinish(this);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}