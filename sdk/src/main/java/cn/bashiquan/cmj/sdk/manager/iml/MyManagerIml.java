package cn.bashiquan.cmj.sdk.manager.iml;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.bashiquan.cmj.sdk.bean.LoginBean;
import cn.bashiquan.cmj.sdk.event.MyManager.TaxationEvent;
import cn.bashiquan.cmj.sdk.event.MyManager.VerifyEvent;
import cn.bashiquan.cmj.sdk.event.login.LoginEvent;
import cn.bashiquan.cmj.sdk.http.BaseRequest;
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
                EventBus.getDefault().post(new VerifyEvent(VerifyEvent.EventType.GET_VERIFY_SUCCESS,"","发送成功"));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new VerifyEvent(VerifyEvent.EventType.GET_VERIFY_FAILED,"",""));
            }
        });
    }

    @Override
    public void VerifyUser(String realname, String mobile, String code) {
        String url = RequestUrl.getVerifyUser_url(realname,mobile,code);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException, JSONException {
                JSONObject jsonObject = new JSONObject(data);
                int code = jsonObject.getInt("code");
                if(code == 200){
                    EventBus.getDefault().post(new VerifyEvent(VerifyEvent.EventType.VERIFY_USER_SUCCESS,"",""));
                }else{
                    EventBus.getDefault().post(new VerifyEvent(VerifyEvent.EventType.VERIFY_USER_FAILED,"","验证失败"));
                }

            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new VerifyEvent(VerifyEvent.EventType.VERIFY_USER_FAILED,"",""));
            }
        });
    }

    @Override
    public void submitTaxation(BaseRequest request) {
        HttpClient.getInstance().sendPostRequest(RequestUrl.TAXATION_URL,request, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String msg = jsonObject.getString("msg");
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        EventBus.getDefault().post(new TaxationEvent(TaxationEvent.EventType.SUMIT_SUCCESS,"提交成功"));
                    }else{
                        EventBus.getDefault().post(new TaxationEvent(TaxationEvent.EventType.SUBMIT_FAILED,msg));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new TaxationEvent(TaxationEvent.EventType.SUBMIT_FAILED,e.toString()));
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new TaxationEvent(TaxationEvent.EventType.SUBMIT_FAILED,cause.getMessage()));
            }
        });
    }
}
