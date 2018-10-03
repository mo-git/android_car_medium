package cn.bashiquan.cmj.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.base.BaseFrg;
import cn.bashiquan.cmj.home.activity.AddMediaAct;
import cn.bashiquan.cmj.home.activity.MediaListAct;
import cn.bashiquan.cmj.sdk.bean.BannersBean;
import cn.bashiquan.cmj.sdk.event.HomeEvent.BannerEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.LocationEvent;
import cn.bashiquan.cmj.utils.DialogListener;
import cn.bashiquan.cmj.utils.MyDialog;

/**
 * Created by mocf on 2018/9/26.
 * 首页
 */
public class HomePageFrg extends BaseFrg {
    private View contentView;
    private TextView tv_city_name;
    private TextView tv_top_msg;

    String cityName = "";
    @Override
    public int contentView() {
        return R.layout.frg_intentship;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentView = getContentView();
        setTitle("车媒介");
        tv_top_msg = (TextView) contentView.findViewById(R.id.tv_top_msg);
        tv_city_name = (TextView) contentView.findViewById(R.id.tv_city_name);
        initData();
        initListener();
    }

    public void initListener(){
        contentView.findViewById(R.id.rl_tab_1).setOnClickListener(this);
        contentView.findViewById(R.id.rl_tab_2).setOnClickListener(this);
        contentView.findViewById(R.id.rl_tab_3).setOnClickListener(this);
        contentView.findViewById(R.id.rl_tab_4).setOnClickListener(this);
        contentView.findViewById(R.id.iv_phone).setOnClickListener(this);
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    private void initData() {
        //获取轮播图
        getCoreService().getHomeManager("HomePageFrg").getBannerImages(BannersBean.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rl_tab_1:
                Intent intent = new Intent(getActivity(), MediaListAct.class);
                startActivity(intent);
                break;
            case R.id.rl_tab_2:
                showToat("积分商城");
                break;
            case R.id.rl_tab_3:
                showToat("用户注册");
                break;
            case R.id.rl_tab_4:
                Intent intentAdd = new Intent(getActivity(),AddMediaAct.class);
                startActivity(intentAdd);
                break;
            case R.id.iv_phone:
                MyDialog.showDialogDetal2(getActivity(), "400-012-0039", "", "呼叫", "取消", false, new DialogListener() {
                    @Override
                    public void onSelect() {
                        showToat("呼叫");
                    }

                    @Override
                    public void onCancle() {
                    }
                });
                break;
        }
    }

    // 获取banner成功
    public void onEventMainThread(BannerEvent event) {
        switch (event.getEventType()){
            case GET_BANNER_SUCCESS:
                showToat("加载数据成功");
                break;
            case GET_BANNER_FAILED:
                showToat(event.getMsg());
                break;

        }
    }

    // 定位成功
    public void onEventMainThread(LocationEvent event){
        if(tv_city_name != null){
            tv_city_name.setText(event.getCityName());
        }
    }

}
