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
public class RegistAct extends BaseAct {

    private  CountDownTimer downTimer;
    private TextView tv_sendnum;
    private EditText phone;
    private EditText code;
    private EditText password;
    private EditText identity;
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
        password = (EditText)findViewById(R.id.regist_password);
        identity = (EditText)findViewById(R.id.regist_identity);
        tv_sendnum.setOnClickListener(this);
        findViewById(R.id.ll_regist).setOnClickListener(this);
        findViewById(R.id.btn_regist).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_regist:
            case R.id.btn_regist:
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
        String identityStr = identity.getText().toString().trim();
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

        if(TextUtils.isEmpty(identityStr)){
            showToast("请选择身份");
            return;
        }
        showToast("注册");
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
