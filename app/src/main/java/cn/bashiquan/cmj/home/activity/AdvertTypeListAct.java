package cn.bashiquan.cmj.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.AdvertTypeListAdapter;
import cn.bashiquan.cmj.sdk.bean.AdListBean;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AdListEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/9/28.
 * 广告类型页
 */

public class AdvertTypeListAct extends BaseAct implements RefreshListView.OnRefreshListener, AdvertTypeListAdapter.AdvertTypeListener {
    private String className = AdvertTypeListAct.class.getName();
    private RefreshListView lv_listview;
    private AdvertTypeListAdapter adapter;
    private int selectIndex = -1;
    private TextView tv_next;
    private RelativeLayout rl_no_data;// 无数据时显示
    private List<AdListBean.AdBean> datas = new ArrayList<>();
    private int id; // 车辆id
    @Override
    public int contentView() {
        return R.layout.activity_advert_type_list;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("广告类型");
        setTitleLeft(true,"");
        lv_listview = (RefreshListView) findViewById(R.id.lv_listview);
        rl_no_data = (RelativeLayout) findViewById(R.id.rl_no_data);
        lv_listview.setOnRefreshListener(this);
        lv_listview.setPushEnable(false);
        rl_no_data.setVisibility(View.GONE);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        initData();
    }

    // 获取数据
    private void initData() {
        id = getIntent().getIntExtra("id",0);
        showProgressDialog(this,"",false);
        getCoreService().getHomeManager(className).getAdList(id);
    }

    public void initAdapter(){
        if(CollectionUtils.isEmpty(datas)){
            rl_no_data.setVisibility(View.VISIBLE);
            lv_listview.setVisibility(View.GONE);
            tv_next.setVisibility(View.GONE);
        }else{
            rl_no_data.setVisibility(View.GONE);
            lv_listview.setVisibility(View.VISIBLE);
            tv_next.setVisibility(View.VISIBLE);
        }
        if(adapter == null){
            adapter = new AdvertTypeListAdapter(this,datas,selectIndex,this);
            lv_listview.setAdapter(adapter);
        }else{
            adapter.setData(datas,selectIndex);
        }
        lv_listview.onRefreshComplete(true);
    }
    @Override
    public void onRefresh() {
        getCoreService().getHomeManager(className).getAdList(id);
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()){
            case R.id.tv_next:
                if(selectIndex == -1){
                    showToast("请选择一项广告");
                }else{
                    getCoreService().getHomeManager(className).addTask(id,datas.get(selectIndex).getId());
                }
                break;
        }
    }

    public void onEventMainThread(AdListEvent event){
        disProgressDialog();
        switch (event.getEventType()){
            case GET_AD_SUCCESS:
                List<AdListBean.AdBean> adBeens = event.getAdListBean().getData();
                datas.clear();
                if(!CollectionUtils.isEmpty(adBeens)){
                    datas.addAll(adBeens);
                }
                initAdapter();
                break;
            case GET_AD_FAILED:
                showToast(event.getMsg());
                break;
            case ADD_TASK_SUCCESS:
                Intent intent = new Intent(this,TaskListAct.class);
                intent.putExtra("task_id",event.getTaskId());
                startActivity(intent);
                finish();
                break;
            case ADD_TASK_FAILED:
                showToast(event.getMsg());
                break;
        }

    }


    @Override
    public void selectClick(int positon) {
        if(selectIndex != positon){
            selectIndex = positon;
        }
        adapter.setData(datas,selectIndex);
    }
}
