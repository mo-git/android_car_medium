package cn.bashiquan.cmj.sdk.manager.iml;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.bashiquan.cmj.sdk.bean.AdListBean;
import cn.bashiquan.cmj.sdk.bean.AddMediaPicBeanFailed;
import cn.bashiquan.cmj.sdk.bean.AddMediaPicBeanSuccess;
import cn.bashiquan.cmj.sdk.bean.CarTypeListBean;
import cn.bashiquan.cmj.sdk.bean.MediaPicBean;
import cn.bashiquan.cmj.sdk.bean.AddPicBean;
import cn.bashiquan.cmj.sdk.bean.BannersBean;
import cn.bashiquan.cmj.sdk.bean.MediaListBean;
import cn.bashiquan.cmj.sdk.bean.ProductBean;
import cn.bashiquan.cmj.sdk.bean.ProductListBean;
import cn.bashiquan.cmj.sdk.bean.ProvinceBean;
import cn.bashiquan.cmj.sdk.bean.TaskInfoReposeBean;
import cn.bashiquan.cmj.sdk.bean.TaskListBean;
import cn.bashiquan.cmj.sdk.bean.WXTokenBean;
import cn.bashiquan.cmj.sdk.bean.WXUserBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AdListEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddMeidaEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddPicEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.BannerEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.MediaListEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.ShopEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.TaskEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.TaskListEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.WXEvent;
import cn.bashiquan.cmj.sdk.http.BaseRequest;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.http.RequestCallback;
import cn.bashiquan.cmj.sdk.http.RequestUrl;
import cn.bashiquan.cmj.sdk.manager.HomeManager;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.sdk.utils.SPUtils;
import de.greenrobot.event.EventBus;

/**
 * Created by mo on 2018/9/27.
 */

