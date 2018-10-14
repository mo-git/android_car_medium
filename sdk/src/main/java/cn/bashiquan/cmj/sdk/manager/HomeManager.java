package cn.bashiquan.cmj.sdk.manager;


import cn.bashiquan.cmj.sdk.bean.BannersBean;
import cn.bashiquan.cmj.sdk.http.BaseRequest;

/**
 * Created by mocf on 2018/9/26.
 * 首页接口管理
 */
public interface HomeManager extends BaseManager {
    // 获取轮播图
    void getBannerImages(Class bannersBean,String city);

    /**********************媒体信息*********************/

    // 获取媒体信息列表
    void getMeidList(int limit, int offSet);

    //取消任务
    void cancleTask(int id, String carNum);

    // 根据车辆id 获取广告列表
    void getAdList(int carId);

    // 广告列表页 点击下一步
    void addTask(int id, int ad_id);

    // 获取任务周期
    void getTaskList(String taskId);

    // 获取任务详情
    void getTaskInfo(int id);

    // 提交任务
    void submitTask(BaseRequest request);


    /*************************添加媒体*************************/

    //添加媒体 上传图片
    void uplodeMediaImage(Class resposeBean, String imagePath, String imageName);

    // 判断车牌号是否有效
    void checkCarNumReal(String carNum);

    // 判断车牌号是否有效
    void getCarType();

    // 添加媒体
    void addMedia(BaseRequest request);

    //添加上传图片
    void uplodeTaskImage(Class resposeBean, String imagePath, String imageName);

    /**********************积分商城***************/
    // 获取积分商城列表
    void getProductList(int limit, int offset, String keyword);

    // 获取产品详情
    void getProductInfo(int id);

    // 支付
    void payProduct(int id, String key, String num);



}
