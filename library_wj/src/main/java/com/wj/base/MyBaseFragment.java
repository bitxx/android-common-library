package com.wj.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import com.wj.R;
import com.wj.util.DeviceUtil;

import java.util.Calendar;

/**
 * @author idea_wj 2015-08-05
 * @version 1.0
 * 所有Fragment的基类，所有定义的Fragment都需要继承它，统一管理
 */
public abstract class MyBaseFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MyBaseFragment";

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private InputMethodManager imm; //输入法控制

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DeviceUtil.hideSoftKeyboard(getActivity().getCurrentFocus(),getActivity());
        getActivity().setContentView(R.layout.view_null);
    }

    protected abstract void initView();
    protected abstract void initData();
    protected abstract void myOnClick(View view);
}
