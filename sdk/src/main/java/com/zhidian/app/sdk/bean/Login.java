package com.zhidian.app.sdk.bean;

import com.zhidian.app.sdk.http.BaseRequest;

/**
 * Created by mocf on 2017/7/17.
 */
public class Login{
    public String userName;
    public String passWord;

    public static class LoginRequest extends BaseRequest{
        public String userName;
        public String passWord;
    }

//    public static class LoginResult{
//        public String userName;
//        public String passWord;
//    }
}
