package cn.bashiquan.cmj.sdk.manager;


import cn.bashiquan.cmj.sdk.bean.User;

/**
 * Created by mocf on 2017/7/14.
 */
public interface LoginManager extends BaseManager {
    /**
     * 登陆
     * @param userName
     * @param passward
     */
    public void login(String userName, String passward, Class login);

    public User getCurrentUser();
}
