package cn.bashiquan.cmj.My.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.My.adapter.MyOrderAdapter;
import cn.bashiquan.cmj.My.adapter.MyOrderInfoAdapter;
import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.sdk.bean.MyOrderInfotBean;
import cn.bashiquan.cmj.sdk.bean.MyOrderListBean;
import cn.bashiquan.cmj.sdk.event.MyManager.MyOrderEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/10/15.
 * 个人积分
 */

public class MyOrderInfoAct extends BaseAct {
    private String className = MyOrderInfoAct.class.getName();
    private String id;
    private RefreshListView lv_listview;
    private MyOrderInfoAdapter adapter;
    private List<MyOrderInfotBean.OrderInfoBean> datas = new ArrayList<>();
    @Override
    public int contentView() {
        return R.layout.activity_my_orderinfo;
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
        lv_listview = (RefreshListView)findViewById(R.id.lv_listview);
        lv_listview.setPushEnable(false);
        lv_listview.setPullEnable(false);
        showProgressDialog(this,"",false);
        initData();
        findViewById(R.id.tv_refund).setOnClickListener(this);
    }

    private void initData() {
        id = getIntent().getStringExtra("id");
        getCoreService().getMyManager(className).getorderToken(id);
    }

    public void setAdapter(){
        if(adapter == null){
            adapter = new MyOrderInfoAdapter(this,datas);
            lv_listview.setAdapter(adapter);
        }else{
            adapter.setData(datas);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_refund:
            getCoreService().getMyManager(className).rufundOrder(id);
                break;
        }
    }

    public void onEventMainThread(MyOrderEvent event){
        disProgressDialog();
        switch (event.getEvent()){
            case GET_ORDERTOKEN_SUCCESS:
                MyOrderInfotBean bean = event.getMyOrderInfotBean();
                datas.clear();
                if(bean != null && !CollectionUtils.isEmpty(bean.getData().getTokens())){
                    datas.addAll(bean.getData().getTokens());
                    setAdapter();
                }
                break;
            case GET_ORDERTOKEN_FAILED:
                showToast(event.getMsg());
                break;
            case REFUND_ORDER_SUCCESS:
                showToast(event.getMsg());
                finish();
                break;
            case REFUND_ORDER_FAILED:
                showToast(event.getMsg());
                break;
        }

    }
}
