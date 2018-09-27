package cn.bashiquan.cmj.sdk.event.HomeEvent;

import cn.bashiquan.cmj.sdk.bean.BannersBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/9/27.
 */

public class BannerEvent extends BaseEvent {
    public enum EventType {
        GET_BANNER_SUCCESS,
        GET_BANNER_FAILED,
    }
    public BannersBean bannersBean;
    private EventType eventType;
    public BannerEvent(EventType eventType, String msg,BannersBean bannersBean){
        this.bannersBean = bannersBean;
        this.eventType = eventType;
        this.msg = msg;

    }
    public BannersBean getBannersBean(){
        return bannersBean;
    }

    public EventType getEventType() {
        return eventType;
    }
}
