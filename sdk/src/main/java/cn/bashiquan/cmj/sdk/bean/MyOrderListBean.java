package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mo on 2018/10/15.
 */

public class MyOrderListBean implements Serializable {

    private boolean state;
    private String msg;
    private String code;
    private OrderDataBean data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public OrderDataBean getData() {
        return data;
    }

    public static class OrderDataBean implements Serializable{
        private List<OrderBean> list;

        public List<OrderBean> getList() {
            return list;
        }
    }

    public static class OrderBean implements Serializable{

    }

}
