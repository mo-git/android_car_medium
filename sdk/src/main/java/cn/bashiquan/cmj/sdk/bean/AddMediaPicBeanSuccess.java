package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;

/**
 * Created by mocf on 2018/9/26.
 */
public class AddMediaPicBeanSuccess implements Serializable{
    private boolean state;
    private String msg;
    private String code;
    private MediaPicBean data;


    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public MediaPicBean getData() {
        return data;
    }
}
