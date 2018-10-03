package cn.bashiquan.cmj.sdk.event.HomeEvent;

import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/9/27.
 * 添加完 照片之后 进入任务 待审核页
 */

public class AddPicCloseEvent extends BaseEvent {

    private int flag; // 1 mainAct finish

    public  AddPicCloseEvent(int flag){
        this.flag = flag;
    }
    public  AddPicCloseEvent(){
        this.flag = flag;
    }

    public int getFlag(){
        return flag;
    }
}
