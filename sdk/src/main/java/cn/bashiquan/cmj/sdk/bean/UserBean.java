package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;

/**
 * Created by mo on 2018/9/26.
 */

public class UserBean implements Serializable{
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
    private int reg_type;

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

    public int getReg_type() {
        return reg_type;
    }
}
