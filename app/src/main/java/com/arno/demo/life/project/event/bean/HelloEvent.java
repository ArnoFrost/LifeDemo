package com.arno.demo.life.project.event.bean;

public class HelloEvent {
    int id;
    String name;

    public HelloEvent() {
    }

    public HelloEvent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "HelloEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
