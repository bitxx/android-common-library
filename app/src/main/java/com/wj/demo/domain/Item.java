package com.wj.demo.domain;

import com.wj.library.domain.Entity;

/**
 * 主界面列表model
 * Created by wuj on 2016/6/1.
 */
public class Item extends Entity {
    private String name;
    private String state;  //是否已经实现
    private Class activity;  //跳转到哪个界面


    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
