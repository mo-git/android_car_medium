package cn.bashiquan.cmj.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.My.adapter.IntegralAdapter;
import cn.bashiquan.cmj.MyApplication;
import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.sdk.bean.IntegralListBean;
import cn.bashiquan.cmj.sdk.event.MyManager.IntegralEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/10/15.
 * 个人积分
 */

public class MyIntegralAct extends BaseAct implements AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener {

    private String className = MyIntegralAct.class.getName();
    private TextView tv_num; // 积分
    private RelativeLayout rl_no_data;
    private RefreshListView listView;
    private IntegralAdapter adapter;
    private List<IntegralListBean.IntegralBean> datas = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    public int contentView() {
        return R.layout.activity_my_integral;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("个人积分");
        setTitleLeft(true,"");

        tv_num = (TextView) findViewById(R.id.tv_num);
        listView = (RefreshListView) findViewById(R.id.lv_listview);
        rl_no_data = (RelativeLayout)findViewById(R.id.rl_no_data);
        listView.setPushEnable(false);
        listView.setPullEnable(false);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        findViewById(R.id.tv_reflect).setOnClickListener(this);
        showProgressDialog(this,"",false);
        initData();
    }

    private void initData() {
        getCoreService().getMyManager(className).getIntegralList(10,currentIndex*10);
        if(MyApplication.userBean != null){
            tv_num.setText(String.valueOf(MyApplication.userBean.getData().getPoint()));
        }
    }

    public void initAdapter(){
        if(CollectionUtils.isEmpty(datas)){
            listView.setVisibility(View.GONE);
            rl_no_data.setVisibility(View.VISIBLE);
        }else{
            listView.setVisibility(View.VISIBLE);
            rl_no_data.setVisibility(View.GONE);
        }
        if(adapter == null){
            adapter = new IntegralAdapter(this,datas);
            listView.setAdapter(adapter);
        }else{
            adapter.setData(datas);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_reflect:
                Intent intent = new Intent(this,MyIntegealWithdrawAct.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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


    public void onEventMainThread(IntegralEvent event){
        disProgressDialog();
        switch (event.getEvent()){
            case GET_INTEGRAL_LIST_SUCCESS:
                IntegralListBean bean = event.getIntegralListBean();
                if(bean != null && bean.getData() != null){
                    if(currentIndex == 0){
                        datas.clear();
                    }
                    if(bean.getData().size() >= 10){
                        listView.setPushEnable(true);
                    }else{
                        listView.setPushEnable(false);
                    }
                    datas.addAll(bean.getData());
                    initAdapter();
                }
                break;
            case GET_INTEGRAL_LIST_FAILED:
                showToast(event.getMsg());
                break;
            case GET_MONEY_SUCCESS:
                currentIndex = 0;
                getCoreService().getMyManager(className).getIntegralList(10,currentIndex*10);
                break;
        }

    }
}
