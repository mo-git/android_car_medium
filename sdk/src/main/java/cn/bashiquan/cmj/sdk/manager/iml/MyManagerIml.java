package cn.bashiquan.cmj.sdk.manager.iml;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.bashiquan.cmj.sdk.bean.IntegralListBean;
import cn.bashiquan.cmj.sdk.bean.JoinUserBean;
import cn.bashiquan.cmj.sdk.bean.LoginBean;
import cn.bashiquan.cmj.sdk.bean.MyOrderInfotBean;
import cn.bashiquan.cmj.sdk.bean.MyOrderListBean;
import cn.bashiquan.cmj.sdk.event.MyManager.DrawEvent;
import cn.bashiquan.cmj.sdk.event.MyManager.IntegralEvent;
import cn.bashiquan.cmj.sdk.event.MyManager.MyOrderEvent;
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

    @Override
    public void getOrderList(int limit, int offset, String keyword) {
        String url = RequestUrl.getOrderUrl(limit,offset,keyword);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                MyOrderListBean myOrderListBean = mGson.fromJson(data,MyOrderListBean.class);
                EventBus.getDefault().post(new MyOrderEvent(MyOrderEvent.EventType.GET_ORDER_LIST_SUCCESS,myOrderListBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new MyOrderEvent(MyOrderEvent.EventType.GET_ORDER_LIST_FAILED,cause.getMessage()));
            }
        });
    }

    @Override
    public void getorderToken(String id) {
        String url = RequestUrl.getorderTokenUrl(id);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                MyOrderInfotBean myOrderInfotBean = mGson.fromJson(data,MyOrderInfotBean.class);
                EventBus.getDefault().post(new MyOrderEvent(MyOrderEvent.EventType.GET_ORDERTOKEN_SUCCESS,myOrderInfotBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new MyOrderEvent(MyOrderEvent.EventType.GET_ORDERTOKEN_FAILED,cause.getMessage()));
            }
        });
    }

    @Override
    public void rufundOrder(String id) {
        String url = RequestUrl.getRufundUrl(id);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if(code == 200){
                        EventBus.getDefault().post(new MyOrderEvent(MyOrderEvent.EventType.REFUND_ORDER_SUCCESS,msg));
                    }else{
                        EventBus.getDefault().post(new MyOrderEvent(MyOrderEvent.EventType.REFUND_ORDER_FAILED,msg));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new MyOrderEvent(MyOrderEvent.EventType.REFUND_ORDER_FAILED,"解析数据类型不符"));
                }


            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new MyOrderEvent(MyOrderEvent.EventType.REFUND_ORDER_FAILED,cause.getMessage()));
            }
        });
    }

    @Override
    public void getLuckList() {
        HttpClient.getInstance().sendGetRequest("", new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
//                IntegralListBean integralListBean = new IntegralListBean();
                EventBus.getDefault().post(new DrawEvent(DrawEvent.EventType.GET_DRAWLIST_SUCCESS,""));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new DrawEvent(DrawEvent.EventType.GET_DRAWLIST_FAILED,cause.getMessage()));
            }
        });
    }

    @Override
    public void luckjoin() {
        HttpClient.getInstance().sendGetRequest(RequestUrl.LUCKJOINURL, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                EventBus.getDefault().post(new DrawEvent(DrawEvent.EventType.JOIN_DRAW_SUCCESS,"参与成功"));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new DrawEvent(DrawEvent.EventType.JOIN_DRAW_FAILED,cause.getMessage()));
            }
        });
    }

    @Override
    public void getLuckjoinUser() {
        HttpClient.getInstance().sendGetRequest(RequestUrl.LUCKJOINUSERURL, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                JoinUserBean joinUserBean = mGson.fromJson(data,JoinUserBean.class);
                EventBus.getDefault().post(new DrawEvent(DrawEvent.EventType.GET_JOIN_USER_SUCCESS,joinUserBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new DrawEvent(DrawEvent.EventType.GET_JOIN_USER_FAILED,cause.getMessage()));
            }
        });
    }

    @Override
    public void getIntegralList(int limit, int offset) {
        String url = RequestUrl.getIntegralListUrl(limit,offset);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                IntegralListBean integralListBean = mGson.fromJson(data,IntegralListBean.class);
                EventBus.getDefault().post(new IntegralEvent(IntegralEvent.EventType.GET_INTEGRAL_LIST_SUCCESS,integralListBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new IntegralEvent(IntegralEvent.EventType.GET_INTEGRAL_LIST_SUCCESS,cause.getMessage()));
            }
        });
    }

    @Override
    public void getWithdrawlimit() {
        HttpClient.getInstance().sendGetRequest(RequestUrl.WITHDRAWLIMIT_URL, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject dataJson  = jsonObject.getJSONObject("data");
                    int limit = dataJson.getInt("limit");
                    EventBus.getDefault().post(new IntegralEvent(IntegralEvent.EventType.GET_WITHDRAWLIMIT_SUCCESS,limit));
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new IntegralEvent(IntegralEvent.EventType.GET_WITHDRAWLIMIT_FAILED,"解析数据类型不符"));
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new IntegralEvent(IntegralEvent.EventType.GET_WITHDRAWLIMIT_FAILED,cause.getMessage()));
            }
        });
    }

    @Override
    public void withdrawMoney(String money) {
        String url = RequestUrl.getWithdrawUrl(money);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        EventBus.getDefault().post(new IntegralEvent(IntegralEvent.EventType.GET_MONEY_SUCCESS,"提现成功"));
                    }else{
                        String msg = jsonObject.getString("msg");
                        EventBus.getDefault().post(new IntegralEvent(IntegralEvent.EventType.GET_MONEY_SUCCESS,msg));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new IntegralEvent(IntegralEvent.EventType.GET_MONEY_SUCCESS,cause.getMessage()));
            }
        });

    }

    @Override
    public void getWithdrawList(int limit, int offset) {
        String url = RequestUrl.getWithdrawListUrl(limit,offset);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
//                IntegralListBean integralListBean = new IntegralListBean();
                EventBus.getDefault().post(new IntegralEvent(IntegralEvent.EventType.GET_WITHDRAWLIST_SUCCESS,""));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new IntegralEvent(IntegralEvent.EventType.GET_WITHDRAWLIST_FAILED,""));
            }
        });

    }
}
