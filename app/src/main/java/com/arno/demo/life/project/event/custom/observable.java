package com.arno.demo.life.project.event.custom;

public interface observable {

    public boolean addWeXinUser(WeXinUser weXinUser);

    public boolean removeWeXinUser(WeXinUser weXinUser);

    public void notify(String message);
}