package cn.bashiquan.cmj.sdk.bean;

import java.util.List;

import cn.bashiquan.cmj.sdk.http.BaseRequest;

/**
 * Created by mo on 2018/10/9.
 */

public class RequestSubmitTaskBean extends BaseRequest {
     private int id; //":"211",
     private List<PicPath> imgs;
     private String address; //":"广西壮族自治区桂林市秀峰区五美路",
     private String lng; //":"25.273609,110.290024"

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PicPath> getImgs() {
        return imgs;
    }

    public void setImgs(List<PicPath> imgs) {
        this.imgs = imgs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public static  class PicPath{
        private String path; //":"cmjtemp/18-9/29/8c485b0dc77a2b8a45f0b94d9def86a8.jpeg"

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
