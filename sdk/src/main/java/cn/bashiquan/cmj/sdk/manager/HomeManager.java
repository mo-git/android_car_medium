package cn.bashiquan.cmj.sdk.manager;


import cn.bashiquan.cmj.sdk.bean.BannersBean;

/**
 * Created by mocf on 2018/9/26.
 * 首页接口管理
 */
public interface HomeManager extends BaseManager {
    /**
     * 获取轮播图
     *
     */
    public void getBannerImages(Class bannersBean);


}
