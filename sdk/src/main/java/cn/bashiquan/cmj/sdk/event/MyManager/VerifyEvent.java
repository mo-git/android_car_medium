package cn.bashiquan.cmj.sdk.event.MyManager;

import cn.bashiquan.cmj.sdk.bean.BannersBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/13.
 */

public class VerifyEvent extends BaseEvent {
    public enum EventType{
        GET_VERIFY_SUCCESS,
        GET_VERIFY_FAILED,
        VERIFY_USER_SUCCESS,
        VERIFY_USER_FAILED;
    }

    private String code;
    private EventType event;

    public VerifyEvent(EventType event,String code,String msg){
        this.event = event;
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public EventType getEvent() {
        return event;
    }
}
