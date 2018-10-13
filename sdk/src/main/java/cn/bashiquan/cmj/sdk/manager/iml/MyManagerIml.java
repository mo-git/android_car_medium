package cn.bashiquan.cmj.sdk.manager.iml;

import android.content.Context;

import com.google.gson.Gson;

import cn.bashiquan.cmj.sdk.bean.LoginBean;
import cn.bashiquan.cmj.sdk.event.login.LoginEvent;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.manager.MyManager;
import de.greenrobot.event.EventBus;

/**
 * Created by mo on 2018/9/27.
 */

public class MyManagerIml implements MyManager {
    private Context mContext;
    private String className;
    private Gson mGson;
    private HttpClient httpClient = HttpClient.getInstance();
    private static MyManager mInstance;
    @Override
    public void init(Context mContext) {
        this.mContext = mContext;
        mGson = new Gson();
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    public static MyManager getInstance(){
        if(mInstance == null){
            mInstance = new MyManagerIml();
        }
        return mInstance;
    }


    @Override
    public void text(String userName, String passward, Class loginClass) {
        // 测试假数据
        LoginBean jsonClass = new LoginBean();
        jsonClass.userName = "1111";
        jsonClass.passWord = "2222";
        LoginBean loginResult = (LoginBean)mGson.fromJson(mGson.toJson(jsonClass),loginClass);

//        EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.LOGIN_SUCCESS,loginResult,"返回成功",className));
    }
}
