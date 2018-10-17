package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mo on 2018/10/15.
 * 兑换记录
 */

public class IntegralWithdrawBean implements Serializable {

    private boolean state;
    private String msg;
    private String code;
    private List<WithdrawBean> data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public List<WithdrawBean> getData() {
        return data;
    }

    public static class WithdrawBean implements Serializable {
        private String id;
        private String uid;
        private String remark;
        private String op_point;
        private String created_at;
        private String updated_at;
        private String ltype;
        private String point_new;
        private String ref_id;

        public String getId() {
            return id;
        }

        public String getUid() {
            return uid;
        }

        public String getRemark() {
            return remark;
        }

        public String getOp_point() {
            return op_point;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getLtype() {
            return ltype;
        }

        public String getPoint_new() {
            return point_new;
        }

        public String getRef_id() {
            return ref_id;
        }
    }



}
