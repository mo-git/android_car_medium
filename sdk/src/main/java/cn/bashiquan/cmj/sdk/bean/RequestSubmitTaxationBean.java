package cn.bashiquan.cmj.sdk.bean;

import java.util.List;

import cn.bashiquan.cmj.sdk.http.BaseRequest;

/**
 * Created by mo on 2018/10/9.
 */

public class RequestSubmitTaxationBean extends BaseRequest {
    private String name;
    private String mobile;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
