package cn.bashiquan.cmj.sdk.bean;

import cn.bashiquan.cmj.sdk.http.BaseRequest;

/**
 * Created by mocf on 2018/9/26.
 */
public class LoginBean {

    private boolean state; //":true,
    private String msg; //:"登录成功",
    private int code; //":200,
    private LoginResponce data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public LoginResponce getData() {
        return data;
    }

    public static class LoginResponce {
        private int user_id; //":990,
        private String token; //":"eyJpdiI6ImYwbXZVenZjUE1IQXBsSTRZb0k0eHc9PSIsInZhbHVlIjoielphNHVHRVZpbjNxak5FYzJyQ3BtOTFjRzE0RXZ5OGJmekRyTCtWTWlibUtsREVxVENhRnRPaVkxTDk2QlwvMVduaFhvUFoxOTRvV09vTkRlM3paYWhnPT0iLCJtYWMiOiI0MTQ4MDVkNmMyODBlZjUyZDZiN2ZiOGYwZjU0NTM1NTA1YjFlYjQxZGQwNjJhNDEzNjViZTM1OGUyMmMzN2VjIn0=",
        private String realname; //":null

        public int getUser_id() {
            return user_id;
        }

        public String getToken() {
            return token;
        }

        public String getRealname() {
            return realname;
        }
    }


}
