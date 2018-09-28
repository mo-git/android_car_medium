package cn.bashiquan.cmj;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.fragement.NoticeFrg;
import cn.bashiquan.cmj.fragement.TaskFrg;
import cn.bashiquan.cmj.fragement.HomePageFrg;
import cn.bashiquan.cmj.fragement.MyFrg;
import cn.bashiquan.cmj.sdk.event.HomeEvent.LocationEvent;
import cn.bashiquan.cmj.utils.widget.MyFragmentTabHost;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseAct implements TencentLocationListener {
    private TencentLocationManager locationManager;
    private TencentLocationRequest locationRequest;

    //定义FragmentTabHost对象
    private MyFragmentTabHost mTabHost;
    // 当前fragment的index
    private int currentTabIndex;

    //定义一个布局
    private LayoutInflater layoutInflater;
    private List<String> mTabList;
    public static final String[] EnterList = {"首页", "公告", "任务", "我的"};
    private int[] drables = {cn.bashiquan.cmj.R.drawable.tab_consult_btn_blue, cn.bashiquan.cmj.R.drawable.tab_internship_btn_blue,
            cn.bashiquan.cmj.R.drawable.tab_course_btn_blue, cn.bashiquan.cmj.R.drawable.tab_my_btn_blue};
    //定义数组来存放Fragment界面
    public static final Class[] fragments = {
            HomePageFrg.class,
            NoticeFrg.class,
            TaskFrg.class,
            MyFrg.class,

    };

    @Override
    public int contentView() {
        return cn.bashiquan.cmj.R.layout.activity_main;
    }

    @Override
    public boolean titleBarVisible() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentTabIndex = getIntent().getIntExtra("index", 0);
        mTabList = new ArrayList<>();
        mTabList.addAll(Arrays.asList(EnterList));
       initView();
        initTecentLoaction();
    }

    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);
        //实例化TabHost对象，得到TabHost
        mTabHost = (MyFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), cn.bashiquan.cmj.R.id.realtabcontent);
        setTabHost(currentTabIndex);
    }

    private void setTabHost(int index) {

        //得到fragment的个数
        int count = mTabList.size();

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTabList.get(i)).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragments[i], null);
            mTabHost.getTabWidget().setDividerDrawable(null);
        }
        View tabW = mTabHost.getTabWidget().getChildAt(index);
        currentTabIndex = index;
        mTabHost.setCurrentTab(index);
        setTabTextColor(tabW);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabChanged(String tabId) {
                currentTabIndex = mTabHost.getCurrentTab();
                System.out.println("tabId:" + tabId + mTabHost.getCurrentTabTag());

                for (int i = 0; i < mTabList.size(); i++) {
                    //为每一个Tab按钮设置图标、文字和内容
                    //设置Tab按钮的背景
                    if (tabId.equals(mTabList.get(i))) {
                        View tabW = mTabHost.getTabWidget().getChildAt(i);
                        setTabTextColor(tabW);
                    } else {
                        View tabW = mTabHost.getTabWidget().getChildAt(i);
                        ((TextView) tabW.findViewById(cn.bashiquan.cmj.R.id.tv_tab_title)).setTextColor(getResources().getColor(R.color.gray));
                    }
                }

            }
        });
    }
    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(cn.bashiquan.cmj.R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(cn.bashiquan.cmj.R.id.iv_tab_img);

        imageView.setImageResource(drables[index]);

        TextView textView = (TextView) view.findViewById(cn.bashiquan.cmj.R.id.tv_tab_title);
        textView.setText(mTabList.get(index));

        ImageView tab_item_isnew = (ImageView) view.findViewById(cn.bashiquan.cmj.R.id.tab_item_isnew);
        tab_item_isnew.setVisibility(View.GONE);
        return view;
    }


    private void setTabTextColor(View tabW) {
            ((TextView) tabW.findViewById(cn.bashiquan.cmj.R.id.tv_tab_title)).setTextColor(getResources().getColor(R.color.text_blue));
    }

    /*******************定位***********************/
    private void initTecentLoaction() {

        locationManager = TencentLocationManager.getInstance(this);
        locationRequest = TencentLocationRequest.create();
        locationRequest.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
        locationManager.requestLocationUpdates(locationRequest, this);
    }


    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int arg1, String arg2) {
        if (arg1 == TencentLocation.ERROR_OK) {
            if (tencentLocation == null || TextUtils.isEmpty(tencentLocation.getCity())) {
                return;
            }
            locationManager.removeUpdates(this);
            String cityName = tencentLocation.getCity();
            EventBus.getDefault().post(new LocationEvent(cityName));
        }

    }


    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

}
