package cn.bashiquan.cmj.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.TaskListAdapter;
import cn.bashiquan.cmj.sdk.bean.TaskListBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.TaskListEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/9/28.
 * 任务周期页
 */

public class TaskListAct extends BaseAct implements RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {
    private String className = TaskListAct.class.getName();
    private RefreshListView lv_listview;
    private TaskListAdapter adapter;
    private List<TaskListBean.TaskBean> datas = new ArrayList<>();
    private String task_id = ""; // 任务id
    private String cardNum = ""; // 任务id
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
        lv_listview.setPullEnable(false);
        lv_listview.setPushEnable(false);
        initData();
    }

    // 获取数据
    private void initData() {
        task_id = getIntent().getStringExtra("task_id");
        cardNum = getIntent().getStringExtra("cardNum");
        showProgressDialog(this,"",false);
        getCoreService().getHomeManager(className).getTaskList(task_id);
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
    public void onRefresh() {}

    @Override
    public void onLoadMore() {}


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        // 进入监测页 添加照片
        if(datas.get(position).isOn()){
            Intent intent = new Intent(this,AddPicAct.class);
            intent.putExtra("id",datas.get(position).getId());
            intent.putExtra("cardNum",cardNum);
            startActivity(intent);
        }
    }

    public void onEventMainThread(TaskListEvent event) {
        disProgressDialog();
        switch (event.getEventType()){
            case GET_TASK_SUCCESS:
                TaskListBean taskListBean = event.getTaskListBean();
                datas.clear();
                if(taskListBean != null && !CollectionUtils.isEmpty(taskListBean.getData())){
                    datas.addAll(taskListBean.getData());
                }
                initAdapter();
                break;
            case GET_TASK_FAILED:
                showToast(event.getMsg());
                break;
        }
    }

//
//    // 监测页提交成功
//    public void onEventMainThread(AddPicCloseEvent event){
//       finish();
//    }
}
