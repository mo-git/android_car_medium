package cn.bashiquan.cmj.sdk.event.TaskManagerEvent;

import cn.bashiquan.cmj.sdk.bean.TaskFrbListBean;
import cn.bashiquan.cmj.sdk.bean.TaskInfoReposeBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/10.
 */

public class TaskFrgListEvent extends BaseEvent{
    public enum EventType {
        GET_TASKFRG_SUCCESS,
        GET_TASKFRG_FAILED,
    }

    private TaskFrbListBean taskFrbListBean;
    private TaskFrgListEvent.EventType eventType;

    public TaskFrgListEvent(TaskFrgListEvent.EventType eventType, String msg, TaskFrbListBean taskFrbListBean){
        this.eventType = eventType;
        this.msg = msg;
        this.taskFrbListBean = taskFrbListBean;
    }
    public TaskFrgListEvent(TaskFrgListEvent.EventType eventType, String msg){
        this.eventType = eventType;
        this.msg = msg;
    }

    public TaskFrbListBean getTaskFrbListBean() {
        return taskFrbListBean;
    }

    public TaskFrgListEvent.EventType getEventType() {
        return eventType;
    }
}
