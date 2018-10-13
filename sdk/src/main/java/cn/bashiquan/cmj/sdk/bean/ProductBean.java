package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mocf on 2018/9/26.
 */
public class ProductBean implements Serializable {

    private boolean state;
    private String msg;
    private int code;
    private ProductsInfoBean data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public ProductsInfoBean getData() {
        return data;
    }

    public static class ProductsInfoBean implements Serializable {

        private int id;
        private String name;
        private String content;
        private List<String> imgs;
        private String created_at;
        private String updated_at;
        private Product product;
        private int p_id;
        private int putaway;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public Product getProduct() {
            return product;
        }

        public int getP_id() {
            return p_id;
        }

        public int getPutaway() {
            return putaway;
        }
    }


    public static class Product {
        private  List<List<AbcBean>> abc;
        private List<inputDataBean> inputData;

        public List<List<AbcBean>> getAbc() {
            return abc;
        }

        public List<inputDataBean> getInputData() {
            return inputData;
        }
    }

    public static class AbcBean {
        private int id;
        private boolean edit;
        private String name;
        private String path;
        private List<String> upload;

        public int getId() {
            return id;
        }

        public boolean isEdit() {
            return edit;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }

        public List<String> getUpload() {
            return upload;
        }
    }

    public static class inputDataBean {

        private String key;
        private int idkey;
        private String price;
        private String stock;
        private int sellNum;
        private String limitBuy;

        public String getKey() {
            return key;
        }

        public int getIdkey() {
            return idkey;
        }

        public String getPrice() {
            return price;
        }

        public String getStock() {
            return stock;
        }

        public int getSellNum() {
            return sellNum;
        }

        public String getLimitBuy() {
            return limitBuy;
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
