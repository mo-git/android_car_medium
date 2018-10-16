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

import cn.bashiquan.cmj.My.activity.MyOrderAct;
import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.IntegralShopAdapter;
import cn.bashiquan.cmj.sdk.bean.ProductBean;
import cn.bashiquan.cmj.sdk.bean.ProductListBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.ShopEvent;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/9/28.
 * 积分商城
 */

public class IntegralShopPayAct extends BaseAct{
    private String className = IntegralShopPayAct.class.getName();
    private TextView tv_name;
    private TextView tv_stock;
    private TextView tv_type_name;
    private TextView tv_price;
    private EditText et_num;
    private TextView tv_total_price;
    private String name;
    private int id;
    private String stock = ""; // 库存
    private ProductBean.InputDataBean data;
    @Override
    public int contentView() {
        return R.layout.activity_integral_pay;
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
        et_num = (EditText) findViewById(R.id.et_num);
        tv_stock = (TextView) findViewById(R.id.tv_stock);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_type_name = (TextView) findViewById(R.id.tv_type_name);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);


        findViewById(R.id.tv_reduce).setOnClickListener(this);
        findViewById(R.id.tv_add).setOnClickListener(this);
        findViewById(R.id.tv_cancle).setOnClickListener(this);
        findViewById(R.id.tv_que_pay).setOnClickListener(this);
//        showProgressDialog(this,"",false);
        initData();
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if(TextUtils.isEmpty(editable.toString().trim())){
                        et_num.setText("0");
                    }else if(data != null && Integer.valueOf(editable.toString().trim()) > Integer.valueOf(stock)){
                        et_num.setText(stock);
                    }
                }catch (Exception e){
                    showToast("数量类型转换异常");
                }

            }
        });
    }

    // 获取数据
    private void initData() {
        name = getIntent().getStringExtra("name");
        id = getIntent().getIntExtra("id",0);
        data = (ProductBean.InputDataBean)getIntent().getSerializableExtra("data");
        tv_name.setText(name);
        stock = String.valueOf(Integer.valueOf(data.getStock()) - data.getSellNum());
        tv_stock.setText("库存:" + stock);
        tv_type_name.setText(data.getKey());
        tv_price.setText(data.getPrice());
        tv_total_price.setText(data.getPrice());


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()){
            case R.id.tv_reduce:
               int num1 =  Integer.valueOf(et_num.getText().toString().trim());
                if(num1 > 0 && data != null){
                    num1 -= 1;
                    et_num.setText(String.valueOf(num1));
                    tv_total_price.setText(String.valueOf(num1*(Integer.valueOf(data.getPrice()))));
                }
                break;
            case R.id.tv_add:
                int num =  Integer.valueOf(et_num.getText().toString().trim());
                if(data != null && Double.valueOf(stock) > num){
                    num += 1;
                    et_num.setText(String.valueOf(num));
                    tv_total_price.setText(String.valueOf(num*(Integer.valueOf(data.getPrice()))));
                }else{
                    showToast("库存不足");
                }
                break;
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_que_pay:
                if(TextUtils.isEmpty(et_num.getText().toString().trim())){
                    showToast("购买数量不能小于1!");
                }else{
                    getCoreService().getHomeManager(className).payProduct(id,data.getKey(),et_num.getText().toString().trim());
                }

                break;
        }
    }


    public void onEventMainThread(ShopEvent event){
        disProgressDialog();
        switch (event.getEventType()){
            case PAY_PRODUCT_SUCCESS:
                showToast(event.getMsg());
                Intent intent = new Intent(this, MyOrderAct.class);
                startActivity(intent);
                finish();
                break;
            case PAY_PRODUCT_FAILED:
                showToast(event.getMsg());
                break;
        }

    }

}
