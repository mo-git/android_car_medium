package cn.bashiquan.cmj.sdk.event.login;

import cn.bashiquan.cmj.sdk.bean.LoginBean;
import cn.bashiquan.cmj.sdk.bean.UserBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mocf on 2018/9/26.
 */
public class UnauthenticatedEvent extends BaseEvent {

    public enum EventType {
        LOIGN_SUCCESS,  //登陆接口
        LOGIN_NO_SUCCESS; // 不是登陆接口
    }

    public EventType eventType;

    public UnauthenticatedEvent(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }


}
