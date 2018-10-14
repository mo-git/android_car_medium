package cn.bashiquan.cmj.sdk.manager.iml;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import cn.bashiquan.cmj.sdk.bean.NoticeListBean;
import cn.bashiquan.cmj.sdk.bean.TaskFrbListBean;
import cn.bashiquan.cmj.sdk.event.TaskManagerEvent.NoticeListEvent;
import cn.bashiquan.cmj.sdk.event.TaskManagerEvent.TaskFrgListEvent;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.http.RequestCallback;
import cn.bashiquan.cmj.sdk.http.RequestUrl;
import cn.bashiquan.cmj.sdk.manager.TaskManager;
import de.greenrobot.event.EventBus;

/**
 * Created by mocf on 2018/9/26.
 */
public class TaskManagerIml implements TaskManager {
    private Context mContext;
    private String className;
    private Gson mGson;
    private HttpClient httpClient = HttpClient.getInstance();
    private static TaskManager mInstance;
    @Override
    public void init(Context mContext) {
        this.mContext = mContext;
        mGson = new Gson();
    }
    public static TaskManager getInstance(){
        if(mInstance == null){
            mInstance = new TaskManagerIml();
        }
        return mInstance;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }


    @Override
    public void getTaskList(int state,int limit,int offset) {
        String url = RequestUrl.getTaskFrgUrl(state,limit,offset);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                TaskFrbListBean taskFrbListBean =  mGson.fromJson(data, TaskFrbListBean.class);
                EventBus.getDefault().post(new TaskFrgListEvent(TaskFrgListEvent.EventType.GET_TASKFRG_SUCCESS,"",taskFrbListBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new TaskFrgListEvent(TaskFrgListEvent.EventType.GET_TASKFRG_FAILED,cause.getMessage()));

            }
        });
    }

    @Override
    public void getNoticeList(String city,int limit,int offset) {
        String url = RequestUrl.getNoticelist_url(city,limit,offset);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                NoticeListBean noticeListBean = mGson.fromJson(data,NoticeListBean.class);
                EventBus.getDefault().post(new NoticeListEvent(NoticeListEvent.EventType.GET_NOTICELIST_SUCCESS,noticeListBean));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new NoticeListEvent(NoticeListEvent.EventType.GET_NOTICELIST_FAILED,cause.getMessage()));

            }
        });
    }

    @Override
    public void getNoticeInfo(String city, int id) {
        String url = RequestUrl.getNoticeInfo_url(city,id);
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
//                NoticeListBean noticeListBean = mGson.fromJson(data,NoticeListBean.class);
                EventBus.getDefault().post(new NoticeListEvent(NoticeListEvent.EventType.GET_NOTICE_INFO_SUCCESS,data));
            }

            @Override
            public void onFailure(Throwable cause) {
                EventBus.getDefault().post(new NoticeListEvent(NoticeListEvent.EventType.GET_NOEICT_INFO_FAILED,cause.getMessage()));

            }
        });
    }
}
