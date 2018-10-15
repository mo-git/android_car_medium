package cn.bashiquan.cmj.sdk.event.MyManager;

import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/13.
 */

public class TaxationEvent extends BaseEvent {
    public enum EventType{
        SUMIT_SUCCESS,
        SUBMIT_FAILED;
    }

    private EventType event;

    public TaxationEvent(EventType event, String msg){
        this.event = event;
        this.msg = msg;
    }


    public EventType getEvent() {
        return event;
    }
}
