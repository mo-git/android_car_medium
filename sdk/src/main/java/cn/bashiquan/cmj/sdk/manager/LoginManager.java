package cn.bashiquan.cmj.sdk.manager;


import cn.bashiquan.cmj.sdk.bean.UserBean;
import cn.bashiquan.cmj.sdk.http.BaseRequest;

/**
 * Created by mocf on 2018/9/26.
 */
public interface LoginManager extends BaseManager {


    // 获取微信的token  微信授权的code
    void getAccess_token(String code);

    // 获取微信的个人信息  @param access_token  @param openid
    void getWxUserMesg(String access_token, String openid);

    // 登陆
    public void login(String unionid);

    // 获取用户信息
    void getUserInfo();

    // 刷新token
    void refreshTodek();

    void regist(BaseRequest request);

}
