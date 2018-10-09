package cn.bashiquan.cmj.sdk.manager.iml;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.bashiquan.cmj.sdk.bean.AdListBean;
import cn.bashiquan.cmj.sdk.bean.BannersBean;
import cn.bashiquan.cmj.sdk.bean.MediaListBean;
import cn.bashiquan.cmj.sdk.bean.TaskListBean;
import cn.bashiquan.cmj.sdk.bean.WXTokenBean;
import cn.bashiquan.cmj.sdk.bean.WXUserBean;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AdListEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AddPicEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.BannerEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.MediaListEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.TaskListEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.WXEvent;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.http.RequestCallback;
import cn.bashiquan.cmj.sdk.http.RequestUrl;
import cn.bashiquan.cmj.sdk.manager.HomeManager;
import cn.bashiquan.cmj.sdk.utils.Constants;
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
                if(data.contains("删除成功")){
                    EventBus.getDefault().post(new MediaListEvent(MediaListEvent.EventType.CANCEL_TASK_SUCCESS,carNum,null));
                }else{
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
                if(data.contains("提交成功")){
                    String taskId = "";
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        JSONObject dataJsonObject = jsonObject.getJSONObject("data");
                        taskId = dataJsonObject.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(new AdListEvent(AdListEvent.EventType.ADD_TASK_SUCCESS,taskId));
                }else{
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
    public void uplodeImage(Class resposeBean,String imagePath,String imageName) {
        HttpClient.getInstance().uplodeImage(RequestUrl.ADD_MEDIA_PIC_URL, imagePath, imageName, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {

                EventBus.getDefault().post(new AddPicEvent(AddPicEvent.EventType.GET_ADD_PIC_SUCCESS,"d","ddd"));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new AddPicEvent(AddPicEvent.EventType.GET_ADD_PIC_FAILED,"d","ddd"));
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
