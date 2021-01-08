package com.arno.demo.life.event.custom;

import java.util.HashSet;

public class Subscription implements observable {
    //    private final List<WeXinUser> mUserList = new ArrayList<>();
    private final HashSet<WeXinUser> mUserList = new HashSet<>();

    @Override
    public boolean addWeXinUser(WeXinUser weXinUser) {
        return mUserList.add(weXinUser);
    }

    @Override
    public boolean removeWeXinUser(WeXinUser weXinUser) {
        return mUserList.remove(weXinUser);
    }

    @Override
    public void notify(String message) {
        for (WeXinUser weXinUser : mUserList) {
            weXinUser.update(message);
        }
    }
}