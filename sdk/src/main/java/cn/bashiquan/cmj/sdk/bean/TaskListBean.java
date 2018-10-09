package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mocf on 2018/9/26.
 */
public class TaskListBean implements Serializable{
    private boolean state;
    private String msg;
    private int code;
    private List<TaskBean> data;


    public static class TaskBean implements Serializable{

    }

}
