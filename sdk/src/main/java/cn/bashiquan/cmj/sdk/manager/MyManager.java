package cn.bashiquan.cmj.sdk.manager;

/**
 * Created by mo on 2018/9/27.
 * 我的页面接口管理
 */

public interface MyManager extends BaseManager{

    /**
     * text
     * @param userName
     * @param passward
     */
    public void text(String userName, String passward, Class login);
}
