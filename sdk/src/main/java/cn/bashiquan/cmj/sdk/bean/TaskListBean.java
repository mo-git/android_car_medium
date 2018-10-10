package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mocf on 2018/9/26.
 */
public class TaskListBean implements Serializable{
    private boolean state;
    private String msg;
    private int code;
    private List<TaskBean> data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public List<TaskBean> getData() {
        return data;
    }

    public static class TaskBean implements Serializable{
        private int id;
        private int ad_id;
        private String start_time;
        private String end_time;
        private int uid;
        private int adspace_id;
        private int task_id;
        private String created_at;
        private String updated_at;
        private List<PicPath> imgs;
        private String address;
        private String cstatus;
        private String remark;
        private String admin_uid;
        private int ad_time_id;
        private String lng;
        private String monitor_time;
        private int batch_id;
        private boolean on;

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

        public List<PicPath> getImgs() {
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

        public String getAdmin_uid() {
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

        public boolean isOn() {
            return on;
        }
    }

    public static class PicPath{
        private String path;

        public String getPath() {
            return path;
        }
    }

}
