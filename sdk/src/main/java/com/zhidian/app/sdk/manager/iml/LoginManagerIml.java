package com.zhidian.app.sdk.manager.iml;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.widget.Toast;
import com.zhidian.app.sdk.event.login.LoginEvent;
import com.zhidian.app.sdk.http.HttpClient;
import com.zhidian.app.sdk.manager.LoginManager;
import de.greenrobot.event.EventBus;

import static de.greenrobot.event.EventBus.*;

/**
 * Created by mocf on 2017/7/14.
 */
public class LoginManagerIml implements LoginManager {
    private Context mContext;
    private String className;
    private HttpClient httpClient = HttpClient.getInstance();
    private static LoginManager mInstance;
    @Override
    public void init(Context mContext) {
        this.mContext = mContext;
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
    public void login(String userName, String passward) {
//        Toast.makeText(mContext,userName + "-->" + passward,Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.LOGIN_SUCCESS,userName,"返回成功",className));
    }
}
