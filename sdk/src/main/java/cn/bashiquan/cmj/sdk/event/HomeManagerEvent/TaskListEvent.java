package cn.bashiquan.cmj.sdk.event.HomeManagerEvent;

import cn.bashiquan.cmj.sdk.bean.TaskListBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/9.
 */

public class TaskListEvent extends BaseEvent{
    public enum EventType {
        GET_TASK_SUCCESS,
        GET_TASK_FAILED
    }

    private TaskListBean taskListBean;
    private EventType eventType;

    public TaskListEvent(EventType eventType, String msg,TaskListBean taskListBean){
        this.eventType = eventType;
        this.msg = msg;
        this.taskListBean = taskListBean;
    }


    public TaskListBean getTaskListBean() {
        return taskListBean;
    }

    public EventType getEventType() {
        return eventType;
    }
}
