package cn.bashiquan.cmj.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import cn.bashiquan.cmj.My.activity.MyDrawAct;
import cn.bashiquan.cmj.My.activity.MyIntegralAct;
import cn.bashiquan.cmj.My.activity.MyOrderAct;
import cn.bashiquan.cmj.My.activity.MyTaxationAct;
import cn.bashiquan.cmj.MyApplication;
import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.base.BaseFrg;
import cn.bashiquan.cmj.sdk.bean.UserBean;
import cn.bashiquan.cmj.sdk.event.MyManager.VerifyEvent;
import cn.bashiquan.cmj.sdk.service.CoreService;
import cn.bashiquan.cmj.utils.FileUtils;
import cn.bashiquan.cmj.utils.ImageUtils;
import cn.bashiquan.cmj.utils.SysConstants;
import cn.bashiquan.cmj.utils.Utils;

/**
 * Created by mocf on 2018/9/26
 * 我的页面
 */
public class MyFrg extends BaseFrg {
    private View contentView;
    private String classNmae = MyFrg.class.getName();
    private boolean isActivity;
    private LinearLayout ll_my_vaild;
    private TextView ver_msg;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_cone;
    private TextView tv_send;

    private ImageView my_head;
    private RelativeLayout rl_valid;
    private TextView tv_mobile;
    private TextView tv_valid;
    private TextView tv_user_name;
    private ImageView iv_valid;


    // 倒计时的初始化时间为60秒
    private int timeCountDown = 60;
    /**
     * 自定义倒计时任务对象
     */
    private MyTimerTask task;
    /**
     * timer对象，执行倒计时
     */
    private Timer timer;
    private UserBean userBean;

    @Override
    public int contentView() {
        return R.layout.frg_my;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentView = getContentView();
        setTitle("个人中心");
        my_head = (ImageView) contentView.findViewById(R.id.my_head);
        iv_valid = (ImageView) contentView.findViewById(R.id.iv_valid);
        tv_mobile = (TextView) contentView.findViewById(R.id.tv_mobile);
        tv_valid = (TextView) contentView.findViewById(R.id.tv_valid);
        tv_user_name = (TextView) contentView.findViewById(R.id.tv_user_name);

        rl_valid = (RelativeLayout) contentView.findViewById(R.id.rl_valid);

        ll_my_vaild = (LinearLayout) contentView.findViewById(R.id.ll_my_vaild);
        ver_msg = (TextView) contentView.findViewById(R.id.ver_msg);
        et_name = (EditText) contentView.findViewById(R.id.et_name);
        et_phone = (EditText) contentView.findViewById(R.id.et_phone);
        et_cone = (EditText) contentView.findViewById(R.id.et_cone);
        tv_send = (TextView) contentView.findViewById(R.id.tv_send);
        ll_my_vaild.getBackground().setAlpha(80);

        initListener();
    }

    public void initListener(){
        contentView.findViewById(R.id.cancel_tv).setOnClickListener(this);
        contentView.findViewById(R.id.ll_my_vaild).setOnClickListener(null);
        contentView.findViewById(R.id.confirm_tv).setOnClickListener(this);
        contentView.findViewById(R.id.tv_send).setOnClickListener(this);
        contentView.findViewById(R.id.rl_my_tuijian).setOnClickListener(this);
        contentView.findViewById(R.id.rl_my_lishi).setOnClickListener(this);
        contentView.findViewById(R.id.rl_my_jnjo).setOnClickListener(this);
        contentView.findViewById(R.id.rl_my_person).setOnClickListener(this);
        contentView.findViewById(R.id.rl_my_mine).setOnClickListener(this);
        contentView.findViewById(R.id.rl_my_customer).setOnClickListener(this);
        contentView.findViewById(R.id.rl_my_car).setOnClickListener(this);
        contentView.findViewById(R.id.rl_my_clean).setOnClickListener(this);
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String imageUri = "";
        userBean = MyApplication.userBean;
        if(userBean != null){
            et_phone.setText(userBean.getData().getMobile());
            imageUri = userBean.getData().getImage();
            rl_valid.setVisibility(View.VISIBLE);
            tv_user_name.setText(userBean.getData().getRealname());
            tv_mobile.setText(userBean.getData().getMobile());
            tv_user_name.setTextColor(getResources().getColor(R.color.white));
            if(userBean.getData().getIs_mobile_valid() == 0){
                iv_valid.setVisibility(View.GONE);
                tv_valid.setText("等待验证");
                tv_valid.setOnClickListener(this);
            }else{
                iv_valid.setVisibility(View.VISIBLE);
                tv_valid.setOnClickListener(null);
                tv_valid.setText("已验证");
            }
        }else{
            imageUri = "drawable://" + R.drawable.person;
            tv_user_name.setText("游客");
            rl_valid.setVisibility(View.GONE);
        }
        rl_valid.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(imageUri,my_head, ImageUtils.loadRoundImagePic(0,360));
    }

