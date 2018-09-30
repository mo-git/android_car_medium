package cn.bashiquan.cmj.sdk.event.HomeEvent;

import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/9/27.
 * 添加媒体照片
 */

public class AddPicEvent extends BaseEvent {

    public enum EventType {
        GET_ADD_PIC_SUCCESS,
        GET_ADD_PIC_FAILED,
    }
    private EventType eventType;
    private String imageName; // 图片的名字
    private String imageUrl;// 图片的连接

    public AddPicEvent(EventType eventType,String imageName,String imageUrl){
        this.eventType = eventType;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public EventType getEventType(){
        return eventType;
    }
}
