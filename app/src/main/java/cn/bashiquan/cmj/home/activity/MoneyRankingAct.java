package cn.bashiquan.cmj.home.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.My.adapter.MoneyRankAdapter;
import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.sdk.bean.MyDrawListBean;
import cn.bashiquan.cmj.sdk.bean.RangListBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.RangEvent;
import cn.bashiquan.cmj.utils.widget.MyListView;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/10/23.
 */

public class MoneyRankingAct extends BaseAct {
    private TextView tv_info;
    private TextView tv_join;
    private View headView;
    private MoneyRankAdapter adapter;
    private MyListView lv_listview;
    private List<RangListBean.RangBean> datas = new ArrayList<>();
    @Override
    public int contentView() {
        return R.layout.activity_money_rank;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("奖金排行榜");
        setTitleLeft(true, "");
        lv_listview = (MyListView)findViewById(R.id.lv_listview);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_join = (TextView)findViewById(R.id.tv_join);
        headView = LayoutInflater.from(this).inflate(R.layout.activity_money_rank_hean_view,null);
        initListener();
        showProgressDialog(this,"",false);
        getCoreService().getHomeManager("MoneyRankingAct").getRange();
    }

    private void initListener() {
        tv_info.setOnClickListener(this);
        tv_join.setOnClickListener(this);
    }

    public void initAdapter(){
        if(adapter == null){
            lv_listview.addHeaderView(headView);
            adapter = new MoneyRankAdapter(this,datas);
            lv_listview.setAdapter(adapter);
        }else{
            adapter.setData(datas);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_info:

                break;
        }
    }

    public void onEventMainThread(RangEvent event){
        disProgressDialog();
        switch (event.getEventType()){
            case GET_RANG_SUCCESS:
               RangListBean rangListBean = event.getRangListBean();
                datas.clear();
                if(rangListBean != null){
                    datas.addAll(rangListBean.getData());
                }
                initAdapter();
                break;
            case GETE_RANG_FAILED:
                showToast(event.getMsg());
                break;
        }

    }

}
