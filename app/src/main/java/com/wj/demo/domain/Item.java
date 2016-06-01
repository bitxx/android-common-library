package com.wj.demo.domain;

import com.wj.library.domain.Entity;

/**
 * Created by wuj on 2016/6/1.
 */
public class Item extends Entity {
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
