package cn.bashiquan.cmj.sdk.manager.iml;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import cn.bashiquan.cmj.sdk.bean.LoginBean;
import cn.bashiquan.cmj.sdk.event.MyManager.VerifyEvent;
import cn.bashiquan.cmj.sdk.event.login.LoginEvent;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.http.RequestCallback;
import cn.bashiquan.cmj.sdk.http.RequestUrl;
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
    public void getVerifyCode(String mobile) {
        String url = RequestUrl.getVerify_mobile_url(mobile);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
//                MediaListBean mediaListBean = mGson.fromJson(data,MediaListBean.class);
                EventBus.getDefault().post(new VerifyEvent(VerifyEvent.EventType.GET_VERIFY_SUCCESS,"",""));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new VerifyEvent(VerifyEvent.EventType.GET_VERIFY_FAILED,"",""));
            }
        });
    }
}
