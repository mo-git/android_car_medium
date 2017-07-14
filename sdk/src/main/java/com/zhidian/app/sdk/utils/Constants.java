package com.zhidian.app.sdk.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by mocf on 2017/7/13.
 */
public class Constants {

    public static String SERVER_ADDR = "api.tx2010.com";
    public static Integer SERVER_PORT = 443;//80

    public static final String OS_NAME = "android";
    public static final String MSG_CANNOT_CONNECT_TO_SERVER = "无法连接服务器";

    public class RESPONSE_STATUS {
        public static final int OK = 200;
        public static final int NOT_FOUND = 404;
        public static final int NOT_ALLOWED = 403;
        public static final int ERROR = 500;

        public static final int ALREADY_DEAL = 999;//重复操作
        public static final int KICK_OFF = 1001; //被T
        public static final int TOKEN_EXPIRED = 1002;//TOKEN过期
    }
}
