package cn.bashiquan.cmj.sdk.event.TaskManagerEvent;

import cn.bashiquan.cmj.sdk.bean.NoticeListBean;
import cn.bashiquan.cmj.sdk.bean.TaskFrbListBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/10.
 */

public class NoticeListEvent extends BaseEvent{
    public enum EventType {
        GET_NOTICELIST_SUCCESS,
        GET_NOTICELIST_FAILED,
        GET_NOTICE_INFO_SUCCESS,
        GET_NOEICT_INFO_FAILED;
    }

    private NoticeListBean noticeListBean;
    private NoticeListEvent.EventType eventType;

    public NoticeListEvent(NoticeListEvent.EventType eventType, NoticeListBean noticeListBean){
        this.eventType = eventType;
        this.msg = msg;
        this.noticeListBean = noticeListBean;
    }
    public NoticeListEvent(NoticeListEvent.EventType eventType, String msg){
        this.eventType = eventType;
        this.msg = msg;
    }

    public NoticeListBean getNoticeListBean() {
        return noticeListBean;
    }

    public NoticeListEvent.EventType getEventType() {
        return eventType;
    }
}
