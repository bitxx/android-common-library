package com.wj.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.wj.library.util.DeviceUtil;

import java.util.Calendar;
import com.wj.library.R;

/**
 * @author idea_wj 2015-01-18
 * 所有activity的基类
 * @version 1.0
 */
public abstract class MyBaseActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private InputMethodManager imm; //输入法控制


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        //makeStatusBarTransitation();  //将状态栏设置为半透明,由于自定义的actionbar背景为白色，在小米手机上会遮挡了时间显示，暂时关闭，后期拓展
        //processLogic(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if(imm!=null)
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            myOnClick(v);//若大于延时,则执行业务
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        DeviceUtil.hideSoftKeyboard(getCurrentFocus(),this);
        setContentView(R.layout.view_null);
    }

    protected abstract void initView();
    protected abstract void initData();
    protected abstract void myOnClick(View view);
}