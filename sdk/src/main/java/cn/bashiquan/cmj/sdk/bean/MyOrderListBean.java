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

    public static class OrderDataBean implements Serializable {
        private List<OrderBean> list;

        public List<OrderBean> getList() {
            return list;
        }
    }

    public static class OrderBean implements Serializable {
        private InfoBean info;
        private String id;
        private String product_id;
        private String idkey;
        private String kname;
        private String num;
        private String point;
        private String uid;
        private String created_at;
        private String updated_at;
        private String pitem_id;
        private int otype;   //otype =1  显示查看  2 = 已退款

        public InfoBean getInfo() {
            return info;
        }

        public String getId() {
            return id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getIdkey() {
            return idkey;
        }

        public String getKname() {
            return kname;
        }

        public String getNum() {
            return num;
        }

        public String getPoint() {
            return point;
        }

        public String getUid() {
            return uid;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getPitem_id() {
            return pitem_id;
        }

        public int getOtype() {
            return otype;
        }

    }

    public static class InfoBean implements Serializable {
        private String key;
        private String idkey;
        private String price;
        private String stock;
        private String sellNum;
        private String limitBuy;

        public String getKey() {
            return key;
        }

        public String getIdkey() {
            return idkey;
        }

        public String getPrice() {
            return price;
        }

        public String getStock() {
            return stock;
        }

        public String getSellNum() {
            return sellNum;
        }

        public String getLimitBuy() {
            return limitBuy;
        }
    }

}
