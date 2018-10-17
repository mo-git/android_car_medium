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
    // 获取消费码
    void getorderToken(String id);
    // 退款
    void rufundOrder(String id);
    //获取抽奖记录
    void getLuckList(int limit,int offset,String type);
    // 参与抽奖
    void luckjoin();
    // 参与抽奖的人
    void getLuckjoinUser();

    //获取积分明细列表
    void getIntegralList(int limit,int offset);
    // 获取体现的xiane
    void getWithdrawlimit();
    // 兑换金额
    void withdrawMoney(String money);
    // 获取提现的明细
    void getWithdrawList(int limit,int offset);


}
