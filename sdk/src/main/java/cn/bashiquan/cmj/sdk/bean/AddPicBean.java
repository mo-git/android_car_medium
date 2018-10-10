package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mocf on 2018/9/26.
 */
public class AddPicBean implements Serializable{
    private boolean state;
    private String msg;
    private int code;
    private PicBean data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public PicBean getData() {
        return data;
    }

    public static class PicBean implements Serializable{
        private String url;
        private String path;

        public String getUrl() {
            return url;
        }

        public String getPath() {
            return path;
        }
    }
}
