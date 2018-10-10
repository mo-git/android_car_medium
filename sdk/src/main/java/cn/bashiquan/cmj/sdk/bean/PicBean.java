package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;

/**
 * Created by mo on 2018/10/10.
 */

public class PicBean implements Serializable {

    private String path;
    private String url;

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }
}
