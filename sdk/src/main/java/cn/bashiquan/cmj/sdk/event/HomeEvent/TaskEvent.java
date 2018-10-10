package cn.bashiquan.cmj.sdk.event.HomeEvent;

import cn.bashiquan.cmj.sdk.bean.TaskInfoReposeBean;
import cn.bashiquan.cmj.sdk.bean.TaskListBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/9.
 */

public class TaskEvent extends BaseEvent{
    public enum EventType {
        GET_TASKINFO_SUCCESS,
        GET_TASKINFO_FAILED,
        GET_SUBMIT_SUCCESS,
        GET_SUBMIT_FAILED
    }

    private TaskInfoReposeBean taskInfoReposeBean;
    private EventType eventType;

    public TaskEvent(EventType eventType, String msg, TaskInfoReposeBean taskInfoReposeBean){
        this.eventType = eventType;
        this.msg = msg;
        this.taskInfoReposeBean = taskInfoReposeBean;
    }


    public TaskInfoReposeBean getTaskInfoReposeBean() {
        return taskInfoReposeBean;
    }

    public EventType getEventType() {
        return eventType;
    }
}
