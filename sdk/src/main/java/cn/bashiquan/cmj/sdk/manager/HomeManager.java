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

    /**
     *添加媒体 上传图片
     */
    public void uplodeImage(Class resposeBean,String imagePath,String imageName);



    /***************微信******************/
    /**
     * 获取微信的token
     * @param code 微信授权的code
     */
    public void getAccess_token(Class wXTokenBean,String code);

    /**
     * 获取微信的个人信息
     * @param access_token
     * @param openid
     */
    public void getUserMesg(Class wXUserBean,String access_token,String openid);

}
