package com.arno.demo.life.event.custom;

import com.blankj.utilcode.util.ToastUtils;

public class WeXinUser implements observer {
    private final String name;

    public WeXinUser(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        ToastUtils.showShort("name:" + name + ",message: " + message);
    }
}