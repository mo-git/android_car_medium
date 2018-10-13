package cn.bashiquan.cmj.sdk.manager.iml;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import cn.bashiquan.cmj.sdk.bean.LoginBean;
import cn.bashiquan.cmj.sdk.bean.UserBean;
import cn.bashiquan.cmj.sdk.event.login.LoginEvent;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.http.RequestCallback;
import cn.bashiquan.cmj.sdk.http.RequestUrl;
import cn.bashiquan.cmj.sdk.manager.LoginManager;
import de.greenrobot.event.EventBus;

/**
 * Created by mocf on 2018/9/26.
 */
public class LoginManagerIml implements LoginManager {
    private Context mContext;
    private String className;
    private Gson mGson;
    private HttpClient httpClient = HttpClient.getInstance();
    private static LoginManager mInstance;
    private UserBean currentUser;
    @Override
    public void init(Context mContext) {
        this.mContext = mContext;
        mGson = new Gson();
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    public static LoginManager getInstance(){
        if(mInstance == null){
            mInstance = new LoginManagerIml();
        }
        return mInstance;
    }

    @Override
    public void login(String userName, String passward,Class loginClass) {
//        Toast.makeText(mContext,userName + "-->" + passward,Toast.LENGTH_SHORT).show();
        LoginBean.LoginRequest loginRequest = new LoginBean.LoginRequest();
        loginRequest.setUserName("1111");
        loginRequest.setPassWord("2222");

//        EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.LOGIN_SUCCESS,loginResult,"返回成功",className));
    }

    @Override
    public void getUserInfo() {
        HttpClient.getInstance().sendGetRequest(RequestUrl.USERINFO_URL, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                UserBean userBean = mGson.fromJson(data,UserBean.class);
                EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.GET_USERINFO_SUCCESS,userBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.GET_USERINFO_FAILED,cause.getMessage()));
            }
        });
    }


}
