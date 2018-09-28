package cn.bashiquan.cmj.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.AdvertTypeListAdapter;
import cn.bashiquan.cmj.home.adapter.MediaListAdapter;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/9/28.
 * 广告类型页
 */

public class AdvertTypeListAct extends BaseAct implements RefreshListView.OnRefreshListener, AdvertTypeListAdapter.AdvertTypeListener {
    private RefreshListView lv_listview;
    private AdvertTypeListAdapter adapter;
    private int selectIndex = -1;
    private RelativeLayout rl_no_data;// 无数据时显示
    private List<String> datas = new ArrayList<>();
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
        rl_no_data.setVisibility(View.GONE);
        findViewById(R.id.tv_next).setOnClickListener(this);
        initData();
    }

    // 获取数据
    private void initData() {
        for(int i = 0; i < 5; i++){
            datas.add(i + "");
        }
        initAdapter();
    }

    public void initAdapter(){
        if(CollectionUtils.isEmpty(datas)){
            rl_no_data.setVisibility(View.VISIBLE);
            lv_listview.setVisibility(View.GONE);
        }else{
            rl_no_data.setVisibility(View.GONE);
            lv_listview.setVisibility(View.VISIBLE);
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
        new TextView(mContext).postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();

            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        new TextView(mContext).postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()){
            case R.id.tv_next:
                Intent intent = new Intent(this,TaskListAct.class);
                startActivity(intent);
                finish();
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
