package cn.bashiquan.cmj.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.base.BaseAct;

/**
 * Created by mocf on 2017/7/20.
 */
public class ChangePasswordAct extends BaseAct {

    private  CountDownTimer downTimer;
    private TextView tv_sendnum;
    private EditText phone;
    private EditText code;
    private EditText password;
    private EditText password1;
    private boolean isClick = true;// 是否可点击发送验证码

    @Override
    public int contentView() {
        return R.layout.activity_changepassword;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.change_password));
        setTitleLeft(true,"");
        tv_sendnum = (TextView) findViewById(R.id.tv_sendnum);
        phone = (EditText)findViewById(R.id.change_phone);
        code = (EditText)findViewById(R.id.change_code);
        password = (EditText)findViewById(R.id.change_password);
        password1 = (EditText)findViewById(R.id.change_password1);
        tv_sendnum.setOnClickListener(this);
        findViewById(R.id.ll_change).setOnClickListener(this);
        findViewById(R.id.btn_change).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_change:
            case R.id.btn_change:
                sendRegist();
                break;
            case R.id.tv_sendnum:
                if(isClick){
                    timer();
                }
                break;
        }
    }

    private void sendRegist() {
        String phoneStr = phone.getText().toString().trim();
        String codStr = code.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        String password1Str = password1.getText().toString().trim();
        if(TextUtils.isEmpty(phoneStr)){
            showToast("手机号不能为空");
            return;
        }

        if(TextUtils.isEmpty(codStr)){
            showToast("验证码不能为空");
            return;
        }
        if(TextUtils.isEmpty(passwordStr)){
            showToast("密码不能为空");
            return;
        }

        if(TextUtils.isEmpty(password1Str)){
            showToast("请选择身份");
            return;
        }

        if(!passwordStr.equals(password1Str)){
            showToast("输入的两次密码不一致请重新输入");
        }
        showToast(getString(R.string.change_password));
    }

    // 计时
    public void timer(){
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
