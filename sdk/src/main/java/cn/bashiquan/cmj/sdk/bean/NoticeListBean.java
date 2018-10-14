package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mo on 2018/10/14.
 */

public class NoticeListBean implements Serializable {
    private boolean state;
    private String msg;
    private int code;
    private List<NoticeBean> data;

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

    public List<NoticeBean> getData() {
        return data;
    }

    public void setData(List<NoticeBean> data) {
        this.data = data;
    }

    public static class NoticeBean implements Serializable {
        private List<PicsBean> pics;
        private OtherInfoBean other_info;
        private SeoBean seo;
        private List<String> area;
        private int id;
        private String name;
        private String content;
        private String created_at;
        private String updated_at;
        private int sort_num;
        private int cate_id;
        private int stick;
        private int putaway;
        private String ref_id;

        public List<PicsBean> getPics() {
            return pics;
        }

        public void setPics(List<PicsBean> pics) {
            this.pics = pics;
        }

        public OtherInfoBean getOther_info() {
            return other_info;
        }

        public void setOther_info(OtherInfoBean other_info) {
            this.other_info = other_info;
        }

        public SeoBean getSeo() {
            return seo;
        }

        public void setSeo(SeoBean seo) {
            this.seo = seo;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getSort_num() {
            return sort_num;
        }

        public void setSort_num(int sort_num) {
            this.sort_num = sort_num;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public int getStick() {
            return stick;
        }

        public void setStick(int stick) {
            this.stick = stick;
        }

        public int getPutaway() {
            return putaway;
        }

        public void setPutaway(int putaway) {
            this.putaway = putaway;
        }

        public String getRef_id() {
            return ref_id;
        }

        public void setRef_id(String ref_id) {
            this.ref_id = ref_id;
        }

    }

    public static class PicsBean implements Serializable {
        private String name;
        private String path; //":"images/d0f56899f1eed3d499aa0bddf6af3904.jpeg",
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

    public static class OtherInfoBean implements Serializable {
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

    public static class SeoBean implements Serializable {
        private String title;
        private String keywords;
        private String description;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


}
