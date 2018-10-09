package cn.bashiquan.cmj.sdk.manager;


import cn.bashiquan.cmj.sdk.bean.BannersBean;

/**
 * Created by mocf on 2018/9/26.
 * 首页接口管理
 */
public interface HomeManager extends BaseManager {
     // 获取轮播图

    void getBannerImages(Class bannersBean);

    /**********************媒体信息*********************/

    // 获取媒体信息列表
    void getMeidList(int limit,int offSet);

    //取消任务
    void cancleTask(int id,String carNum);

    // 根据车辆id 获取广告列表
    void getAdList(int carId);

    // 广告列表页 点击下一步
    void addTask(int id,int ad_id);

    // 获取任务周期
    void getTaskList(String taskId);



     //添加媒体 上传图片
    void uplodeImage(Class resposeBean,String imagePath,String imageName);



    /***************微信*******************************/
    /**
     * 获取微信的token
     * @param code 微信授权的code
     */
    void getAccess_token(Class wXTokenBean,String code);

    /**
     * 获取微信的个人信息
     * @param access_token
     * @param openid
     */
    void getUserMesg(Class wXUserBean,String access_token,String openid);

}
