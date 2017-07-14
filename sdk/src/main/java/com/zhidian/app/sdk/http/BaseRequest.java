package com.zhidian.app.sdk.http;

import okio.ByteString;

/**
 * Created by mocf on 2017/7/13.
 */
public class BaseRequest {

    public String url;  //url
    public ByteString body;//参数
    public String token;
    public String version; // aoo版本
    public String osName; // 客户端 ios or android
    public String osVersion; // 手机版本



}
