package cn.bashiquan.cmj.My.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bashiquan.cmj.My.adapter.IntegralWithAdapter;
import cn.bashiquan.cmj.MyApplication;
import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.fragement.MyFrg;
import cn.bashiquan.cmj.sdk.bean.IntegralListBean;
import cn.bashiquan.cmj.sdk.bean.IntegralWithdrawBean;
import cn.bashiquan.cmj.sdk.bean.UserBean;
import cn.bashiquan.cmj.sdk.event.MyManager.IntegralEvent;
import cn.bashiquan.cmj.sdk.event.MyManager.VerifyEvent;
import cn.bashiquan.cmj.sdk.event.login.LoginEvent;
import cn.bashiquan.cmj.sdk.service.CoreService;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.sdk.utils.SPUtils;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/10/15.
 * 提现页面
 */

public class MyIntegealWithdrawAct extends BaseAct {
    private String className = MyIntegealWithdrawAct.class.getName();

    private boolean isActivity = true;
    private LinearLayout ll_my_vaild;
    private TextView ver_msg;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_cone;
    private TextView tv_send;

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

        ll_my_vaild = (LinearLayout) findViewById(R.id.ll_my_vaild);
        ver_msg = (TextView) findViewById(R.id.ver_msg);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_cone = (EditText) findViewById(R.id.et_cone);
        tv_send = (TextView) findViewById(R.id.tv_send);
        ll_my_vaild.getBackground().setAlpha(80);
        lv_listview.setPullEnable(false);
        initListener();
        iniData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivity = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivity = false;
    }

    private void initListener() {
        ll_my_vaild.setOnClickListener(null);
        findViewById(R.id.cancel_tv).setOnClickListener(this);
        findViewById(R.id.confirm_tv).setOnClickListener(this);
        findViewById(R.id.tv_send).setOnClickListener(this);
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
                getMoney();
                break;
            case R.id.cancel_tv:
                restoreView();
                break;
            case R.id.confirm_tv:
                showProgressDialog(this,"",false);
                if(TextUtils.isEmpty(et_name.getText().toString().trim())){
                    showToast("请输入姓名");
                }else if(TextUtils.isEmpty(et_phone.getText().toString().trim())){
                    showToast("请输入手机号");
                }else if(!Utils.isCellphone(et_phone.getText().toString().trim())){
                    showToast("请输入正确的手机号");
                }else if(TextUtils.isEmpty(et_cone.getText().toString().trim())){
                    showToast("请输入验证码");
                }else{
                    CoreService.getInstance().getMyManager(className).VerifyUser(et_name.getText().toString().trim(),
                            et_phone.getText().toString().trim(),
                            et_cone.getText().toString().trim());
                }
                break;
            case R.id.tv_send:
                if(TextUtils.isEmpty(et_name.getText().toString().trim())){
                    showToast("请输入姓名");
                }else if(TextUtils.isEmpty(et_phone.getText().toString().trim())){
                    showToast("请输入手机号");
                }else if(!Utils.isCellphone(et_phone.getText().toString().trim())){
                    showToast("请输入正确的手机号");
                }else{
                    showProgressDialog(this,"",false);
                    CoreService.getInstance().getMyManager(className).getVerifyCode(et_phone.getText().toString().trim());
                }
                break;
            case R.id.tv_valid:
                ll_my_vaild.setVisibility(View.VISIBLE);
                break;

        }
    }

    // 提现的逻辑验证
    public void getMoney(){
        UserBean userBean = MyApplication.userBean;
        if(userBean != null){
            if(userBean.getData().getIs_mobile_valid() == 0){
                ll_my_vaild.setVisibility(View.VISIBLE);
            }else{
                //验证之后
                String strPoint = et_num.getText().toString().trim();
                if(TextUtils.isEmpty(strPoint)){
                    showToast("请输入兑换的积分!");
                }else{
                    if(point >= limit){
                        if(Double.valueOf(strPoint).doubleValue() > point){
                            showToast("积分不足!");
                        }else{
                            getCoreService().getMyManager(className).withdrawMoney(strPoint);
                        }
                    }else{
                        if(Double.valueOf(strPoint).doubleValue() < limit){
                            showToast("兑换积分不能少于" + limit);
                            et_num.setText("");
                        }else if(Double.valueOf(strPoint).doubleValue() > point){
                            showToast("积分不足!");
                        }else{
                            showProgressDialog(this,"",false);
                            getCoreService().getMyManager(className).withdrawMoney(strPoint);
                        }
                    }

                }
            }
        }else{
            showProgressDialog(this,"",false);
            getCoreService().getLoginManager(className).getUserInfo();
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

    public void onEventMainThread(VerifyEvent event){
        disProgressDialog();
        switch (event.getEvent()){
            case GET_VERIFY_SUCCESS:
                startTimeCountDown();
                ver_msg.setVisibility(View.VISIBLE);
                showToast(event.getMsg());
                break;
            case GET_VERIFY_FAILED:
                showToast(event.getMsg());
                break;
            case VERIFY_USER_SUCCESS:
                restoreView();
                if(MyApplication.userBean != null){
                    MyApplication.userBean.getData().setIs_mobile_valid(1);
                }
                showToast(event.getMsg());
            case VERIFY_USER_FAILED:
                showToast(event.getMsg());
                break;
        }
    }

    public void onEventMainThread(LoginEvent event){
        disProgressDialog();
        if(isActivity){
            switch (event.getEvent()){
                case GET_USERINFO_SUCCESS:
                    MyApplication.userBean = event.getUserBean();
                    getMoney();
                    break;

            }
        }

    }


    // 还原验证布局
    public void restoreView(){
        ll_my_vaild.setVisibility(View.GONE);
        if (timer != null) {
            if (task != null) {
                task.cancel();
                task = null;
            }

            timer.cancel();
            timer = null;
        }
        tv_send.setEnabled(true);
        tv_send.setText("发送");
        et_name.setText("");
        et_phone.setText("");
        et_cone.setText("");
        timeCountDown = 60;
        ver_msg.setVisibility(View.GONE);
    }



    /****************************计时器***************************/

    // 倒计时的初始化时间为60秒
    private int timeCountDown = 60;
     //自定义倒计时任务对象
    private MyTimerTask task;
     //timer对象，执行倒计时
    private Timer timer;

     //开始倒计时
    private void startTimeCountDown(){
        // 启动计时器
        timer = new Timer();
        // 开启倒计时任务
        task = new MyTimerTask();
        // 1秒钟倒计时1次
        timer.schedule(task, 1000, 1000);
        // 让按钮的背景变灰
//        tv_get_verifycode.setBackground(getResources().getDrawable(R.drawable.d4d4d4_16radius_solid_corner));
        // 设置获取验证码按钮不可点击
        tv_send.setEnabled(false);
    }


    /**
     * 自定义倒计时任务执行类
     *
     * @author chen
     */
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // 倒计时秒数递减
                    timeCountDown--;

                    // 倒计时显示的形式 "倒计时58秒"
                    tv_send.setText(timeCountDown
                            + "秒");

                    // 如果倒计时小于等于0，则取消倒计时任务和timer，置空倒计时任务和timer
                    if (timeCountDown <= 0) {
                        if (timer != null) {
                            if (task != null) {
                                task.cancel();
                                task = null;
                            }

                            timer.cancel();
                            timer = null;
                        }

                        // 让发送验证码按钮可以点击
                        tv_send.setEnabled(true);
                        // 让获取验证码的按钮变黄
                        // 倒计时完毕，恢复"重新获取"字样
                        tv_send.setText("重新获取");

                        timeCountDown = 60;

                    }
                }
            });
        }
    };

}
