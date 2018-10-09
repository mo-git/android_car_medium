package cn.bashiquan.cmj.sdk.event.HomeEvent;

import cn.bashiquan.cmj.sdk.bean.AdListBean;
import cn.bashiquan.cmj.sdk.bean.MediaListBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/9/27.
 */

public class AdListEvent extends BaseEvent {
    public enum EventType {
        GET_AD_SUCCESS,
        GET_AD_FAILED,
        ADD_TASK_SUCCESS,
        ADD_TASK_FAILED
    }
    public AdListBean adListBean;
    private String taskId; // 提交之后返回任务id
    private EventType eventType;
    public AdListEvent(EventType eventType, String msg, AdListBean adListBean){
        this.adListBean = adListBean;
        this.eventType = eventType;
        this.msg = msg;

    }

    public AdListEvent(EventType eventType, String taskId){
        this.taskId = taskId;
        this.eventType = eventType;
        this.msg = msg;

    }

    public String getTaskId() {
        return taskId;
    }

    public AdListBean getAdListBean() {
        return adListBean;
    }

    public EventType getEventType() {
        return eventType;
    }
}
