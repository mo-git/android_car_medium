package cn.bashiquan.cmj.sdk.event.HomeManagerEvent;

import cn.bashiquan.cmj.sdk.bean.CarTypeListBean;
import cn.bashiquan.cmj.sdk.bean.TaskInfoReposeBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/9.
 */

public class AddMeidaEvent extends BaseEvent{
    public enum EventType {
        CHECK_CAR_NUM_SUCCESS,
        CHECK_CAR_NUM_FAILED,
        ADD_MEDIA_SUCCESS,
        ADD_MEDIA_FAILED,
        GET_CAR_TYPE_SUCCESS,
        GETE_CAR_TYPE_FAILED
    }

    private TaskInfoReposeBean taskInfoReposeBean;
    private CarTypeListBean carTypeListBean;
    private EventType eventType;

    public AddMeidaEvent(EventType eventType, String msg, TaskInfoReposeBean taskInfoReposeBean){
        this.eventType = eventType;
        this.msg = msg;
        this.taskInfoReposeBean = taskInfoReposeBean;
    }
    public AddMeidaEvent(EventType eventType,CarTypeListBean carTypeListBean){
        this.eventType = eventType;
        this.carTypeListBean = carTypeListBean;
    }

    public CarTypeListBean getCarTypeListBean() {
        return carTypeListBean;
    }

    public TaskInfoReposeBean getTaskInfoReposeBean() {
        return taskInfoReposeBean;
    }

    public EventType getEventType() {
        return eventType;
    }
}
