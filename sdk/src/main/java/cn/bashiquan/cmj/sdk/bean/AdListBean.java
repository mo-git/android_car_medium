package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mocf on 2018/9/26.
 */
public class AdListBean implements Serializable{
    private boolean state;
    private String msg;
    private int code;
    private List<AdBean> data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public List<AdBean> getData() {
        return data;
    }

    public static class AdBean implements Serializable{
        private int id;
        private String name;
        private String created_at;
        private String updated_at;
        private List<Imgs> imgs;
        private String content;
        private List<String> area;
        private String sign_start_time;
        private String sign_end_time;
        private int adp_id;
        private List<String> type_take;
        private int limit_num;
        private int is_end;
        private List<Tasks> tasks;

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

        public List<Imgs> getImgs() {
            return imgs;
        }

        public String getContent() {
            return content;
        }

        public List<String> getArea() {
            return area;
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

        public List<String> getType_take() {
            return type_take;
        }

        public int getLimit_num() {
            return limit_num;
        }

        public int getIs_end() {
            return is_end;
        }

        public List<Tasks> getTasks() {
            return tasks;
        }
    }

    public static class Tasks {

        private int id;
        private int uid;
        private int ad_id;
        private int adspace_id;
        private String created_at;
        private String updated_at;
        private String start_time;
        private String end_time;
        private String first_upload_time;

        public int getId() {
            return id;
        }

        public int getUid() {
            return uid;
        }

        public int getAd_id() {
            return ad_id;
        }

        public int getAdspace_id() {
            return adspace_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getStart_time() {
            return start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public String getFirst_upload_time() {
            return first_upload_time;
        }
    }

    public static class Imgs {

        private String name;
        private String path;
        private String group;

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }

        public String getGroup() {
            return group;
        }
    }
}
