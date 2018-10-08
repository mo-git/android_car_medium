package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;

/**
 * Created by mo on 2018/10/8.
 * 微信的user信息
 */

public class WXUserBean implements Serializable {

    private String openid; //":"oouW51aUvNcWo_o3zmN4knkg-2Xw",
    private String nickname; //":"老默",
    private int sex; //":0,
    private String language; //":"zh_CN",
    private String city;//":"",
    private String province; //":"",
    private String country; //":"",
    private String headimgurl; //":"",
    //        "privilege":Array[0],
    private String unionid; //":"oZi3s5wRkIMjNC9RkZO6NDbWR1TQ"

    public String getOpenid() {
        return openid;
    }

    public String getNickname() {
        return nickname;
    }

    public int getSex() {
        return sex;
    }

    public String getLanguage() {
        return language;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }
}
