package cn.bashiquan.cmj.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.IntegralShopAdapter;
import cn.bashiquan.cmj.home.adapter.MediaListAdapter;
import cn.bashiquan.cmj.sdk.bean.ProductListBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.ShopEvent;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/9/28.
 * 积分商城
 */

public class IntegralShopAct extends BaseAct implements AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener{
    private String className = IntegralShopAct.class.getName();
    private RefreshListView lv_listview;
    private IntegralShopAdapter adapter;
    private EditText et_search;

    private List<ProductListBean.ProductBean> datas = new ArrayList<>();
    private int currentIndex = 0;
    @Override
    public int contentView() {
        return R.layout.activity_integral;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("产品列表");
        setTitleLeft(true,"");
        lv_listview = (RefreshListView) findViewById(R.id.lv_listview);
        et_search = (EditText) findViewById(R.id.et_search);
        lv_listview.setOnItemClickListener(this);
        lv_listview.setOnRefreshListener(this);
        findViewById(R.id.tv_cancla_search).setOnClickListener(this);
        showProgressDialog(this,"",false);
        initData();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                currentIndex = 0;
                initData();
            }
        });
    }

    // 获取数据
    private void initData() {
        getCoreService().getHomeManager(className).getProductList(10,currentIndex * 10,et_search.getText().toString().trim());
    }

    public void initAdapter(){
        if(adapter == null){
            adapter = new IntegralShopAdapter(this,datas);
            lv_listview.setAdapter(adapter);
        }else{
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
                Utils.closeKeybord(et_search,this);
                break;
        }
    }


    public void onEventMainThread(ShopEvent event){
        disProgressDialog();
        switch (event.getEventType()){
            case GET_PROTECT_SUCCESS:
                ProductListBean bean = event.getProductListBean();
                if(currentIndex == 0){
                    datas.clear();
                }
                if(bean != null && bean.getData() != null && bean.getData().getList() != null){
                    if(bean.getData().getList().size() >= 10){
                        lv_listview.setPushEnable(false);
                    }else{
                        lv_listview.setPushEnable(false);
                    }
                    datas.addAll(bean.getData().getList());
                }
                initAdapter();
                break;
            case GET_PROTECT_FAILED:
                showToast(event.getMsg());
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            //有任务是进入 任务周期
            Intent intent = new Intent(this,IntegralShopInfoAct.class);
        intent.putExtra("id",datas.get(position).getId());
        intent.putExtra("cover",datas.get(position).getCover());
            startActivity(intent);
    }

}
