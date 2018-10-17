package cn.bashiquan.cmj.sdk.event.MyManager;

import cn.bashiquan.cmj.sdk.bean.JoinUserBean;
import cn.bashiquan.cmj.sdk.bean.MyDrawListBean;
import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/10/13.
 */

public class DrawEvent extends BaseEvent {
    public enum EventType{
        GET_DRAWLIST_SUCCESS,
        GET_DRAWLIST_FAILED,
        JOIN_DRAW_SUCCESS,
        JOIN_DRAW_FAILED,
        GET_JOIN_USER_SUCCESS,
        GET_JOIN_USER_FAILED;
    }

    private EventType event;
    private JoinUserBean joinUserBean;
    private MyDrawListBean myDrawListBean;
    public DrawEvent(EventType event, JoinUserBean joinUserBean){
        this.event = event;
        this.joinUserBean = joinUserBean;
    }
    public DrawEvent(EventType event, MyDrawListBean myDrawListBean){
        this.event = event;
        this.myDrawListBean = myDrawListBean;
    }
 public DrawEvent(EventType event, String msg){
        this.event = event;
        this.msg = msg;
    }

    public MyDrawListBean getMyDrawListBean() {
        return myDrawListBean;
    }

    public JoinUserBean getJoinUserBean() {
        return joinUserBean;
    }

    public EventType getEvent() {
        return event;
    }
}
