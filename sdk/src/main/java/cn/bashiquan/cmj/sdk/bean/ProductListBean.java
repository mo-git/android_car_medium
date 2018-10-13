package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mocf on 2018/9/26.
 */
public class ProductListBean implements Serializable {

    private boolean state;
    private String msg;
    private int code;
    private ProductsBean data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public ProductsBean getData() {
        return data;
    }

    public static class ProductsBean implements Serializable {
            private List<ProductBean> list;

        public List<ProductBean> getList() {
            return list;
        }
    }
    public static class ProductBean implements Serializable {
        private int id;
        private String name;
        private String content;
        private List<ProductImage> imgs;
        private String created_at; //":"2018-10-10 09:30:08",
        private String updated_at; //":"2018-10-10 09:30:08",
        private int p_id; //":934,
        private int putaway; //":628,
        private String sellNum; //":null,
        private String minPrice;
        private String cover;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public List<ProductImage> getImgs() {
            return imgs;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public int getP_id() {
            return p_id;
        }

        public int getPutaway() {
            return putaway;
        }

        public String getSellNum() {
            return sellNum;
        }

        public String getMinPrice() {
            return minPrice;
        }

        public String getCover() {
            return cover;
        }
    }


    public static class ProductImage {
        private String path;
        private String name;
        private String group;

        public String getPath() {
            return path;
        }

        public String getName() {
            return name;
        }

        public String getGroup() {
            return group;
        }
    }

}
