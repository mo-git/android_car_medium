package com.zhidian.app.sdk.event.login;

import com.zhidian.app.sdk.event.BaseEvent;

/**
 * Created by mocf on 2017/7/14.
 */
public class LoginEvent extends BaseEvent {
    public enum EventType{
        LOGIN_SUCCESS,
        LOGIN_FAILD;
    }

    private String name;
    private  EventType enent;
    public LoginEvent(EventType event,String name,String msg,String className){
        this.name = name;
        this.enent = event;
        this.msg = msg;
        this.className = className;
    }

    public EventType getEvent(){
        return enent;
    }

    public String getName(){
        return name;
    }
}
