package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;

/**
 * Created by mocf on 2018/9/26.
 */
public class AddMediaPicBeanFailed implements Serializable{
    private boolean state;
    private String code;
    private MediaPicBean msg;

    public boolean isState() {
        return state;
    }

    public String getCode() {
        return code;
    }

    public MediaPicBean getMsg() {
        return msg;
    }
}
