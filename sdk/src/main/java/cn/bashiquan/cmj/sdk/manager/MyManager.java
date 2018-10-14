package cn.bashiquan.cmj.sdk.manager;

/**
 * Created by mo on 2018/9/27.
 * 我的页面接口管理
 */

public interface MyManager extends BaseManager{

    // 获取验证码
    void getVerifyCode(String mobile);

}
