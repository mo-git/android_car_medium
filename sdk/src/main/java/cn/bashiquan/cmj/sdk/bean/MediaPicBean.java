package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;

/**
 * Created by mocf on 2018/9/26.
 */
public class MediaPicBean implements Serializable {

    private boolean state;
    private String num;
    private PicBean img;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }


    public String getNum() {
        return num;
    }

    public PicBean getImg() {
        return img;
    }
}
