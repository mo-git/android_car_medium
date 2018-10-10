package cn.bashiquan.cmj.sdk.http;

/**
 * Created by Alan on 2015/6/10.
 */
public class RequestUrl {

    /**
     *  获取banner图
     */
    public static final String BANNER_URL = "/getIndexTopImg?city=%E6%A1%82%E6%9E%97";
    /**
     * 添加媒体上传照片
     */
    public static final String ADD_MEDIA_PIC_URL = "/adspace/plateNumberGet?image";
    /**
     * 添加任务照片
     */
    public static final String ADD_TASK_PIC_URL = "/user/uploadimg?image";
    /**
     * 添加任务照片后 提交任务数据
     */
    public static final String ADD_TASK_SUBMIT_URL = "/task/monitor_img";
    /**
     * 获取车辆类型
     */
    public static final String GET_CAR_TYPE_URL = "/adspace/type";
    /**
     * 添加媒体
     */
    public static final String ADD_MEDIA = "/adspace/add";

    /**
     * 获取车辆列表
     * @return
     */
    public static String getMediaListUrl(int liimit,int offset){
//        adspace/list?limit=10&offset=0
        String url = "/adspace/list?limit="
                + liimit
                + "&offset="
                + offset;
        return url;
    }

    // 取消任务
    public static String getCancleTaskUrl(int id){
//        adspace/delTask?adspace_id=13
        String url = "/adspace/delTask?adspace_id="
                + id;
        return url;
    }


    // 获取广告类型列表
    public static String getAdListUrl(int cardId){
//        adspace/adlist?adspace_id=13;
        String url = "/adspace/adlist?adspace_id="
                + cardId;
        return url;
    }
    // 广告列表页点击下一步
    public static String getAddTaskUrl(int id,int ad_id){
//        task/add?adspace_id=13&ad_id=11
//        task/add?adspace_id=13task/add?adspace_id=11
        String url = "/task/add?adspace_id="
                + id
                + "&ad_id="
                + ad_id;
        return url;
    }

    // 获取任务周期
    public static String getTaskListUrl(String taskId){
//        task/list_item?id=88
        String url = "/task/list_item?id="
                + taskId;
        return url;
    }

    // 获取任务详情
    public static String getTaskInfotUrl(int id){
//        /task/getTaskInfo?id=211
        String url = "/task/getTaskInfo?id="
                + id;
        return url;
    }
 // 判断车牌号是否有效
    public static String getCardNumRealUrl(String cardNum){
//        /adspace/checkcarnumber?car_number=
        String url = "/adspace/checkcarnumber?car_number="
                + cardNum;
        return url;
    }

}
