package cn.bashiquan.cmj.sdk.event.login;

import cn.bashiquan.cmj.sdk.bean.LoginBean;
import cn.bashiquan.cmj.sdk.bean.UserBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mocf on 2018/9/26.
 */
public class LoginEvent extends BaseEvent {
    public enum EventType{
        LOGIN_SUCCESS,
        LOGIN_FAILD,
        GET_USERINFO_SUCCESS,
        GET_USERINFO_FAILED;
    }

    private LoginBean mLogin;
    private UserBean userBean;
    private  EventType enent;
    public LoginEvent(EventType event,String msg){
        this.enent = event;
        this.msg = msg;
    }

    public LoginEvent(EventType event, LoginBean login){
        this.mLogin = login;
        this.enent = event;
    }

    public LoginEvent(EventType event, UserBean userBean){
        this.userBean = userBean;
        this.enent = event;
    }

    public EventType getEvent(){
        return enent;
    }

    public LoginBean getLogin(){
        return mLogin;
    }

    public UserBean getUserBean() {
        return userBean;
    }
}
