package cn.bashiquan.cmj.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import cn.bashiquan.cmj.MyApplication;
import cn.bashiquan.cmj.R;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.home.activity.NoticeInfoAct;
import cn.bashiquan.cmj.home.adapter.NoticeAdapter;
import cn.bashiquan.cmj.base.BaseFrg;
import cn.bashiquan.cmj.sdk.bean.NoticeListBean;
import cn.bashiquan.cmj.sdk.event.TaskManagerEvent.NoticeListEvent;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mocf on 2018/9/26.
 * 公告页面
 */
public class NoticeFrg extends BaseFrg implements AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener {
    private String TAG = NoticeFrg.class.getSimpleName();
    private View contentView;
    private NoticeAdapter adapter;
    private RefreshListView lv_listview;
    private List<NoticeListBean.NoticeBean> datas = new ArrayList<>();
    private String citiName;
    private int currentIndex = 0;

    @Override
    public int contentView() {
        return R.layout.frg_consult;
    }
    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentView = getContentView();
        setTitle("公告");
        lv_listview = (RefreshListView) contentView.findViewById(R.id.lv_listview);
        lv_listview.setOnItemClickListener(this);
        lv_listview.setOnRefreshListener(this);
        showProgressDialog(getActivity(),"",false);
        initData();
    }

    private void initData() {
       getCoreService().getTaskManager("NoticeFrg").getNoticeList(MyApplication.cityName,10,currentIndex*10);
    }

    private void setAdapter() {
        if(adapter == null){
            adapter = new NoticeAdapter(mContext,datas);
            lv_listview.setAdapter(adapter);
        }else{
            adapter.setData(datas);
        }
        lv_listview.onRefreshComplete(true);
    }

    @Override
    public void onRefresh() {
      currentIndex = 0;
        initData();
    }

    @Override
    public void onLoadMore() {
        currentIndex ++;
        initData();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
       showProgressDialog(getActivity(),"",false);
        getCoreService().getTaskManager("NoticeFrg").getNoticeInfo(citiName,datas.get(position).getId());
    }

    public void onEventMainThread(NoticeListEvent event){
        disProgressDialog();
        switch (event.getEventType()){
            case GET_NOTICELIST_SUCCESS:
                NoticeListBean noticeListBean = event.getNoticeListBean();
                if(noticeListBean != null){
                    if(currentIndex == 0){
                        datas.clear();
                    }
                    datas.addAll(noticeListBean.getData());
                    setAdapter();
                    if(datas.size() >= 10){
                        lv_listview.setPushEnable(true);
                    }else{
                        lv_listview.setPushEnable(false);
                    }
                }
                break;
            case GET_NOTICELIST_FAILED:
                lv_listview.onRefreshComplete(true);
                showToat(event.getMsg());
                break;
            case GET_NOTICE_INFO_SUCCESS:
                String content = "";
                if(!TextUtils.isEmpty(event.getMsg())){
                    content = event.getMsg();
                }
                Intent intent = new Intent(getActivity(), NoticeInfoAct.class);
                intent.putExtra("content", content);
                startActivity(intent);
                break;
            case GET_NOEICT_INFO_FAILED:
                break;
        }

    }

}
