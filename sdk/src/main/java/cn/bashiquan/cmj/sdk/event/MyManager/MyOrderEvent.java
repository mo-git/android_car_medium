package cn.bashiquan.cmj.sdk.event.MyManager;

import cn.bashiquan.cmj.sdk.bean.MyOrderInfotBean;
import cn.bashiquan.cmj.sdk.bean.MyOrderListBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/13.
 */

public class MyOrderEvent extends BaseEvent {
    public enum EventType{
        GET_ORDER_LIST_SUCCESS,
        GET_ORDER_LIST_FAILED,
        GET_ORDERTOKEN_SUCCESS,
        GET_ORDERTOKEN_FAILED,
        REFUND_ORDER_SUCCESS,
        REFUND_ORDER_FAILED;
    }

    private EventType event;
    private MyOrderListBean myOrderListBean;
    private MyOrderInfotBean myOrderInfotBean;
    public MyOrderEvent(EventType event, MyOrderListBean myOrderListBean){
        this.event = event;
        this.myOrderListBean = myOrderListBean;
    }
    public MyOrderEvent(EventType event,MyOrderInfotBean myOrderInfotBean){
        this.event = event;
        this.myOrderInfotBean = myOrderInfotBean;
    }
    public MyOrderEvent(EventType event, String msg){
        this.event = event;
        this.msg = msg;
    }

    public MyOrderInfotBean getMyOrderInfotBean() {
        return myOrderInfotBean;
    }

    public MyOrderListBean getMyOrderListBean() {
        return myOrderListBean;
    }

    public EventType getEvent() {
        return event;
    }
}
