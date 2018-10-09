package cn.bashiquan.cmj;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.fragement.NoticeFrg;
import cn.bashiquan.cmj.fragement.TaskFrg;
import cn.bashiquan.cmj.fragement.HomePageFrg;
import cn.bashiquan.cmj.fragement.MyFrg;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.http.RequestCallback;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.sdk.utils.SPUtils;
import cn.bashiquan.cmj.utils.widget.MyFragmentTabHost;

public class MainActivity extends BaseAct {


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
        getToken();
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


    // 监测页提交成功
    public void onEventMainThread(AddPicCloseEvent event){
        if(event.getFlag() == 1){
            finish();
        }
    }

    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 3000) {
                showToast("再按一次退出程序");
                firstTime = secondTime;
            } else {
                finish();
                ActivityManager manager = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE); //获取应用程序管理器
                manager.killBackgroundProcesses(getPackageName()); //强制结束当前应用程序

            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 临时获取token
    public void getToken(){
        String url = "user/login1";
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject dataJson = jsonObject.getJSONObject("data");
                    String token = dataJson.getString("token");
                    String userId = dataJson.getString("user_id");
                    SPUtils.put(getApplicationContext(), Constants.SP_LOGINTOKEN,"cmj_session=" + token);
                    SPUtils.put(getApplicationContext(), Constants.SP_USER_ID,userId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });
    }


}
