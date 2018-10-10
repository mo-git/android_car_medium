package cn.bashiquan.cmj.sdk.bean;

import java.util.List;

import cn.bashiquan.cmj.sdk.http.BaseRequest;

/**
 * Created by mo on 2018/10/9.
 */

public class RequestSubmitmmMediaBean extends BaseRequest {
     private String car_number; //":"211",
     private List<PicPath> face_imgs;
    private String province; //":"广西壮族自治区",
    private String city; //":"桂林市",
    private int ctype; //":5,
    private String district; //":"秀峰区"

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public List<PicPath> getFace_imgs() {
        return face_imgs;
    }

    public void setFace_imgs(List<PicPath> face_imgs) {
        this.face_imgs = face_imgs;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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
