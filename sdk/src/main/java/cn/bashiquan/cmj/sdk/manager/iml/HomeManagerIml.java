package cn.bashiquan.cmj.sdk.manager.iml;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import cn.bashiquan.cmj.sdk.bean.BannersBean;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AddPicEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.BannerEvent;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.http.RequestCallback;
import cn.bashiquan.cmj.sdk.http.RequestUrl;
import cn.bashiquan.cmj.sdk.manager.HomeManager;
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

}
