package com.zhidian.app.sdk.manager;

/**
 * Created by mocf on 2017/7/14.
 */
public interface LoginManager extends BaseManager {
    /**
     * 登陆
     * @param userName
     * @param passward
     */
    void login(String userName,String passward);
}
