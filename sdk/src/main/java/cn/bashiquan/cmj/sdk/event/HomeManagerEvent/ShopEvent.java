package cn.bashiquan.cmj.sdk.event.HomeManagerEvent;

import cn.bashiquan.cmj.sdk.bean.ProductBean;
import cn.bashiquan.cmj.sdk.bean.ProductListBean;
import cn.bashiquan.cmj.sdk.bean.TaskListBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/9.
 */

public class ShopEvent extends BaseEvent{
    public enum EventType {
        GET_PROTECT_SUCCESS,
        GET_PROTECT_FAILED,
        GET_PROTECTINFO_SUCCESS,
        GET_PROTECTINFO_FAILED
    }

    private ProductListBean productListBean;
    private ProductBean productBean;
    private EventType eventType;

    public ShopEvent(EventType eventType, String msg, ProductListBean productListBean){
        this.eventType = eventType;
        this.msg = msg;
        this.productListBean = productListBean;
    }

    public ShopEvent(EventType eventType, ProductBean productBean){
        this.eventType = eventType;
        this.productBean = productBean;
    }
    public ShopEvent(EventType eventType, String msg){
        this.eventType = eventType;
        this.msg = msg;
    }

    public ProductBean getProductBean() {
        return productBean;
    }

    public ProductListBean getProductListBean() {
        return productListBean;
    }

    public EventType getEventType() {
        return eventType;
    }
}
