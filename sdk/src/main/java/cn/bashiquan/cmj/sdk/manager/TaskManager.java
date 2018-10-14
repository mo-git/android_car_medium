package cn.bashiquan.cmj.sdk.manager;



/**
 * Created by mocf on 2018/9/26.
 */
public interface TaskManager extends BaseManager {
    /**
     * 获取任务列表
     */
    public void getTaskList(int state,int limit,int offset);
    // 获取公告列表
    public void getNoticeList(String city,int limit,int offset);
    // 获取公告详情
    public void getNoticeInfo(String city,int id);

}
