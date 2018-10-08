package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;

/**
 * Created by mo on 2018/10/8.
 * 微信的token对象
 */

public class WXTokenBean implements Serializable{
    private String access_token; //":"14_yuPZ8a-1OK58_1YZGFjZIvuwiEw_GsRXw9MMTmEUN905mu-PUzXYkcc59p13EsLg6gAJ7S9oqG90P-f6F7NPBXzOz7YvGLCKVKPJTNJJCHc",
    private int expires_in;//":7200,
    private String refresh_token;//":"14_NneuuzyQydw4q_1Rp80qaHJ7UZTqo94Xo4o9i2mvCdmsio65KR5Tvpoq7fGvbK-GL2913phYAypGKVJAO4pe_-p_M73eks1_boKi2NBLfuc",
    private String openid; //":"oouW51aUvNcWo_o3zmN4knkg-2Xw",
    private String scope; //":"snsapi_userinfo",
    private String unionid; //":"oZi3s5wRkIMjNC9RkZO6NDbWR1TQ"

    public String getAccess_token() {
        return access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public String getScope() {
        return scope;
    }

    public String getUnionid() {
        return unionid;
    }


}
