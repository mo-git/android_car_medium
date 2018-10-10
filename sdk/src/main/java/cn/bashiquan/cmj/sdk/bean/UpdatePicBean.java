package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;

/**
 * Created by mo on 2018/9/30.
 * 添加图片时使用
 */

public class UpdatePicBean implements Serializable{
    private String imageName; // 图片的名字
    private String imagePath;  //  图片的本地path
    private String imageUrl; // 图片的url
    private String imageUrlPath; // 图片的urlPath
    private boolean isUploadSuccess; // 是否上传成功
    private boolean isSuccessCarNum; // 是识别成功
    private String  carNum;// 识别的车牌号

    public String getImageUrlPath() {
        return imageUrlPath;
    }

    public void setImageUrlPath(String imageUrlPath) {
        this.imageUrlPath = imageUrlPath;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public boolean isSuccessCarNum() {
        return isSuccessCarNum;
    }

    public void setSuccessCarNum(boolean successCarNum) {
        isSuccessCarNum = successCarNum;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isUploadSuccess() {
        return isUploadSuccess;
    }

    public void setUploadSuccess(boolean uploadSuccess) {
        isUploadSuccess = uploadSuccess;
    }
}
