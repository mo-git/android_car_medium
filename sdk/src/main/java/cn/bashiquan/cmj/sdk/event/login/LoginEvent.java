package cn.bashiquan.cmj.sdk.event.login;

import cn.bashiquan.cmj.sdk.bean.Login;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mocf on 2018/9/26.
 */
public class LoginEvent extends BaseEvent {
    public enum EventType{
        LOGIN_SUCCESS,
        LOGIN_FAILD;
    }

    private String name;
    private Login mLogin;
    private  EventType enent;
    public LoginEvent(EventType event,String name,String msg,String className){
        this.name = name;
        this.enent = event;
        this.msg = msg;
        this.className = className;
    }

    public LoginEvent(EventType event,Login login,String msg,String className){
        this.mLogin = login;
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
    public Login getLogin(){
        return mLogin;
    }
}
