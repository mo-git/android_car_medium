package cn.bashiquan.cmj.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.MediaListAdapter;
import cn.bashiquan.cmj.sdk.bean.MediaListBean;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AdListEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.MediaListEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.DialogListener;
import cn.bashiquan.cmj.utils.MyDialog;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/9/28.
 * 媒体信息
 */

public class MediaListAct extends BaseAct implements AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener, MediaListAdapter.MediaListener {
    private RefreshListView lv_listview;
    private MediaListAdapter adapter;
    private RelativeLayout rl_no_data;
    private EditText et_search;
    private int currentIndex = 0;

    private List<MediaListBean.MediaBean> datas = new ArrayList<>();
    private List<MediaListBean.MediaBean> searchdatas = new ArrayList<>();
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
        et_search = (EditText) findViewById(R.id.et_search);
        lv_listview.setOnItemClickListener(this);
        lv_listview.setOnRefreshListener(this);
        findViewById(R.id.tv_add_meat).setOnClickListener(this);
        findViewById(R.id.tv_cancla_search).setOnClickListener(this);
        initData();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable.toString())){
                    lv_listview.setPullEnable(true);
                    initAdapter(datas,isUpload);
                }else{
                    searchdatas.clear();
                    for (int i = 0; i < datas.size(); i++){
                        if(datas.get(i).getCar_number().contains(editable.toString().trim())){
                            searchdatas.add(datas.get(i));
                        }
                    }
                    lv_listview.setPullEnable(false);
                    lv_listview.setPushEnable(false);
                    initAdapter(searchdatas,false);
                }
            }
        });
    }

    // 获取数据
    private void initData() {
        initAdapter(datas,isUpload);
        getCoreService().getHomeManager(MediaListAct.class.getName()).getMeidList(10,currentIndex);
    }

    public void initAdapter(List<MediaListBean.MediaBean> mDatas,boolean isUpload){
        if(CollectionUtils.isEmpty(mDatas)){
            rl_no_data.setVisibility(View.VISIBLE);
            lv_listview.setVisibility(View.GONE);
        }else{
            rl_no_data.setVisibility(View.GONE);
            lv_listview.setVisibility(View.VISIBLE);
        }
        if(adapter == null){
            adapter = new MediaListAdapter(this,mDatas,this);
            lv_listview.setAdapter(adapter);
        }else{
            adapter.setData(mDatas);
        }
        lv_listview.onRefreshComplete(true);
        lv_listview.setPushEnable(isUpload);
    }
    @Override
    public void onRefresh() {
        currentIndex = 0;
        getCoreService().getHomeManager(MediaListAct.class.getName()).getMeidList(10,currentIndex);
    }

    @Override
    public void onLoadMore() {
        currentIndex++;
        getCoreService().getHomeManager(MediaListAct.class.getName()).getMeidList(10,currentIndex);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()){
            case R.id.tv_add_meat:
                Intent intent = new Intent(this,AddMediaAct.class);
                startActivity(intent);
                break;
            case R.id.tv_cancla_search:
                et_search.setText("");
                Utils.closeKeybord(et_search,this);
                break;
        }
    }

    boolean isUpload = false;
    public void onEventMainThread(MediaListEvent event){
       switch (event.getEventType()){
           case GET_MEDIA_SUCCESS:
               List<MediaListBean.MediaBean> mediaBeens = event.getMediaListBean().getData().getList();
               if(mediaBeens.size() == 10){
                   isUpload = true;
               }
               if(currentIndex == 0){
                   datas.clear();
               }
               datas.addAll(mediaBeens);
               initAdapter(datas,isUpload);
               break;
           case GET_MEDIA_FAILED:
              showToast(event.getMsg());
               break;
           case CANCEL_TASK_SUCCESS:
              showToast("车辆" + event.getMsg() + "任务取消成功");
               currentIndex = 0;
               initData();
               break;
           case CANCLE_TASK_FAILED:
              showToast("车辆" + event.getMsg() + "任务取消失败");
               break;
       }
    }

    public void onEventMainThread(AdListEvent event){
        switch (event.getEventType()){

            case ADD_TASK_SUCCESS:
                currentIndex = 0;
                initData();
                break;
        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(TextUtils.isEmpty(datas.get(position).getTask_id())){
            //无任务进入 选择广告类型页
            Intent intentMediaType = new Intent(this,AdvertTypeListAct.class);
            int id = 0;
            if(TextUtils.isEmpty(et_search.getText().toString().trim())){
                id = datas.get(position).getId();
            }else{
                id = searchdatas.get(position).getId();
            }
            intentMediaType.putExtra("id",id);
            startActivity(intentMediaType);
        }else{
            //有任务是进入 任务周期
            Intent intent = new Intent(this,TaskListAct.class);
            String taskId = "";
            if(TextUtils.isEmpty(et_search.getText().toString().trim())){
                taskId = datas.get(position).getTask_id();
            }else{
                taskId = searchdatas.get(position).getTask_id();
            }
            intent.putExtra("task_id",taskId);
            startActivity(intent);
        }
    }

    @Override
    public void cancleTaskClick(final int positon) {
        String msg = "取消任务,将无法完成此类广告所有任务,请谨慎考虑!";
        MyDialog.showDialogDetal2(this, msg, "", "确定", "取消", false, new DialogListener() {
            @Override
            public void onSelect() {
                int id = datas.get(positon).getId();
                String cardNum = datas.get(positon).getCar_number();
                getCoreService().getHomeManager(MediaListAct.class.getName()).cancleTask(id,cardNum);
            }

            @Override
            public void onCancle() {

            }
        });

    }

    @Override
    public void camcarClick(int positon) {
        // 点击拍照进入任务周期
        Intent intentTask = new Intent(this,TaskListAct.class);
        startActivity(intentTask);
    }

    @Override
    public void taskClick(int position) {
        // 接任务 进入广告类型页
        Intent intentMediaType = new Intent(this,AdvertTypeListAct.class);
        int id = 0;
        if(TextUtils.isEmpty(et_search.getText().toString().trim())){
            id = datas.get(position).getId();
        }else{
            id = searchdatas.get(position).getId();
        }
        intentMediaType.putExtra("id",id);
        startActivity(intentMediaType);
    }

    // 监测页提交成功
    public void onEventMainThread(AddPicCloseEvent event){
        finish();
    }
}
