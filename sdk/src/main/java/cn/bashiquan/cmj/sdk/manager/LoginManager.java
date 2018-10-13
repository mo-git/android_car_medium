package cn.bashiquan.cmj.sdk.manager;


import cn.bashiquan.cmj.sdk.bean.UserBean;

/**
 * Created by mocf on 2018/9/26.
 */
public interface LoginManager extends BaseManager {
    /**
     * 登陆
     * @param userName
     * @param passward
     */
    public void login(String userName, String passward, Class login);

    public void getUserInfo();
}
