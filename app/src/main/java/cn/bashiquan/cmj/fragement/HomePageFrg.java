package cn.bashiquan.cmj.fragement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.MainActivity;
import cn.bashiquan.cmj.MyApplication;
import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.base.BaseFrg;
import cn.bashiquan.cmj.home.activity.AddMediaAct;
import cn.bashiquan.cmj.home.activity.IntegralShopAct;
import cn.bashiquan.cmj.home.activity.MediaListAct;
import cn.bashiquan.cmj.home.activity.RegistAct;
import cn.bashiquan.cmj.sdk.bean.BannersBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.BannerEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.LocationEvent;
import cn.bashiquan.cmj.sdk.manager.HomeManager;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.sdk.utils.SPUtils;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.DialogListener;
import cn.bashiquan.cmj.utils.ImageUtils;
import cn.bashiquan.cmj.utils.MyDialog;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.picCarousel.AutoScrollViewPager;
import cn.bashiquan.cmj.utils.picCarousel.CirclePageIndicator;
import cn.bashiquan.cmj.utils.widget.RollTextView;

/**
 * Created by mocf on 2018/9/26.
 * 首页
 */
public class HomePageFrg extends BaseFrg {
    private String className = HomePageFrg.class.getName();
    private View contentView;
    private TextView tv_city_name;
    private RollTextView tv_top_msg;
    private AutoScrollViewPager pager;
    private CirclePageIndicator indicator;

    String cityName = "";
    @Override
    public int contentView() {
        return R.layout.frg_intentship;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentView = getContentView();
        setTitle("车媒介");
        tv_top_msg = (RollTextView) contentView.findViewById(R.id.tv_top_msg);
        tv_city_name = (TextView) contentView.findViewById(R.id.tv_city_name);
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



    @Override
    public void onClick(View v) {
        super.onClick(v);
        String loginToken = (String) SPUtils.get(mContext, Constants.SP_LOGINTOKEN,"");
            switch (v.getId()) {
                case R.id.rl_tab_1:
                    if(TextUtils.isEmpty(loginToken)){
                        if(MyApplication.wxTokenBean == null){
                            ((MainActivity)getActivity()).showDialog();
                        }else{
                            Intent intentResist = new Intent(getActivity(), RegistAct.class);
                            startActivity(intentResist);
                        }
                    }else{
                        Intent intent = new Intent(getActivity(), MediaListAct.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.rl_tab_2:
                    Intent intentShop = new Intent(getActivity(), IntegralShopAct.class);
                    startActivity(intentShop);
                    break;
                case R.id.rl_tab_3:
                    if(TextUtils.isEmpty(loginToken)){
                        if(MyApplication.wxTokenBean == null){
                            ((MainActivity)getActivity()).showDialog();
                        }else{
                            Intent intentResist = new Intent(getActivity(), RegistAct.class);
                            startActivity(intentResist);
                        }
                    }else{
                        showToat("已注册");
                    }
                    break;
                case R.id.rl_tab_4:
                    if(TextUtils.isEmpty(loginToken)){
                        if(MyApplication.wxTokenBean == null){
                            ((MainActivity)getActivity()).showDialog();
                        }else{
                            Intent intentResist = new Intent(getActivity(), RegistAct.class);
                            startActivity(intentResist);
                        }
                    }else{
                        Intent intentAdd = new Intent(getActivity(), AddMediaAct.class);
                        startActivity(intentAdd);
                    }
                    break;
                case R.id.iv_phone:
                    MyDialog.showDialogDetal2(getActivity(), "400-012-0039", "", "呼叫", "取消", false, new DialogListener() {
                        @Override
                        public void onSelect() {
                            Intent dialIntent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "400-012-0039"));
                            startActivity(dialIntent);
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
                setBanners(event.getBannersBean());
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
            getCoreService().getHomeManager("HomePageFrg").getBannerImages(BannersBean.class,event.getCityName());
        }
    }
    List<BannersBean.Data> banners;
    public void setBanners(final BannersBean bannersBean){
        banners = new ArrayList<>();
        if (bannersBean != null && !CollectionUtils.isEmpty(bannersBean.getData())) {
            banners = bannersBean.getData();
        }
        indicator = (CirclePageIndicator) contentView.findViewById(R.id.indicator);
        if (banners.size() == 1) {
            indicator.setVisibility(View.GONE);
        } else {
            indicator.setVisibility(View.VISIBLE);
        }

        pager = (AutoScrollViewPager) contentView.findViewById(R.id.scroll_pager);
//        int width = DeviceUtils.deviceWidth();
//        int height = width * 110 / 355;
//        pager.getLayoutParams().height = height;
        pager.setAdapter(new MyPagerAdapter());
        indicator.setViewPager(pager);
        indicator.setSnap(true);
        pager.startAutoScroll(2000);
//        pager.setOnPageClickListener(new AutoScrollViewPager.OnPageClickListener() {
//            @Override
//            public void onPageClick(AutoScrollViewPager autoScrollPager, int position) {
//                //图片点击事件
//                if (!TextUtils.isEmpty(banners.get(position).getOther_info().getUrl())) {
//                    // 判断是否注册
//                    if(TextUtils.isEmpty((String) SPUtils.get(mContext, Constants.SP_LOGINTOKEN,""))){
//                        if(MyApplication.wxTokenBean == null){
//                            ((MainActivity)getActivity()).showDialog();
//                        }else{
//                            Intent intentResist = new Intent(getActivity(), RegistAct.class);
//                            startActivity(intentResist);
//                        }
//                    }else{
//                        showToat(banners.get(position).getOther_info().getUrl());
//                    }
//
//                }
//            }
//        });
    }


    public class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return banners.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            String uri = Constants.IMAGE_URL + banners.get(position).getPics().get(0).getPath();
            ImageLoader.getInstance().displayImage(uri,ImageUtils.getImageViewAware(view), ImageUtils.loadImage(R.drawable.defal_image));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
