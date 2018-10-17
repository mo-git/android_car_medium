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
        private LuckconfigBean luck_config;
//                 "award_product_id":null,
//                 "order_num":null,
//                 "award_product":null,
//                 "award":null
        private String id;
        private String uid;
        private String luck_id;
        private String vresult;
        private String nums;
        private String awardname;
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
    }

    public static class LuckconfigBean implements Serializable {
        private OpenconfigBean open_config;
        private String name;
        private String id;
        private String join_num;
        private String end_time;
//        luck_result

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
