package cn.bashiquan.cmj.sdk.event.HomeEvent;

import cn.bashiquan.cmj.sdk.bean.BannersBean;
import cn.bashiquan.cmj.sdk.bean.MediaListBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/9/27.
 */

public class MediaListEvent extends BaseEvent {
    public enum EventType {
        GET_MEDIA_SUCCESS,
        GET_MEDIA_FAILED,
        CANCEL_TASK_SUCCESS,
        CANCLE_TASK_FAILED
    }
    public MediaListBean mediaListBean;
    private EventType eventType;
    public MediaListEvent(EventType eventType, String msg,MediaListBean mediaListBean){
        this.mediaListBean = mediaListBean;
        this.eventType = eventType;
        this.msg = msg;

    }

    public MediaListBean getMediaListBean() {
        return mediaListBean;
    }

    public EventType getEventType() {
        return eventType;
    }
}
