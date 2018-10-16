package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mo on 2018/10/16.
 */

public class JoinUserBean implements Serializable {

    private boolean state;
    private String msg;
    private int code;
    private JoinBean data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public JoinBean getData() {
        return data;
    }

    public static class JoinBean implements Serializable {
        private LuckconfigbeBean luck_config;
        private DescribeBean describe;
        private List<LuckbeBean> luck;
        private boolean joinAble;
        private String joinId;

        public LuckconfigbeBean getLuck_config() {
            return luck_config;
        }

        public DescribeBean getDescribe() {
            return describe;
        }

        public List<LuckbeBean> getLuck() {
            return luck;
        }

        public boolean isJoinAble() {
            return joinAble;
        }

        public String getJoinId() {
            return joinId;
        }
    }


    public static class LuckbeBean implements Serializable {
        private JUserBean user;
        private String id;
        private String uid;

        public JUserBean getUser() {
            return user;
        }

        public String getId() {
            return id;
        }

        public String getUid() {
            return uid;
        }
    }

    public static class JUserBean implements Serializable {
        private String id;
        private String avatar_url;

        public String getId() {
            return id;
        }

        public String getAvatar_url() {
            return avatar_url;
        }
    }

    public static class LuckconfigbeBean implements Serializable {
        private int join_num;
        private String vmd5;

        public int getJoin_num() {
            return join_num;
        }

        public String getVmd5() {
            return vmd5;
        }
    }

    public static class DescribeBean implements Serializable {
        private String content;
        private List<String> pics;

        public String getContent() {
            return content;
        }

        public List<String> getPics() {
            return pics;
        }
    }
}
