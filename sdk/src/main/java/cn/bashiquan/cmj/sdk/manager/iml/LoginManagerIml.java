package cn.bashiquan.cmj.sdk.manager.iml;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.bashiquan.cmj.sdk.bean.LoginBean;
import cn.bashiquan.cmj.sdk.bean.UserBean;
import cn.bashiquan.cmj.sdk.bean.WXTokenBean;
import cn.bashiquan.cmj.sdk.bean.WXUserBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.WXEvent;
import cn.bashiquan.cmj.sdk.event.login.LoginEvent;
import cn.bashiquan.cmj.sdk.event.login.UnauthenticatedEvent;
import cn.bashiquan.cmj.sdk.http.BaseRequest;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.http.RequestCallback;
import cn.bashiquan.cmj.sdk.http.RequestUrl;
import cn.bashiquan.cmj.sdk.manager.LoginManager;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.sdk.utils.SPUtils;
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
    public void getAccess_token(String code) {
        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + Constants.WEIXIN_APP_ID
                + "&secret="
                + Constants.APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";
        //1,创建OKHttpClient对象
        HttpClient.getInstance().sendGetRequestWX(path, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                if (data.contains("errcode")) {
                    EventBus.getDefault().post(new WXEvent(WXEvent.EventType.GET_WX_TOKEN_FAILED, "获取toker失败,请稍后重试"));
                } else {

//                    {
//                        "access_token":"14_yuPZ8a-1OK58_1YZGFjZIvuwiEw_GsRXw9MMTmEUN905mu-PUzXYkcc59p13EsLg6gAJ7S9oqG90P-f6F7NPBXzOz7YvGLCKVKPJTNJJCHc",
//                            "expires_in":7200,
//                            "refresh_token":"14_NneuuzyQydw4q_1Rp80qaHJ7UZTqo94Xo4o9i2mvCdmsio65KR5Tvpoq7fGvbK-GL2913phYAypGKVJAO4pe_-p_M73eks1_boKi2NBLfuc",
//                            "openid":"oouW51aUvNcWo_o3zmN4knkg-2Xw",
//                            "scope":"snsapi_userinfo",
//                            "unionid":"oZi3s5wRkIMjNC9RkZO6NDbWR1TQ"
//                    }
                    WXTokenBean tokenBean = mGson.fromJson(data, WXTokenBean.class);
                    if(tokenBean != null){
                        EventBus.getDefault().post(new WXEvent(WXEvent.EventType.GET_WX_TOKEN_SUCCESS,tokenBean));
                    }else{
                        EventBus.getDefault().post(new WXEvent(WXEvent.EventType.GET_WX_TOKEN_FAILED,"获取toker失败,请稍后重试"));
                    }
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new WXEvent(WXEvent.EventType.GET_WX_TOKEN_FAILED,"获取toker失败,请稍后重试"));
            }
        });
    }

    @Override
    public void getWxUserMesg(String access_token, String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
        HttpClient.getInstance().sendGetRequestWX(path, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
//                "openid":"olmt4wfxS24VeeVX16_zUhZezY",
//                        "nickname":"李文星",
//                        "sex":1,
//                        "language":"zh_CN",
//                        "city":"Shenzhen",
//                        "province":"Guangdong",
//                        "country":"CN",
//                        "headimgurl":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLDickRibe5D4x2ADgSfianmA4kK9hY4esrvGhmAFCe5wjox6b6pL4ibiblKnxibzVtGdqfa2UVHACfmmUsQ/0",
//                        "privilege":[
//
//    ],
//                "unionid":"o5aWQwAa7niCIXhAIRBOwglIJ7UQ"
                WXUserBean userBean = mGson.fromJson(data,WXUserBean.class);
                EventBus.getDefault().post(new WXEvent(WXEvent.EventType.GET_WX_USER_SUCCESS,userBean));

            }
            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new WXEvent(WXEvent.EventType.GET_WX_USER_FAILED,"获取个人信息失败,请稍后重试"));
            }
        });

    }

    @Override
    public void login(String unionid) {
        String uri = RequestUrl.getLoginUrl(unionid);
        HttpClient.getInstance().sendGetRequest(uri, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                LoginBean loginBean = mGson.fromJson(data,LoginBean.class);
                if(loginBean.getCode() == 401){
                    EventBus.getDefault().post(new UnauthenticatedEvent(UnauthenticatedEvent.EventType.LOIGN_SUCCESS));
                }else{
                    EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.LOGIN_SUCCESS,loginBean));
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.LOGIN_FAILD,cause.getMessage()));
            }
        });


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

    @Override
    public void refreshTodek() {
        HttpClient.getInstance().sendGetRequest(RequestUrl.REFRESH_TOKEN_URL, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);
                    int  code = jsonObject.getInt("code");
                    if(code == 200){
                        EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.REFRSH_TOKEN_SUCCESS,""));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable cause) {
            }
        });
    }

    @Override
    public void regist(BaseRequest request) {
        HttpClient.getInstance().sendPostRequest(RequestUrl.REGIST_URL,request, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int  code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if(code == 200){
                        EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.REGIST_SUCCESS,msg));
                    }else{
                        EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.REGIST_FAILED,msg));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.REGIST_FAILED,e.getMessage()));
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new LoginEvent(LoginEvent.EventType.REGIST_FAILED,cause.getMessage()));
            }
        });
    }


}
