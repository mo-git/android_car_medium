package cn.bashiquan.cmj.sdk.manager;



/**
 * Created by mocf on 2018/9/26.
 */
public interface TaskManager extends BaseManager {
    /**
     * 获取任务列表
     */
    public void getTaskList(int state,int limit,int offset);

}
