package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mo on 2018/10/15.
 */

public class MyOrderInfotBean implements Serializable {

    private boolean state;
    private String msg;
    private String code;
    private OrderInfoDataBean data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public OrderInfoDataBean getData() {
        return data;
    }

    public static class OrderInfoDataBean implements Serializable {
        private List<OrderInfoBean> tokens;

        public List<OrderInfoBean> getTokens() {
            return tokens;
        }
    }

    public static class OrderInfoBean implements Serializable {
        private String id;
        private String p_id;
        private String uid;
        private String ref_id;
        private String use_time;
        private String created_at;
        private String updated_at;
        private String token;
        private String atype;
        private String p_uid;
        private String imgcode;
        private String desc;
//        "remark":null,
        private int use_type;

        public String getId() {
            return id;
        }

        public String getP_id() {
            return p_id;
        }

        public String getUid() {
            return uid;
        }

        public String getRef_id() {
            return ref_id;
        }

        public String getUse_time() {
            return use_time;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getToken() {
            return token;
        }

        public String getAtype() {
            return atype;
        }

        public String getP_uid() {
            return p_uid;
        }

        public String getImgcode() {
            return imgcode;
        }

        public String getDesc() {
            return desc;
        }

        public int getUse_type() {
            return use_type;
        }
    }



}
