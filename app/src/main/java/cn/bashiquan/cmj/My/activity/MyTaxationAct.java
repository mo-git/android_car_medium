package cn.bashiquan.cmj.My.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.sdk.bean.RequestSubmitTaxationBean;
import cn.bashiquan.cmj.sdk.event.MyManager.TaxationEvent;
import cn.bashiquan.cmj.utils.Utils;

/**
 * Created by mo on 2018/10/15.
 * 我的报单
 */

public class MyTaxationAct extends BaseAct{

    private EditText et_name;
    private EditText et_phone;
    private EditText et_mark;


    @Override
    public int contentView() {
        return R.layout.activity_my_taxation;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("报单");
        setTitleLeft(true,"");
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_mark = (EditText) findViewById(R.id.et_mark);

        findViewById(R.id.tv_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_submit:
             String etName = et_name.getText().toString().trim();
             String etPhone = et_phone.getText().toString().trim();
             String etMark = et_mark.getText().toString().trim();
                if(TextUtils.isEmpty(etName)){
                    showToast("请输入姓名");
                }else if(TextUtils.isEmpty(etPhone)){
                    showToast("请输入手机号");
                }else if(!Utils.isCellphone(etPhone)){
                    showToast("请输输入正确的手机号");
                }else if(TextUtils.isEmpty(etMark)) {
                    showToast("备注内容");
                }else{
                    RequestSubmitTaxationBean bean = new RequestSubmitTaxationBean();
                    bean.setMobile(etPhone);
                    bean.setName(etName);
                    bean.setContent(etMark);
                    getCoreService().getMyManager("MyTaxation").submitTaxation(bean);
                }
                break;
        }
    }


    public void onEventMainThread(TaxationEvent event){
        switch (event.getEvent()){
            case SUMIT_SUCCESS:
                showToast(event.getMsg());
                finish();
                break;
            case SUBMIT_FAILED:
                showToast(event.getMsg());
                break;
        }

    }
}
