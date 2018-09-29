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
import cn.bashiquan.cmj.home.adapter.TaskListAdapter;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/9/28.
 * 任务周期页
 */

public class TaskListAct extends BaseAct implements RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {
    private RefreshListView lv_listview;
    private TaskListAdapter adapter;
    private List<String> datas = new ArrayList<>();
    @Override
    public int contentView() {
        return R.layout.activity_task_list;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("任务周期");
        setTitleLeft(true,"");
        lv_listview = (RefreshListView) findViewById(R.id.lv_listview);
        lv_listview.setOnItemClickListener(this);
        lv_listview.setOnRefreshListener(this);
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
        if(adapter == null){
            adapter = new TaskListAdapter(this,datas);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // 进入监测页 添加照片
        Intent intent = new Intent(this,AddPicAct.class);
        startActivity(intent);
    }
}
