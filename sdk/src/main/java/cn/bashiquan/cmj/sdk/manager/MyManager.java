package cn.bashiquan.cmj.sdk.manager;

import cn.bashiquan.cmj.sdk.http.BaseRequest;

/**
 * Created by mo on 2018/9/27.
 * 我的页面接口管理
 */

public interface MyManager extends BaseManager{

    // 获取验证码
    void getVerifyCode(String mobile);
    // 验证用户
    void VerifyUser(String realname,String mobile,String code);
    // 提交报单
    void submitTaxation(BaseRequest request);
    // 获取订单列表
    void getOrderList(int limit,int offset,String keyword);

}