    @Override
    public void onResume() {
        super.onResume();
        isActivity = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isActivity = false;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.cancel_tv:
                restoreView();
                break;
            case R.id.confirm_tv:
                if(TextUtils.isEmpty(et_name.getText().toString().trim())){
                    showToat("请输入姓名");
                }else if(TextUtils.isEmpty(et_phone.getText().toString().trim())){
                    showToat("请输入手机号");
                }else if(!Utils.isCellphone(et_phone.getText().toString().trim())){
                    showToat("请输入正确的手机号");
                }else if(TextUtils.isEmpty(et_cone.getText().toString().trim())){
                    showToat("请输入验证码");
                }else{
                    CoreService.getInstance().getMyManager(classNmae).VerifyUser(et_name.getText().toString().trim(),
                                    et_phone.getText().toString().trim(),
                                    et_cone.getText().toString().trim());
                }
                break;
            case R.id.tv_send:
                if(TextUtils.isEmpty(et_name.getText().toString().trim())){
                    showToat("请输入姓名");
                }else if(TextUtils.isEmpty(et_phone.getText().toString().trim())){
                    showToat("请输入手机号");
                }else if(!Utils.isCellphone(et_phone.getText().toString().trim())){
                    showToat("请输入正确的手机号");
                }else{
                    showProgressDialog(getActivity(),"",false);
                    CoreService.getInstance().getMyManager(classNmae).getVerifyCode(et_phone.getText().toString().trim());
                }

                break;
            case R.id.tv_valid:
                ll_my_vaild.setVisibility(View.VISIBLE);
                break;

            case R.id.rl_my_tuijian:
                // 推荐
                break;
            case R.id.rl_my_lishi:
                // 历史
                break;
            case R.id.rl_my_jnjo:
                // 抽奖
                Intent drawIntent = new Intent(getActivity(), MyDrawAct.class);
                startActivity(drawIntent);
                break;
            case R.id.rl_my_person:
                //个人积分
                Intent integralIntent = new Intent(getActivity(), MyIntegralAct.class);
                startActivity(integralIntent);
                break;
            case R.id.rl_my_mine:
                // 我的订单
                Intent orderIntent = new Intent(getActivity(), MyOrderAct.class);
                startActivity(orderIntent);
                break;
            case R.id.rl_my_customer:
                // 客户保单
                Intent taxationIntent = new Intent(getActivity(), MyTaxationAct.class);
                startActivity(taxationIntent);
                break;
            case R.id.rl_my_car:
                //移车二维码
                break;
            case R.id.rl_my_clean:
                // 清理缓存
                FileUtils.deleteFile(new File(SysConstants.DATA_DIR_ROOT));
                showToat("清除成功");
                break;
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

    public void onEventMainThread(VerifyEvent event) {
        if (isActivity) {
            disProgressDialog();
            switch (event.getEvent()) {
                case GET_VERIFY_SUCCESS:
                    startTimeCountDown();
                    ver_msg.setVisibility(View.VISIBLE);
                    showToat(event.getMsg());
                    break;
                case GET_VERIFY_FAILED:
                    showToat(event.getMsg());
                    break;
                case VERIFY_USER_SUCCESS:
                    restoreView();
                    showToat(event.getMsg());
                    if (MyApplication.userBean != null) {
                        MyApplication.userBean.getData().setIs_mobile_valid(1);
                        getCoreService().getLoginManager("MyFrg").getUserInfo();
                        iv_valid.setVisibility(View.VISIBLE);
                        tv_valid.setOnClickListener(null);
                        tv_valid.setText("已验证");

                    }
                case VERIFY_USER_FAILED:
                    showToat(event.getMsg());
                    break;
            }
        }

    }

    /****************************计时器***************************/

    /**
     * 开始倒计时
     */
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

            getActivity().runOnUiThread(new Runnable() {
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
