package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

import cn.bashiquan.cmj.sdk.http.BaseRequest;

/**
 * Created by mocf on 2018/9/26.
 */
public class MediaListBean implements Serializable{
    private boolean state;
    private String msg;
    private int code;
    private Data data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public class Data {
        private List<MediaBean> list;
        public void setList(List<MediaBean> list) {
            this.list = list;
        }
        public List<MediaBean> getList() {
            return list;
        }

    }

    public class Face_imgs {
        private String path;
        public void setPath(String path) {
            this.path = path;
        }
        public String getPath() {
            return path;
        }

    }

    public class MediaBean {

        private int id;
        private int uid;
        private String realname;
        private String mobile;
        private String car_number;
        private List<Face_imgs> face_imgs;
        private String created_at;
        private String updated_at;
        private String province;
        private String city;
        private String district;
        private int ctype;
        private String cend_time;
        private int check_result;
        private String desc_log;
        private String ad_name;
        private String task_id;
        private String end_time;
        private String type;
        private boolean able_upload;
        private boolean able_del;
        private User user;

        public int getId() {
            return id;
        }

        public int getUid() {
            return uid;
        }

        public String getRealname() {
            return realname;
        }

        public String getMobile() {
            return mobile;
        }

        public String getCar_number() {
            return car_number;
        }

        public List<Face_imgs> getFace_imgs() {
            return face_imgs;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getDistrict() {
            return district;
        }

        public int getCtype() {
            return ctype;
        }

        public String getCend_time() {
            return cend_time;
        }

        public int getCheck_result() {
            return check_result;
        }

        public String getDesc_log() {
            return desc_log;
        }

        public String getAd_name() {
            return ad_name;
        }

        public String getTask_id() {
            return task_id;
        }

        public String getEnd_time() {
            return end_time;
        }

        public String getType() {
            return type;
        }

        public boolean isAble_upload() {
            return able_upload;
        }

        public boolean isAble_del() {
            return able_del;
        }

        public User getUser() {
            return user;
        }
    }

    public class User {

        private int id;
        private String mobile;
        private String realname;
        private String nickname;
        private String country;
        private String province;
        private String city;
        private String created_at;
        private String updated_at;
        private int gender;
        private String avatar_url;
        private int point;
        private int ref_uid;
        private String qrcode;
        private int is_mobile_valid;
        private int able_withdraw;
        private String ad_manager;
        private String keyman;

        public int getId() {
            return id;
        }

        public String getMobile() {
            return mobile;
        }

        public String getRealname() {
            return realname;
        }

        public String getNickname() {
            return nickname;
        }

        public String getCountry() {
            return country;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public int getGender() {
            return gender;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public int getPoint() {
            return point;
        }

        public int getRef_uid() {
            return ref_uid;
        }

        public String getQrcode() {
            return qrcode;
        }

        public int getIs_mobile_valid() {
            return is_mobile_valid;
        }

        public int getAble_withdraw() {
            return able_withdraw;
        }

        public String getAd_manager() {
            return ad_manager;
        }

        public String getKeyman() {
            return keyman;
        }
    }
}