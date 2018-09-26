package cn.bashiquan.cmj.sdk.manager.iml;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;

import cn.bashiquan.cmj.sdk.bean.Login;
import cn.bashiquan.cmj.sdk.bean.User;
import cn.bashiquan.cmj.sdk.event.login.LoginEvent;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.manager.LoginManager;
import de.greenrobot.event.EventBus;

import static de.greenrobot.event.EventBus.*;

/**
 * Created by mocf on 2018/9/26.
 */
public class LoginManagerIml implements LoginManager {
    private Context mContext;
    private String className;
    private Gson mGson;
    private HttpClient httpClient = HttpClient.getInstance();
    private static LoginManager mInstance;
    private User currentUser;
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
        Login jsonClass = new Login();
        jsonClass.userName = "1111";
        jsonClass.passWord = "2222";
        Login loginResult = (Login)mGson.fromJson(mGson.toJson(jsonClass),loginClass);

        EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.LOGIN_SUCCESS,loginResult,"返回成功",className));
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
