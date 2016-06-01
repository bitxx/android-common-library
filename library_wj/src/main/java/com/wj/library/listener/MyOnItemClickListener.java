package com.wj.library.listener;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import java.util.Calendar;

/**
 * @version 1.0
 * @author idea_wj 2016-01-21
 * 防止listview的item单击事件重复执行
 */
public class MyOnItemClickListener implements AdapterView.OnItemClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private InputMethodManager imm; //输入法控制

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            myOnItemClick(parent,view,position,id);  //若大于延时,则执行业务
        }
    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void myOnItemClick(AdapterView<?> parent, View view, int position, long id){
        imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    };
}
