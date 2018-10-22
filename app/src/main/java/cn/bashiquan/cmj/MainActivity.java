package cn.bashiquan.cmj;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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
import cn.bashiquan.cmj.home.activity.RegistAct;
import cn.bashiquan.cmj.sdk.bean.WXTokenBean;
import cn.bashiquan.cmj.sdk.bean.WXUserBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.WXEvent;
import cn.bashiquan.cmj.sdk.event.login.LoginEvent;
import cn.bashiquan.cmj.sdk.event.login.UnauthenticatedEvent;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.http.RequestCallback;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.sdk.utils.SPUtils;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.MyFragmentTabHost;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseAct {


    //定义FragmentTabHost对象
    private MyFragmentTabHost mTabHost;
    // 当前fragment的index
    private int currentTabIndex;
    public boolean isBunndle = false;
    public boolean isShowDialog = false;

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
        isBunndle = getIntent().getBooleanExtra("isBunndle",false);
        isShowDialog = getIntent().getBooleanExtra("isShowDialog",false);
        mTabList = new ArrayList<>();
        mTabList.addAll(Arrays.asList(EnterList));
       initView();
        initTecentLoaction();
        if(!TextUtils.isEmpty((String)SPUtils.get(this,Constants.SP_LOGINTOKEN,""))){
           getCoreService().getLoginManager("Main").refreshTodek();
        }
        if(isShowDialog){
            if(MyApplication.wxTokenBean != null){
                getCoreService().getLoginManager("BaseAct").login((String)SPUtils.get(mContext,Constants.SP_WXUNIONID,""));
            }else{
                showDialog();
            }
        }
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

    public void onEventMainThread(WXEvent event){
        switch (event.getEventType()){
            case GET_WX_TOKEN_SUCCESS:
                WXTokenBean wxTokenBean = event.getwTokenBean();
                MyApplication.wxTokenBean = wxTokenBean;
                showProgressDialog(mContext,"",false);
                SPUtils.put(mContext,Constants.SP_WXTOKEN,wxTokenBean.getAccess_token());
                SPUtils.put(mContext,Constants.SP_WXUNIONID,wxTokenBean.getUnionid());
                getCoreService().getLoginManager("MainAct").getWxUserMesg(wxTokenBean.getAccess_token(), wxTokenBean.getOpenid());

                break;
            case GET_WX_TOKEN_FAILED:
                showToast(event.getMsg());
                break;
            case GET_WX_USER_SUCCESS:
                WXUserBean wxUserBean = event.getwXUserBean();
                MyApplication.wxUserBean = wxUserBean;
                getCoreService().getLoginManager("MainAct").login(MyApplication.wxTokenBean.getUnionid());
                break;
        }
    }

    public void onEventMainThread(LoginEvent event){
        disProgressDialog();
        switch (event.getEvent()){
            case LOGIN_SUCCESS:
               if(event.getLogin() != null){
                   SPUtils.put(mContext,Constants.SP_LOGINTOKEN,"cmj_session=" + event.getLogin().getData().getToken());
                   getCoreService().getLoginManager("Main").getUserInfo();
               }
                break;
            case REFRSH_TOKEN_SUCCESS:
                getCoreService().getLoginManager("Main").getUserInfo();
                break;
            case GET_USERINFO_SUCCESS:
                MyApplication.userBean = event.getUserBean();
                break;

        }
    }


    public void onEventMainThread(UnauthenticatedEvent event){
        disProgressDialog();
        switch (event.getEventType()){
            case LOIGN_SUCCESS:
                if(MyApplication.wxTokenBean != null){
                    Intent intentRegist = new Intent(this, RegistAct.class);
                    startActivity(intentRegist);
                }else{
                    showDialog();
                }
                break;
            case LOGIN_NO_SUCCESS:
                SPUtils.put(mContext,Constants.SP_LOGINTOKEN,"");
                if(currentTabIndex == 0 && isMainActivity()){
                    if(MyApplication.wxTokenBean != null){
                        getCoreService().getLoginManager("BaseAct").login((String)SPUtils.get(mContext,Constants.SP_WXUNIONID,""));
                    }
                }else{
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("isShowDialog",true);
                    startActivity(intent);
                    EventBus.getDefault().post(new AddPicCloseEvent(1));
                    finish();
            }

//                }
                break;
        }
    }

    public boolean isMainActivity(){
        boolean flag = false;
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);

        // 类名 .ui.mobile.activity.WebsiteLoginActivity
        String shortClassName = info.topActivity.getShortClassName();
        if(shortClassName.contains("MainActivity")){
            flag = true;
        }
        return flag;
    }
}
