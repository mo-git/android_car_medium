package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mo on 2018/10/15.
 */

public class IntegralListBean implements Serializable {

private boolean state;
    private String msg;
    private String code;
    private List<IntegralBean> data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public List<IntegralBean> getData() {
        return data;
    }

    public static class IntegralBean implements Serializable{
        private String id;
        private String uid;
//                "admin_uid":null,
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
//    public static class Data_infoBean implements Serializable{
//       private InfoBean info;
//         private int id;
//        private int num;
//        private String uid;
//        private String  idkey;
//        private String kname;
//        private String otype;
//        private String point;
//        private String pitem_id;
//        private String created_at;
//        private String product_id;
//        private String updated_at;
//
//        public InfoBean getInfo() {
//            return info;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public int getNum() {
//            return num;
//        }
//
//        public String getUid() {
//            return uid;
//        }
//
//        public String getIdkey() {
//            return idkey;
//        }
//
//        public String getKname() {
//            return kname;
//        }
//
//        public String getOtype() {
//            return otype;
//        }
//
//        public String getPoint() {
//            return point;
//        }
//
//        public String getPitem_id() {
//            return pitem_id;
//        }
//
//        public String getCreated_at() {
//            return created_at;
//        }
//
//        public String getProduct_id() {
//            return product_id;
//        }
//
//        public String getUpdated_at() {
//            return updated_at;
//        }
//    }
//    public static class InfoBean implements Serializable{
//        private String key;
//        private int idkey;
//        private int price;
//        private int stock;
//        private int sellNum;
//        private String limitBuy;
//
//        public String getKey() {
//            return key;
//        }
//
//        public int getIdkey() {
//            return idkey;
//        }
//
//        public int getPrice() {
//            return price;
//        }
//
//        public int getStock() {
//            return stock;
//        }
//
//        public int getSellNum() {
//            return sellNum;
//        }
//
//        public String getLimitBuy() {
//            return limitBuy;
//        }
//    }
}
