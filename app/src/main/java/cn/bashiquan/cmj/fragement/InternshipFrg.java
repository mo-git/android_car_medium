package cn.bashiquan.cmj.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.base.BaseFrg;
import cn.bashiquan.cmj.sdk.bean.BannersBean;
import cn.bashiquan.cmj.sdk.event.HomeEvent.BannerEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.LocationEvent;

/**
 * Created by mocf on 2018/9/26.
 * 首页
 */
public class InternshipFrg extends BaseFrg {
    private View contentView;

    String cityName = "";
    @Override
    public int contentView() {
        return R.layout.frg_intentship;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentView = getContentView();
        setTitle("车媒介");
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    private void initData() {
        getCoreService().getHomeManager("InternshipFrg").getBannerImages(BannersBean.class);
    }

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

    public void onEventMainThread(LocationEvent event){
        showToat(event.cityName);
    }

}
