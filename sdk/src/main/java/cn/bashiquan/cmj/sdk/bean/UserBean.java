package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;

/**
 * Created by mo on 2018/9/26.
 */

public class UserBean implements Serializable {


    private boolean state; //":true,
    private String msg; //":"获取用户信息",
    private int code; //":200,
    private UserDataBean data;//":{

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public UserDataBean getData() {
        return data;
    }

    public static class UserDataBean implements Serializable {


        private String image; //":"https://wx.qlogo.cn/mmopen/vi_32/T3iaP7PA5jO0vzZmjyeVrUEBKsbZPSCSxyqPBqBgpoaTgLBicILXk1LJq1CMaksyBl1bm5L8lK5zUia68nxrZe7iaw/132",
        private String realname; //":"刘园",
        private String mobile; //":"13146791496",
        private int point; //":0,
        private int adspace_num; //":5,
        private int is_mobile_valid; //":0,
        private int task_num; //":3,
        private boolean is_manager; //":false,
        private boolean is_keyman; //":false

        public String getImage() {
            return image;
        }

        public String getRealname() {
            return realname;
        }

        public String getMobile() {
            return mobile;
        }

        public int getPoint() {
            return point;
        }

        public int getAdspace_num() {
            return adspace_num;
        }

        public int getIs_mobile_valid() {
            return is_mobile_valid;
        }

        public int getTask_num() {
            return task_num;
        }

        public boolean is_manager() {
            return is_manager;
        }

        public boolean is_keyman() {
            return is_keyman;
        }
    }

}
