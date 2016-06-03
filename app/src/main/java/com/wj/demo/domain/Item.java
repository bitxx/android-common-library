package com.wj.demo.domain;

import android.support.v7.app.AppCompatActivity;

import com.wj.library.domain.Entity;

/**
 * Created by wuj on 2016/6/1.
 */
public class Item extends Entity {
    private String name;
    private Class activity;

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
}
