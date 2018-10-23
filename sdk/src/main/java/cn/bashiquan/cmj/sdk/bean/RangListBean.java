package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mocf on 2018/9/26.
 */
public class RangListBean implements Serializable{
    private boolean state;
    private String msg;
    private int code;
    private Rang1Bean data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public Rang1Bean getData() {
        return data;
    }

    public static class Rang1Bean implements Serializable{
//        private List<Rang1Bean> ;
    }
    public static class RangBean implements Serializable{
        private String sum;
        private int uid;
        private UserBean user;

        public String getSum() {
            return sum;
        }

        public int getUid() {
            return uid;
        }

        public UserBean getUser() {
            return user;
        }
    }

    public static class UserBean implements Serializable {
        private String nickname;
        private int id;
        private String avatar_url;

        public String getNickname() {
            return nickname;
        }

        public int getId() {
            return id;
        }

        public String getAvatar_url() {
            return avatar_url;
        }
    }
}
