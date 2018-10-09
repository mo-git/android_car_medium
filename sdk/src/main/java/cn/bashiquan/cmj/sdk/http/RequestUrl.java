package cn.bashiquan.cmj.sdk.http;

/**
 * Created by Alan on 2015/6/10.
 */
public class RequestUrl {

    /**
     *  获取banner图
     */
    public static final String BANNER_URL = "getIndexTopImg?city=%E6%A1%82%E6%9E%97";
    /**
     * 添加媒体上传照片
     */
    public static final String ADD_MEDIA_PIC_URL = "adspace/plateNumberGet?image";

    /**
     * 获取车辆列表
     * @return
     */
    public static String getMediaListUrl(int liimit,int offset){
//        adspace/list?limit=10&offset=0
        String url = "adspace/list?limit="
                + liimit
                + "&offset="
                + offset;
        return url;
    }

    // 取消任务
    public static String getCancleTaskUrl(int id){
//        adspace/delTask?adspace_id=13
        String url = "adspace/delTask?adspace_id="
                + id;
        return url;
    }


    // 获取广告类型列表
    public static String getAdListUrl(int cardId){
//        adspace/adlist?adspace_id=13;
        String url = "adspace/adlist?adspace_id="
                + cardId;
        return url;
    }
    // 广告列表页点击下一步
    public static String getAddTaskUrl(int id,int ad_id){
//        task/add?adspace_id=13&ad_id=11
//        task/add?adspace_id=13task/add?adspace_id=11
        String url = "task/add?adspace_id="
                + id
                + "&ad_id="
                + ad_id;
        return url;
    }

    // 获取任务周期
    public static String getTaskListUrl(String taskId){
//        task/list_item?id=88
        String url = "task/list_item?id="
                + taskId;
        return url;
    }


}
