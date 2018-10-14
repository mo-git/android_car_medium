package cn.bashiquan.cmj.sdk.bean;

import cn.bashiquan.cmj.sdk.http.BaseRequest;

/**
 * Created by mo on 2018/10/14.
 */

public class RegistRequestBean extends BaseRequest {

    private RegistBean info;

    public RegistBean getInfo() {
        return info;
    }

    public void setInfo(RegistBean info) {
        this.info = info;
    }

    public static class RegistBean {

        private String nickname;
        private String openid;
        private String unionid;
        private String city;
        private String province;
        private String country;
        private String gender;
        private String avatar_url;
        private String mobile;
        private String code;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
