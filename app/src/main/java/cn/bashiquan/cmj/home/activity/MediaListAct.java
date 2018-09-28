package cn.bashiquan.cmj.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.MediaListAdapter;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/9/28.
 * 媒体信息
 */

public class MediaListAct extends BaseAct implements AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener, MediaListAdapter.MediaListener {
    private RefreshListView lv_listview;
    private MediaListAdapter adapter;
    private RelativeLayout rl_no_data;
    private List<String> datas = new ArrayList<>();
    @Override
    public int contentView() {
        return R.layout.activity_medialist;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("媒体信息");
        setTitleLeft(true,"");
        lv_listview = (RefreshListView) findViewById(R.id.lv_listview);
        rl_no_data = (RelativeLayout) findViewById(R.id.rl_no_data);
        lv_listview.setOnItemClickListener(this);
        lv_listview.setOnRefreshListener(this);
        findViewById(R.id.tv_add_meat).setOnClickListener(this);
        initData();
    }

    // 获取数据
    private void initData() {
        for(int i = 0; i < 20; i++){
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
            adapter = new MediaListAdapter(this,datas,this);
            lv_listview.setAdapter(adapter);
        }else{
            adapter.setData(datas);
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
            case R.id.tv_add_meat:
                showToast("添加媒体");
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(position == 0){
            //无任务进入 选择广告类型页
            Intent intentMediaType = new Intent(this,AdvertTypeListAct.class);
            startActivity(intentMediaType);
        }else{
            //有任务是进入 任务周期
            Intent intentTask = new Intent(this,TaskListAct.class);
            startActivity(intentTask);
        }
    }

    @Override
    public void cancleTaskClick(int positon) {

    }

    @Override
    public void camcarClick(int positon) {
        // 点击拍照进入任务周期
        Intent intentTask = new Intent(this,TaskListAct.class);
        startActivity(intentTask);
    }

    @Override
    public void taskClick(int positon) {
        // 接任务 进入广告类型页
        Intent intentMediaType = new Intent(this,AdvertTypeListAct.class);
        startActivity(intentMediaType);
    }
}
