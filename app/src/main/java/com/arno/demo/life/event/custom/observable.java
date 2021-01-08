package com.arno.demo.life.event.custom;

public interface observable {

    public boolean addWeXinUser(WeXinUser weXinUser);

    public boolean removeWeXinUser(WeXinUser weXinUser);

    public void notify(String message);
}