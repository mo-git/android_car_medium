package cn.bashiquan.cmj.home.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bashiquan.cmj.MyApplication;
import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.sdk.bean.RegistRequestBean;
import cn.bashiquan.cmj.sdk.event.MyManager.VerifyEvent;
import cn.bashiquan.cmj.sdk.event.login.LoginEvent;
import cn.bashiquan.cmj.sdk.event.login.UnauthenticatedEvent;
import cn.bashiquan.cmj.sdk.service.CoreService;
import cn.bashiquan.cmj.utils.Utils;

/**
 * Created by mocf on 2018/9/26.
 */
public class RegistAct extends BaseAct {

    private  CountDownTimer downTimer;
    private TextView tv_sendnum;
    private EditText phone;
    private EditText code;
    private EditText regist_name;
    private String cityName = "";
    private boolean isClick = true;// 是否可点击发送验证码

    @Override
    public int contentView() {
        return R.layout.activity_regist;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.regist));
        setTitleLeft(true,"");
        tv_sendnum = (TextView) findViewById(R.id.tv_sendnum);
        phone = (EditText)findViewById(R.id.regist_phone);
        code = (EditText)findViewById(R.id.regist_code);
        regist_name = (EditText)findViewById(R.id.regist_name);
        tv_sendnum.setOnClickListener(this);
        findViewById(R.id.ll_regist).setOnClickListener(this);
        findViewById(R.id.btn_regist).setOnClickListener(this);
    }

    @Override
    public String getLocationCityName(String cityName) {
        this.cityName = cityName;
        return super.getLocationCityName(cityName);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_regist:
            case R.id.btn_regist:
                sendRegist(true);
                break;
            case R.id.tv_sendnum:
                if(isClick){
                    sendRegist(false);
                }
                break;
        }
    }

    private void sendRegist(boolean isRegist) {
        String phoneStr = phone.getText().toString().trim();
        String codStr = code.getText().toString().trim();
        String name = regist_name.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            showToast("请输入姓名");
            return;
        }
        if(TextUtils.isEmpty(phoneStr)){
           showToast("请输入手机号");
            return;
        }
        if(!Utils.isCellphone(phoneStr)){
           showToast("请输入正确的手机号");
            return;
        }
        if(isRegist){
            if(TextUtils.isEmpty(codStr)){
                showToast("验证码不能为空");
                return;
            }
            if(MyApplication.wxTokenBean != null && MyApplication.wxUserBean != null){
                showProgressDialog(this,"",false);
                RegistRequestBean registRequestBean = new RegistRequestBean();
                RegistRequestBean.RegistBean registBean = new RegistRequestBean.RegistBean();
                registBean.setCity(cityName);
                registBean.setProvince(MyApplication.wxUserBean.getProvince());
                registBean.setAvatar_url(MyApplication.wxUserBean.getHeadimgurl());
                registBean.setCode(codStr);
                registBean.setCountry(MyApplication.wxUserBean.getCountry());
                registBean.setMobile(phoneStr);
                registBean.setGender(MyApplication.wxUserBean.getSex() == 1 ? "男" : "女");
                registBean.setOpenid(MyApplication.wxTokenBean.getOpenid());
                registBean.setUnionid(MyApplication.wxTokenBean.getUnionid());
                registBean.setNickname(name);
                registRequestBean.setInfo(registBean);
                getCoreService().getLoginManager("Regist").regist(registRequestBean);
            }
        }else{
            showProgressDialog(this,"",false);
            getCoreService().getMyManager("regist").getVerifyCode(phoneStr);

        }
    }

    public void onEventMainThread(VerifyEvent event){
        disProgressDialog();
        startTimeCountDown();
        switch (event.getEvent()){
            case GET_VERIFY_SUCCESS:
                showToast(event.getMsg());
                break;
            case GET_VERIFY_FAILED:
                showToast(event.getMsg());
                break;
        }
    }
    public void onEventMainThread(LoginEvent event){
        disProgressDialog();
        switch (event.getEvent()){
            case REGIST_SUCCESS:
                showToast(event.getMsg());
                getCoreService().getLoginManager("MainAct").login(MyApplication.wxTokenBean.getUnionid());
                finish();
                break;
            case REGIST_FAILED:
                showToast(event.getMsg());
                break;
        }

    }

    // 计时
    public void startTimeCountDown(){
        downTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                isClick = false;
                tv_sendnum.setText((l / 1000) + "秒");
            }

            @Override
            public void onFinish() {
                isClick = true;
                tv_sendnum.setText("重新发送");
                downTimer.cancel();
            }
        };
        downTimer.start();
    }
}
