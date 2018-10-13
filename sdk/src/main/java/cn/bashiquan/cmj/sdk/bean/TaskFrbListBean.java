package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mocf on 2018/9/26.
 */
public class TaskFrbListBean implements Serializable {

    private boolean state;
    private String msg;
    private int code;
    private TaskFrgsBean data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public TaskFrgsBean getData() {
        return data;
    }

    public static class TaskFrgsBean implements Serializable {
            private List<TaskFrgBean> list;

        public List<TaskFrgBean> getList() {
            return list;
        }
    }
    public static class TaskFrgBean implements Serializable {
        private AdImage ad_img;
        private Adspace adspace;
        private AdBean ad;
        private int id; //":4308,
        private int ad_id; //":12,
        private String start_time; //":"12月05日",
        private String end_time; //":"12月12日",
        private int uid; //":934,
        private int adspace_id; //":628,
        private int task_id; //":909,
        private String created_at; //":"2018-10-10 09:30:08",
        private String updated_at; //":"2018-10-10 09:30:08",
        private List<AdImage> imgs;
        private String address; //":null,
        private String cstatus; //":"还未开始",
        private String remark; //":null,
        private int admin_uid; //":null,
        private int ad_time_id; //":19,
        private String lng; //":null,
        private String monitor_time; //":null,
        private int batch_id; //":9,
        private String car_number; //":"桂C19889",
        private String name; //":"两侧车门全贴，每个月400元",
        private String type; //":"私家车",
        private boolean is_able_upload; //":false,

        public AdImage getAd_img() {
            return ad_img;
        }

        public Adspace getAdspace() {
            return adspace;
        }

        public AdBean getAd() {
            return ad;
        }

        public int getId() {
            return id;
        }

        public int getAd_id() {
            return ad_id;
        }

        public String getStart_time() {
            return start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public int getUid() {
            return uid;
        }

        public int getAdspace_id() {
            return adspace_id;
        }

        public int getTask_id() {
            return task_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public List<AdImage> getImgs() {
            return imgs;
        }

        public String getAddress() {
            return address;
        }

        public String getCstatus() {
            return cstatus;
        }

        public String getRemark() {
            return remark;
        }

        public int getAdmin_uid() {
            return admin_uid;
        }

        public int getAd_time_id() {
            return ad_time_id;
        }

        public String getLng() {
            return lng;
        }

        public String getMonitor_time() {
            return monitor_time;
        }

        public int getBatch_id() {
            return batch_id;
        }

        public String getCar_number() {
            return car_number;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public boolean is_able_upload() {
            return is_able_upload;
        }
    }

    public static class AdBean {
        private int id;
        private String name;
        private String created_at;
        private String updated_at;
        private String content;
        private String sign_start_time;
        private String sign_end_time;
        private int adp_id;
        private int limit_num;
        private int is_end;
        private List<AdImage> imgs;
        private List<String> area;
        private List<String> type_take;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getContent() {
            return content;
        }

        public String getSign_start_time() {
            return sign_start_time;
        }

        public String getSign_end_time() {
            return sign_end_time;
        }

        public int getAdp_id() {
            return adp_id;
        }

        public int getLimit_num() {
            return limit_num;
        }

        public int getIs_end() {
            return is_end;
        }

        public List<AdImage> getImgs() {
            return imgs;
        }

        public List<String> getArea() {
            return area;
        }

        public List<String> getType_take() {
            return type_take;
        }
    }

    public static class Adspace {
        private List<PicImage> face_imgs;
        private UserTaskBean user;

        private int id;// ":628,
        private int uid; //":934,
        private String realname; //":"刘园",
        private String mobile; //":"13146791496",
        private String car_number; //":"桂C19889",
        private String created_at; //":"2018-09-21 16:21:13",
        private String updated_at; //":"2018-10-10 09:30:08",
        private String province; //":"广西壮族自治区",
        private String city; //":"桂林市",
        private String district; //":"秀峰区",
        private int ctype; //":5,
        private String cend_time; //:"2018-12-12 00:00:00",
        private int check_result; //":1,

        public List<PicImage> getFace_imgs() {
            return face_imgs;
        }

        public UserTaskBean getUser() {
            return user;
        }

        public int getId() {
            return id;
        }

        public int getUid() {
            return uid;
        }

        public String getRealname() {
            return realname;
        }

        public String getMobile() {
            return mobile;
        }

        public String getCar_number() {
            return car_number;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getDistrict() {
            return district;
        }

        public int getCtype() {
            return ctype;
        }

        public String getCend_time() {
            return cend_time;
        }

        public int getCheck_result() {
            return check_result;
        }
    }

    public static class AdImage {
        private String path;
        private String url;
        private String group;

        public String getPath() {
            return path;
        }

        public String getUrl() {
            return url;
        }

        public String getGroup() {
            return group;
        }
    }

    public static class PicImage {
        private String path;

        public String getPath() {
            return path;
        }
    }

    public class UserTaskBean implements Serializable{
        private int id;
        private String mobile;
        private String realname;
        private String nickname;
        private String country;
        private String province;
        private String city;
        private String created_at;
        private String updated_at;
        private int gender;
        private String avatar_url;
        private int point;
        private int ref_uid;
        private String qrcode;
        private int is_mobile_valid;
        private int able_withdraw;
        private String ad_manager;
        private String keyman;
        private int reg_type;

        public int getId() {
            return id;
        }

        public String getMobile() {
            return mobile;
        }

        public String getRealname() {
            return realname;
        }

        public String getNickname() {
            return nickname;
        }

        public String getCountry() {
            return country;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public int getGender() {
            return gender;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public int getPoint() {
            return point;
        }

        public int getRef_uid() {
            return ref_uid;
        }

        public String getQrcode() {
            return qrcode;
        }

        public int getIs_mobile_valid() {
            return is_mobile_valid;
        }

        public int getAble_withdraw() {
            return able_withdraw;
        }

        public String getAd_manager() {
            return ad_manager;
        }

        public String getKeyman() {
            return keyman;
        }

        public int getReg_type() {
            return reg_type;
        }
    }

}
