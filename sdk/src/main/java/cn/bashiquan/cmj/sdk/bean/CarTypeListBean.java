package cn.bashiquan.cmj.sdk.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mo on 2018/10/10.
 */

public class CarTypeListBean implements Serializable{
    private boolean state; //":true,
    private String msg; //":"",
    private int code; //":200,
    private List<CarType> data;

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public List<CarType> getData() {
        return data;
    }

    public static class CarType{
         private int id; //":0,
        private String name; //":"请选择"

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
