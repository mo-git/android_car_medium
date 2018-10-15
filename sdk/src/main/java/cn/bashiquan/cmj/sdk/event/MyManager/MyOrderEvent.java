package cn.bashiquan.cmj.sdk.event.MyManager;

import cn.bashiquan.cmj.sdk.bean.MyOrderListBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/13.
 */

public class MyOrderEvent extends BaseEvent {
    public enum EventType{
        GET_ORDER_LIST_SUCCESS,
        GET_ORDER_LIST_FAILED;
    }

    private EventType event;
    private MyOrderListBean myOrderListBean;
    public MyOrderEvent(EventType event, MyOrderListBean myOrderListBean){
        this.event = event;
        this.myOrderListBean = myOrderListBean;
    }
    public MyOrderEvent(EventType event, String msg){
        this.event = event;
        this.msg = msg;
    }

    public MyOrderListBean getMyOrderListBean() {
        return myOrderListBean;
    }

    public EventType getEvent() {
        return event;
    }
}
