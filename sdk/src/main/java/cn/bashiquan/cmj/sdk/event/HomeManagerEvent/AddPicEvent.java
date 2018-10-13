package cn.bashiquan.cmj.sdk.event.HomeManagerEvent;

import cn.bashiquan.cmj.sdk.bean.MediaPicBean;
import cn.bashiquan.cmj.sdk.bean.AddPicBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/9/27.
 * 添加照片
 */

public class AddPicEvent extends BaseEvent {

    public enum EventType {
        GET_ADD_PIC_SUCCESS,
        GET_ADD_PIC_FAILED
    }
    private EventType eventType;
    private String imageName; // 图片的名字
    private AddPicBean addPicBean;
    private MediaPicBean addMediaPicBean;

    public AddPicEvent(EventType eventType,String imageName,AddPicBean addPicBean){
        this.eventType = eventType;
        this.imageName = imageName;
        this.addPicBean = addPicBean;
    }
    public AddPicEvent(EventType eventType,String imageName,MediaPicBean addMediaPicBean){
        this.eventType = eventType;
        this.imageName = imageName;
        this.addMediaPicBean = addMediaPicBean;
    }

    public AddPicEvent(EventType eventType,String msg){
        this.eventType = eventType;
        this.msg = msg;
    }

    public String getImageName() {
        return imageName;
    }

    public AddPicBean getAddPicBean() {
        return addPicBean;
    }

    public EventType getEventType(){
        return eventType;
    }

    public MediaPicBean getAddMediaPicBean() {
        return addMediaPicBean;
    }
}
