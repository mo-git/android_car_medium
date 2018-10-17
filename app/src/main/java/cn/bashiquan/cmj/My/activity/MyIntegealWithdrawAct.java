package cn.bashiquan.cmj.My.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.My.adapter.IntegralWithAdapter;
import cn.bashiquan.cmj.MyApplication;
import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.sdk.bean.IntegralListBean;
import cn.bashiquan.cmj.sdk.bean.IntegralWithdrawBean;
import cn.bashiquan.cmj.sdk.event.MyManager.IntegralEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/10/15.
 * 提现页面
 */

public class MyIntegealWithdrawAct extends BaseAct {
    private String className = MyIntegealWithdrawAct.class.getName();
    private TextView tv_integeal_num; //当前积分
    private TextView tv_money; // 剩余金额
    private TextView tv_msg; // 剩余金额
    private EditText et_num;
    private RefreshListView lv_listview;
    private RelativeLayout rl_no_data;
    private int currentIndex = 0;
    private int point = 0;
    private int limit = 0;
    private double money;
    private IntegralWithAdapter adapter;
    private List<IntegralWithdrawBean.WithdrawBean> datas = new ArrayList<>();
    @Override
    public int contentView() {
        return R.layout.activity_withdraw;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("积分提现");
        setTitleLeft(true,"");
        lv_listview = (RefreshListView)findViewById(R.id.lv_listview) ;
        tv_integeal_num = (TextView) findViewById(R.id.tv_integeal_num);
        tv_money = (TextView) findViewById(R.id.tv_money);
        rl_no_data = (RelativeLayout) findViewById(R.id.rl_no_data);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        et_num = (EditText) findViewById(R.id.et_num);
        lv_listview.setPullEnable(false);
        initListener();
        iniData();
    }

    private void initListener() {
        findViewById(R.id.tv_duihuan).setOnClickListener(this);
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString().trim();
                if(TextUtils.isEmpty(str)){
                    tv_msg.setVisibility(View.INVISIBLE);
                }else{
                    if(Double.valueOf(str).doubleValue() == 0){
                        et_num.setText("");
                    }else{
                        if(point > Double.valueOf(str).doubleValue()){
                            tv_msg.setVisibility(View.INVISIBLE);
                        }else{
                            tv_msg.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    private void iniData() {
        if(MyApplication.userBean != null){
            point = MyApplication.userBean.getData().getPoint();
        }
        tv_integeal_num.setText(String.valueOf(point));
        money = ((double)point/100);
        tv_money.setText(Utils.keepTwoSecimal2(String.valueOf(money)));
        getCoreService().getMyManager(className).getWithdrawlimit();
        getCoreService().getMyManager(className).getWithdrawList(10,currentIndex*10);
    }

    public void setAdapter(){
        if(CollectionUtils.isEmpty(datas)){
            rl_no_data.setVisibility(View.VISIBLE);
            lv_listview.setVisibility(View.GONE);
        }else{
            rl_no_data.setVisibility(View.GONE);
            lv_listview.setVisibility(View.VISIBLE);
        }

        if(adapter == null){
            adapter = new IntegralWithAdapter(this,datas);
            lv_listview.setAdapter(adapter);
        }else{
            adapter.setData(datas);
        }
        lv_listview.onRefreshComplete(true);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_duihuan:
                String strPoint = et_num.getText().toString().trim();
                if(TextUtils.isEmpty(strPoint)){
                    showToast("请输入兑换的积分!");
                }else{
                    if(Double.valueOf(strPoint).doubleValue() < limit){
                        showToast("兑换积分不能少于" + limit);
                        et_num.setText("");
                    }else if(Double.valueOf(strPoint).doubleValue() > point){
                        showToast("积分不足!");
                    }else{
                        getCoreService().getMyManager(className).withdrawMoney(strPoint);
                    }
                }
                break;
        }
    }


    public void onEventMainThread(IntegralEvent event){
        disProgressDialog();
        switch (event.getEvent()){
            case GET_WITHDRAWLIMIT_SUCCESS:
                limit = event.getLimit();
                break;

            case GET_WITHDRAWLIST_SUCCESS:
                IntegralWithdrawBean bean = event.getIntegralWithdrawBean();
                if(bean != null && bean.getData() != null){
                    if(currentIndex == 0){
                        datas.clear();
                    }
                    if(bean.getData().size() >= 10){
                        lv_listview.setPushEnable(true);
                    }else{
                        lv_listview.setPushEnable(false);
                    }
                    datas.addAll(bean.getData());
                }
                setAdapter();
                break;
            case GET_WITHDRAWLIST_FAILED:
                lv_listview.onRefreshComplete(true);
                break;
            case GET_MONEY_SUCCESS:
                showToast(event.getMsg());
                finish();
                break;
            case GET_MONEY_FAILED:
                showToast(event.getMsg());
                break;

        }

    }
}
