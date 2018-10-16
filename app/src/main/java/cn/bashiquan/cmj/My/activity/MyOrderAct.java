package cn.bashiquan.cmj.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.MainActivity;
import cn.bashiquan.cmj.My.adapter.MyOrderAdapter;
import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.activity.IntegralShopAct;
import cn.bashiquan.cmj.sdk.bean.MyOrderListBean;
import cn.bashiquan.cmj.sdk.bean.TaskFrbListBean;
import cn.bashiquan.cmj.sdk.event.MyManager.MyOrderEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/10/15.
 * 我的订单
 */

public class MyOrderAct extends BaseAct implements RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener, MyOrderAdapter.MyOrderListener {
    private String className = MyOrderAct.class.getName();
    private EditText et_search;
    private RefreshListView lv_listview;
    private MyOrderAdapter adapter;
    private int currentIndex = 0;
    List<MyOrderListBean.OrderBean> datas = new ArrayList<>();
    @Override
    public int contentView() {
        return R.layout.activity_my_order;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的订单");
        setTitleLeft(true,"");
        et_search  = (EditText)findViewById(R.id.et_search);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        lv_listview = (RefreshListView) findViewById(R.id.lv_listview);
        lv_listview.setOnRefreshListener(this);
        lv_listview.setOnItemClickListener(this);
        lv_listview.setPushEnable(false);
        et_search.setHint("请输入商品");
        findViewById(R.id.tv_cancla_search).setOnClickListener(this);
        findViewById(R.id.tv_shop).setOnClickListener(this);
        showProgressDialog(this,"",false);
        initData();

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_SEARCH)) {
                    showProgressDialog(MyOrderAct.this,"",false);
                    currentIndex = 0;
                   initData();
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        getCoreService().getMyManager(className).getOrderList(10,currentIndex*10,et_search.getText().toString().trim());
    }

    public void initAdapter() {
        if (adapter == null) {
            adapter = new MyOrderAdapter(this,this,datas);
            lv_listview.setAdapter(adapter);
        } else {
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
        currentIndex++;
        initData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()){
            case R.id.tv_cancla_search:
                et_search.setText("");
                showProgressDialog(MyOrderAct.this,"",false);
                currentIndex = 0;
                initData();
                Utils.closeKeybord(et_search,this);
                break;
            case R.id.tv_shop:
                Intent intent = new Intent(this, IntegralShopAct.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void onEventMainThread(MyOrderEvent event){
        disProgressDialog();
        switch (event.getEvent()){
            case GET_ORDER_LIST_SUCCESS:
                MyOrderListBean bean = event.getMyOrderListBean();
                if(bean != null && bean.getData() != null && bean.getData().getList() != null){
                    if(currentIndex == 0){
                        datas.clear();
                    }
                    datas.addAll(bean.getData().getList());
                    initAdapter();
                    if(bean.getData().getList().size() >= 10){
                        lv_listview.setPushEnable(true);
                    }else{
                        lv_listview.setPushEnable(false);
                    }
                }
                break;
            case REFUND_ORDER_SUCCESS:
               currentIndex = 0;
                initData();
                break;
            case GET_ORDER_LIST_FAILED:
                showToast(event.getMsg());
                break;
        }
    }

    @Override
    public void seeClick(int index) {
        Intent intent = new Intent(this,MyOrderInfoAct.class);
        intent.putExtra("id",datas.get(index).getId());
        startActivity(intent);
    }
}
