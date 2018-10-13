package cn.bashiquan.cmj.sdk.event.HomeManagerEvent;

import cn.bashiquan.cmj.sdk.bean.WXTokenBean;
import cn.bashiquan.cmj.sdk.bean.WXUserBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/9/27.
 * 添加媒体照片
 */

public class WXEvent extends BaseEvent {


    public enum EventType {
        GET_WX_TOKEN_FAILED,
        GET_WX_TOKEN_SUCCESS,
        GET_WX_USER_FAILED,
        GET_WX_USER_SUCCESS
    }

    private EventType eventType;
    private WXTokenBean wTokenBean; // token
    private WXUserBean wXUserBean;// user

    public WXEvent(EventType eventType,String msg) {
        this.eventType = eventType;
        this.msg = msg;
    }
    public WXEvent(EventType eventType, WXTokenBean wTokenBean) {
        this.eventType = eventType;
        this.wTokenBean = wTokenBean;
    }

    public WXEvent(EventType eventType, WXUserBean wXUserBean) {
        this.eventType = eventType;
        this.wXUserBean = wXUserBean;
    }

    public WXTokenBean getwTokenBean() {
        return wTokenBean;
    }

    public WXUserBean getwXUserBean() {
        return wXUserBean;
    }

    public EventType getEventType() {
        return eventType;
    }
}
