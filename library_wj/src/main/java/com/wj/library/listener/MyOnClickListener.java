package com.wj.library.listener;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;

/**
 * @version 1.0
 * @author idea_wj 2016-01-21
 * 防止普通点击事件重复执行
 */
public class MyOnClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 500;
    private long lastClickTime = 0;
    private InputMethodManager imm; //输入法控制


    public void myOnClick(View view) {
        imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null&&imm.isActive())
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @Override
    public void onClick(View v) {

        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            myOnClick(v);  //若大于延时,则执行业务
        }
    }
}
