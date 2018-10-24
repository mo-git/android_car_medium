package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mocf on 2018/9/26.
 */
public class RangListBean implements Serializable{
    private List<RangBean> data;

    public List<RangBean> getData() {
        return data;
    }

    public void setData(List<RangBean> data) {
        this.data = data;
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

        public void setSum(String sum) {
            this.sum = sum;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }
    }

    public static class UserBean implements Serializable {
        private String nickname;
        private int id;
        private String avatar_url;


        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

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
