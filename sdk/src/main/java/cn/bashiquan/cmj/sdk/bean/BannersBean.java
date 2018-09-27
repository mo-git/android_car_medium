package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mo on 2018/9/27.
 * banner图
 */

public class BannersBean implements Serializable {

    private boolean state;
    private String msg;
    private int code;
    private List<Data> data;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private int id;
        private List<Pics> pics;
        private Other_info other_info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Pics> getPics() {
            return pics;
        }

        public void setPics(List<Pics> pics) {
            this.pics = pics;
        }

        public Other_info getOther_info() {
            return other_info;
        }

        public void setOther_info(Other_info other_info) {
            this.other_info = other_info;
        }
    }

    public static class Pics {

        private String name;
        private String path;
        private String group;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }
    }

    public static class Other_info {

        private String url;
        private String writer;
        private String hometop;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }

        public String getHometop() {
            return hometop;
        }

        public void setHometop(String hometop) {
            this.hometop = hometop;
        }
    }
}
