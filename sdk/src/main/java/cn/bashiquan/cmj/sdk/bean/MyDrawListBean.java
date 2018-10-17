package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mo on 2018/10/15.
 */

public class MyDrawListBean implements Serializable {

    private boolean state;
    private String msg;
    private String code;
    private DrawDataBean data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public DrawDataBean getData() {
        return data;
    }

    public static class DrawDataBean implements Serializable {
        private List<DrawBean> luck;

        public List<DrawBean> getLuck() {
            return luck;
        }
    }

    public static class DrawBean implements Serializable {
        //                 "award_product":null,
        private AwardBean award;
        private LuckconfigBean luck_config;
        private String award_product_id; // 2 等奖
        private String order_num;
        private String id;
        private String uid;
        private String luck_id;
        private String vresult; // 0 未中奖  1 已中奖
        private String nums;
        private String awardname; //"京津冀"
        private String awardImg; //中奖条码
        private boolean status;

        public LuckconfigBean getLuck_config() {
            return luck_config;
        }

        public String getId() {
            return id;
        }

        public String getUid() {
            return uid;
        }

        public String getLuck_id() {
            return luck_id;
        }

        public String getVresult() {
            return vresult;
        }

        public String getNums() {
            return nums;
        }

        public String getAwardname() {
            return awardname;
        }

        public boolean isStatus() {
            return status;
        }

        public String getAward_product_id() {
            return award_product_id;
        }

        public String getOrder_num() {
            return order_num;
        }

        public String getAwardImg() {
            return awardImg;
        }

        public AwardBean getAward() {
            return award;
        }
    }

    public static class AwardBean implements Serializable {
//        "id":51,
//        "p_id":2,
//        "uid":1,
//        "ref_id":105,
//        "use_time":null,
//        "created_at":"2018-09-03 16:36:00",
//        "updated_at":"2018-09-03 16:36:00",
        private String token; //":"74166148",
//        "atype":4,
//        "p_uid":null,
//        "remark":null,
//        "use_type":0

        public String getToken() {
            return token;
        }
    }
    public static class LuckconfigBean implements Serializable {
        private OpenconfigBean open_config;
        private String name;
        private String id;
        private String join_num;
        private String end_time;
        private List<Integer> luck_result;

        public List<Integer> getLuck_result() {
            return luck_result;
        }

        public OpenconfigBean getOpen_config() {
            return open_config;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getJoin_num() {
            return join_num;
        }

        public String getEnd_time() {
            return end_time;
        }
    }
    public static class OpenconfigBean implements Serializable {
        private String luck_range;
        private String luck_min_num;

        public String getLuck_range() {
            return luck_range;
        }

        public String getLuck_min_num() {
            return luck_min_num;
        }
    }

}
