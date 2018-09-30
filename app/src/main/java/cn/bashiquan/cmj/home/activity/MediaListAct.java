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
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.MediaListAdapter;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
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

    private List<String> datas = new ArrayList<>();
    private List<String> searchdatas = new ArrayList<>();
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
                    lv_listview.setPushEnable(true);
                    initAdapter(datas);
                }else{
                    searchdatas.clear();
                    for (int i = 0; i < datas.size(); i++){
                        if(datas.get(i).contains(editable.toString().trim())){
                            searchdatas.add(datas.get(i));
                        }
                    }
                    lv_listview.setPullEnable(false);
                    lv_listview.setPushEnable(false);
                    initAdapter(searchdatas);
                }
            }
        });
    }

    // 获取数据
    private void initData() {
        for(int i = 0; i < 20; i++){
            datas.add(i + "");
        }
        initAdapter(datas);
    }

    public void initAdapter(List<String> mDatas){
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
                Intent intent = new Intent(this,AddMediaAct.class);
                startActivity(intent);
                break;
            case R.id.tv_cancla_search:
                et_search.setText("");
                Utils.closeKeybord(et_search,this);
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

    // 监测页提交成功
    public void onEventMainThread(AddPicCloseEvent event){
        finish();
    }
}
