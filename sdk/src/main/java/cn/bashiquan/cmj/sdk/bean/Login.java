package cn.bashiquan.cmj.sdk.bean;

import cn.bashiquan.cmj.sdk.http.BaseRequest;

/**
 * Created by mocf on 2018/9/26.
 */
public class Login{
    public String userName;
    public String passWord;

    public static class LoginRequest extends BaseRequest {
        public String userName;
        public String passWord;
    }

//    public static class LoginResult{
//        public String userName;
//        public String passWord;
//    }
}