public class HomeManagerIml implements HomeManager {
    private Context mContext;
    private String className;
    private Gson mGson;
    private static HomeManager mInstance;
    @Override
    public void init(Context mContext) {
        this.mContext = mContext;
        mGson = new Gson();
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    public static HomeManager getInstance(){
        if(mInstance == null){
            mInstance = new HomeManagerIml();
        }
        return mInstance;
    }

    @Override
    public void getBannerImages(final Class bannersBean) {
        HttpClient.getInstance().sendGetRequest(RequestUrl.BANNER_URL, new RequestCallback() {
            @Override
            public void onResponse(String data){
                BannersBean responseData = (BannersBean)mGson.fromJson(data,bannersBean);
                if(responseData != null){
                    EventBus.getDefault().post(new BannerEvent(BannerEvent.EventType.GET_BANNER_SUCCESS,"",responseData));
                }else{
                    EventBus.getDefault().post(new BannerEvent(BannerEvent.EventType.GET_BANNER_FAILED,"没有数据",null));
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new BannerEvent(BannerEvent.EventType.GET_BANNER_FAILED,cause.getMessage(),null));
            }
        });
    }

    @Override
    public void getMeidList(int limit, int offSet) {
        String url = RequestUrl.getMediaListUrl(limit,offSet);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                MediaListBean mediaListBean = mGson.fromJson(data,MediaListBean.class);
                EventBus.getDefault().post(new MediaListEvent(MediaListEvent.EventType.GET_MEDIA_SUCCESS,"",mediaListBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new MediaListEvent(MediaListEvent.EventType.GET_MEDIA_FAILED,cause.getMessage(),null));

            }
        });
    }

    @Override
    public void cancleTask(int id,final String carNum) {
        String url = RequestUrl.getCancleTaskUrl(id);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String msg = jsonObject.getString("msg");
                    if(msg.contains("删除成功")){
                        EventBus.getDefault().post(new MediaListEvent(MediaListEvent.EventType.CANCEL_TASK_SUCCESS,carNum,null));
                    }else{
                        EventBus.getDefault().post(new MediaListEvent(MediaListEvent.EventType.CANCLE_TASK_FAILED,carNum,null));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new MediaListEvent(MediaListEvent.EventType.CANCLE_TASK_FAILED,carNum,null));
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new MediaListEvent(MediaListEvent.EventType.CANCLE_TASK_FAILED,cause.getMessage(),null));

            }
        });
    }

    @Override
    public void getAdList(final int carId) {
        String url = RequestUrl.getAdListUrl(carId);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                AdListBean adListBean = mGson.fromJson(data,AdListBean.class);
                EventBus.getDefault().post(new AdListEvent(AdListEvent.EventType.GET_AD_SUCCESS,"",adListBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new AdListEvent(AdListEvent.EventType.GET_AD_FAILED,cause.getMessage(),null));
            }
        });
    }

    @Override
    public void addTask(int id, int ad_id) {
        String url = RequestUrl.getAddTaskUrl(id,ad_id);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                    String taskId = "";
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        String msg = jsonObject.getString("msg");
                        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
                        taskId = dataJsonObject.getString("id");
                        if(msg.contains("提交成功")){
                            EventBus.getDefault().post(new AdListEvent(AdListEvent.EventType.ADD_TASK_SUCCESS,taskId));
                        }else{
                            EventBus.getDefault().post(new AdListEvent(AdListEvent.EventType.ADD_TASK_FAILED,"提交失败!",null));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        EventBus.getDefault().post(new AdListEvent(AdListEvent.EventType.ADD_TASK_FAILED,"提交失败!",null));
                    }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new AdListEvent(AdListEvent.EventType.ADD_TASK_FAILED,cause.getMessage(),null));
            }
        });
    }

    @Override
    public void getTaskList(String taskId) {
        String url = RequestUrl.getTaskListUrl(taskId);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                TaskListBean taskListBean = mGson.fromJson(data,TaskListBean.class);
                EventBus.getDefault().post(new TaskListEvent(TaskListEvent.EventType.GET_TASK_SUCCESS,"",taskListBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new TaskListEvent(TaskListEvent.EventType.GET_TASK_FAILED,"",null));
            }
        });
    }

    @Override
    public void getTaskInfo(int id) {
        String url = RequestUrl.getTaskInfotUrl(id);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                TaskInfoReposeBean taskInfoReposeBean = mGson.fromJson(data,TaskInfoReposeBean.class);
                EventBus.getDefault().post(new TaskEvent(TaskEvent.EventType.GET_TASKINFO_SUCCESS,"",taskInfoReposeBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new TaskEvent(TaskEvent.EventType.GET_TASKINFO_FAILED,cause.getMessage(),null));
            }
        });
    }

    @Override
    public void submitTask(BaseRequest request) {
        HttpClient.getInstance().sendPostRequest(RequestUrl.ADD_TASK_SUBMIT_URL,request, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String msg = jsonObject.getString("msg");
                    if(msg.contains("提交成功")){
                        EventBus.getDefault().post(new TaskEvent(TaskEvent.EventType.GET_SUBMIT_SUCCESS,"",null));
                    }else{
                        EventBus.getDefault().post(new TaskEvent(TaskEvent.EventType.GET_SUBMIT_FAILED,msg,null));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new TaskEvent(TaskEvent.EventType.GET_SUBMIT_FAILED,e.getMessage(),null));
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new TaskEvent(TaskEvent.EventType.GET_SUBMIT_FAILED,cause.getMessage(),null));
            }
        });
    }

    @Override
    public void uplodeMediaImage(Class resposeBean, String imagePath, final String imageName) {
        HttpClient.getInstance().uplodeImage(RequestUrl.ADD_MEDIA_PIC_URL, imagePath, imageName, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    MediaPicBean addMediaPicBean;
                    boolean state = jsonObject.getBoolean("state");
                    String msg = "";
                    if(state){
                        AddMediaPicBeanSuccess mediaPicBeanSuccess = mGson.fromJson(data,AddMediaPicBeanSuccess.class);
                        addMediaPicBean = mediaPicBeanSuccess.getData();
                        addMediaPicBean.setState(mediaPicBeanSuccess.isState());
                        addMediaPicBean.setMsg(msg);
                    }else{
                        AddMediaPicBeanFailed addMediaPicBeanFailed = mGson.fromJson(data,AddMediaPicBeanFailed.class);
                        addMediaPicBean = addMediaPicBeanFailed.getMsg();
                        addMediaPicBean.setState(addMediaPicBeanFailed.isState());
                        msg = "无法识别您的车牌号,请在下方填写正确的车牌号";
                        addMediaPicBean.setMsg(msg);
                    }
                    EventBus.getDefault().post(new AddPicEvent(AddPicEvent.EventType.GET_ADD_PIC_SUCCESS,imageName,addMediaPicBean));
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new AddPicEvent(AddPicEvent.EventType.GET_ADD_PIC_FAILED,"数据解析失败"));
                }

            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new AddPicEvent(AddPicEvent.EventType.GET_ADD_PIC_FAILED,cause.getMessage()));
            }
        });
    }

    @Override
    public void checkCarNumReal(String carNum) {
        String url = RequestUrl.getCardNumRealUrl(carNum);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if(code == 200){
                        EventBus.getDefault().post(new AddMeidaEvent(AddMeidaEvent.EventType.CHECK_CAR_NUM_SUCCESS,"",null));
                    }else{
                        EventBus.getDefault().post(new AddMeidaEvent(AddMeidaEvent.EventType.CHECK_CAR_NUM_FAILED,msg,null));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new AddMeidaEvent(AddMeidaEvent.EventType.CHECK_CAR_NUM_FAILED,"数据解析失败",null));
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new AddMeidaEvent(AddMeidaEvent.EventType.CHECK_CAR_NUM_FAILED,cause.getMessage(),null));
            }
        });
    }

    @Override
    public void getCarType() {
        HttpClient.getInstance().sendGetRequest(RequestUrl.GET_CAR_TYPE_URL, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                CarTypeListBean carTypeListBean = mGson.fromJson(data,CarTypeListBean.class);
                EventBus.getDefault().post(new AddMeidaEvent(AddMeidaEvent.EventType.GET_CAR_TYPE_SUCCESS,carTypeListBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new AddMeidaEvent(AddMeidaEvent.EventType.GETE_CAR_TYPE_FAILED,cause.getMessage(),null));
            }
        });
    }

    @Override
    public void addMedia(BaseRequest request) {
        HttpClient.getInstance().sendPostRequest(RequestUrl.ADD_MEDIA,request, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String msg = jsonObject.getString("msg");
                    if(msg.contains("提交成功")){
                        EventBus.getDefault().post(new AddMeidaEvent(AddMeidaEvent.EventType.ADD_MEDIA_SUCCESS,null));
                    }else{
                        EventBus.getDefault().post(new AddMeidaEvent(AddMeidaEvent.EventType.ADD_MEDIA_FAILED,null));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new AddMeidaEvent(AddMeidaEvent.EventType.ADD_MEDIA_FAILED,null));
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new AddMeidaEvent(AddMeidaEvent.EventType.ADD_MEDIA_FAILED,null));
            }
        });
    }

    @Override
    public void uplodeTaskImage(Class resposeBean, String imagePath, final String imageName) {
        HttpClient.getInstance().uplodeImage(RequestUrl.ADD_TASK_PIC_URL, imagePath, imageName, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                AddPicBean addPicBean = mGson.fromJson(data,AddPicBean.class);
                EventBus.getDefault().post(new AddPicEvent(AddPicEvent.EventType.GET_ADD_PIC_SUCCESS,imageName,addPicBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new AddPicEvent(AddPicEvent.EventType.GET_ADD_PIC_FAILED,cause.getMessage()));
            }
        });
    }

    @Override
    public void getProductList(int limit, int offset, String keyword) {
        String uri = RequestUrl.getProductUrl(limit,offset,keyword);
        HttpClient.getInstance().sendGetRequest(uri, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                ProductListBean productListBean = mGson.fromJson(data,ProductListBean.class);
                EventBus.getDefault().post(new ShopEvent(ShopEvent.EventType.GET_PROTECT_SUCCESS,"",productListBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new ShopEvent(ShopEvent.EventType.GET_PROTECT_FAILED,cause.getMessage(),null));
            }
        });
    }

    @Override
    public void getProductInfo(int id) {
        String uri = RequestUrl.getProductInfoUrl(id);
        HttpClient.getInstance().sendGetRequest(uri, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                ProductBean productBean = mGson.fromJson(data,ProductBean.class);
                EventBus.getDefault().post(new ShopEvent(ShopEvent.EventType.GET_PROTECTINFO_SUCCESS,productBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new ShopEvent(ShopEvent.EventType.GET_PROTECTINFO_FAILED,cause.getMessage(),null));
            }
        });
    }

    @Override
    public void payProduct(int id, String key, String num) {
        String uri = RequestUrl.getPayProductInfoUrl(id,key,num);
        HttpClient.getInstance().sendGetRequest(uri, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if(code == 200){
                        EventBus.getDefault().post(new ShopEvent(ShopEvent.EventType.PAY_PRODUCT_SUCCESS,msg));
                    }else{
                        EventBus.getDefault().post(new ShopEvent(ShopEvent.EventType.PAY_PRODUCT_SUCCESS,msg));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new ShopEvent(ShopEvent.EventType.PAY_PRODUCT_FAILED,cause.getMessage()));
            }
        });
    }


    @Override
    public void getAccess_token(final Class wXTokenBean, String code) {
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
                    WXTokenBean tokenBean = (WXTokenBean) mGson.fromJson(data, wXTokenBean);
                    if(tokenBean != null){
                        SPUtils.put(mContext,Constants.SP_WXTOKEN,tokenBean.getAccess_token());
                    }
                    EventBus.getDefault().post(new WXEvent(WXEvent.EventType.GET_WX_TOKEN_SUCCESS,tokenBean));
                    getUserMesg(WXUserBean.class, tokenBean.getAccess_token(), tokenBean.getOpenid());
                }


            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new WXEvent(WXEvent.EventType.GET_WX_TOKEN_FAILED,"获取toker失败,请稍后重试"));
            }
        });
    }

    @Override
    public void getUserMesg(final Class wXUserBean, String access_token, String openid) {
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
                WXUserBean userBean = (WXUserBean) mGson.fromJson(data,wXUserBean);
                EventBus.getDefault().post(new WXEvent(WXEvent.EventType.GET_WX_USER_SUCCESS,userBean));

            }
            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new WXEvent(WXEvent.EventType.GET_WX_USER_FAILED,"获取个人信息失败,请稍后重试"));
            }
        });

    }

}
