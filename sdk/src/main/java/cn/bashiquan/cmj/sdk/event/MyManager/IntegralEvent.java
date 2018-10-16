package cn.bashiquan.cmj.sdk.event.MyManager;

import cn.bashiquan.cmj.sdk.bean.IntegralListBean;
import cn.bashiquan.cmj.sdk.bean.MyOrderListBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/13.
 */

public class IntegralEvent extends BaseEvent {
    public enum EventType{
        GET_INTEGRAL_LIST_SUCCESS,
        GET_INTEGRAL_LIST_FAILED,
        GET_WITHDRAWLIMIT_SUCCESS,
        GET_WITHDRAWLIMIT_FAILED,
        GET_WITHDRAWLIST_SUCCESS,
        GET_WITHDRAWLIST_FAILED,
        GET_MONEY_SUCCESS,
        GET_MONEY_FAILED;
    }

    private EventType event;
    private int limit;
    private IntegralListBean integralListBean;
    public IntegralEvent(EventType event, IntegralListBean integralListBean){
        this.event = event;
        this.integralListBean = integralListBean;
    }
    public IntegralEvent(EventType event, String msg){
        this.event = event;
        this.msg = msg;
    }
 public IntegralEvent(EventType event, int limit){
        this.event = event;
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public IntegralListBean getIntegralListBean() {
        return integralListBean;
    }

    public EventType getEvent() {
        return event;
    }
}
