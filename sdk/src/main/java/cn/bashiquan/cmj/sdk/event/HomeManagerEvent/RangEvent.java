package cn.bashiquan.cmj.sdk.event.HomeManagerEvent;

import cn.bashiquan.cmj.sdk.bean.CarTypeListBean;
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

    private CarTypeListBean carTypeListBean;
    private EventType eventType;
    public RangEvent(EventType eventType, CarTypeListBean carTypeListBean){
        this.eventType = eventType;
        this.carTypeListBean = carTypeListBean;
    }
    public RangEvent(EventType eventType,String msg){
        this.eventType = eventType;
        this.msg = msg;
    }

    public EventType getEventType() {
        return eventType;
    }
}
