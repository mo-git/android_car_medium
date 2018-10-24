package cn.bashiquan.cmj.sdk.event.HomeManagerEvent;

import cn.bashiquan.cmj.sdk.bean.CarTypeListBean;
import cn.bashiquan.cmj.sdk.bean.RangListBean;
import cn.bashiquan.cmj.sdk.bean.TaskInfoReposeBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/9.
 */

public class RangEvent extends BaseEvent{
    public enum EventType {
        GET_RANG_SUCCESS,
        GETE_RANG_FAILED
    }

    private RangListBean rangListBean;
    private EventType eventType;
    public RangEvent(EventType eventType, RangListBean rangListBean){
        this.eventType = eventType;
        this.rangListBean = rangListBean;
    }
    public RangEvent(EventType eventType,String msg){
        this.eventType = eventType;
        this.msg = msg;
    }

    public RangListBean getRangListBean() {
        return rangListBean;
    }

    public EventType getEventType() {
        return eventType;
    }
}
