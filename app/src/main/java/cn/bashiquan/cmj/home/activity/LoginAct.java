package cn.bashiquan.cmj.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.MainActivity;
import cn.bashiquan.cmj.base.BaseAct;

/**
 * Created by mocf on 2018/9/26.
 */
public class LoginAct extends BaseAct {

    private EditText username;
    private EditText password;

    @Override
    public int contentView() {
        return R.layout.activity_login;
    }

    @Override
    public boolean titleBarVisible() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = (EditText)findViewById(R.id.et_username);
        password = (EditText)findViewById(R.id.et_password);

        findViewById(R.id.ll_login).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.tv_regist).setOnClickListener(this);
        findViewById(R.id.tv_forget_password).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_login:
            case R.id.btn_login:
                startAct(MainActivity.class,null);
            break;
            case R.id.tv_regist:
                showToast("regist");
                startAct(RegistAct.class,null);
                break;
            case R.id.tv_forget_password:
                showToast("forget_password");
                startAct(ChangePasswordAct.class,null);
                break;
        }
    }
}
