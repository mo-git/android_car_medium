package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mocf on 2018/9/26.
 */
public class TaskInfoReposeBean implements Serializable{
    private boolean state;
    private String msg;
    private int code;
    private TaskInfoBean data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public TaskInfoBean getData() {
        return data;
    }

    public static class TaskInfoBean implements Serializable{
        private String remark;
        private List<PicPath> imgs;

        public String getRemark() {
            return remark;
        }

        public List<PicPath> getImgs() {
            return imgs;
        }
    }

    public static class PicPath{
        private String path;
        private String url;

        public String getUrl() {
            return url;
        }

        public String getPath() {
            return path;
        }
    }

}
