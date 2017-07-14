package com.zhidian.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.zhidian.app.base.BaseAct;
import com.zhidian.app.fragement.ConsultFrg;
import com.zhidian.app.fragement.CourseFrg;
import com.zhidian.app.fragement.InternshipFrg;
import com.zhidian.app.fragement.MyFrg;
import com.zhidian.app.utils.SysConstants;
import com.zhidian.app.utils.widget.MyFragmentTabHost;

import java.util.*;

public class MainActivity extends BaseAct {


    //定义FragmentTabHost对象
    private MyFragmentTabHost mTabHost;
    // 当前fragment的index
    private int currentTabIndex;

    //定义一个布局
    private LayoutInflater layoutInflater;
    private Map<Integer, Boolean> tabHasNew = new HashMap<Integer, Boolean>();
    private List<String> mTabList;
    public static final String[] EnterList = {"咨询", "实习", "课程", "我"};
    private int[] drables = {R.drawable.tab_consult_btn_blue, R.drawable.tab_internship_btn_blue,
            R.drawable.tab_course_btn_blue, R.drawable.tab_my_btn_blue};
    //定义数组来存放Fragment界面
    public static final Class[] fragments = {
            ConsultFrg.class,
            InternshipFrg.class,
            CourseFrg.class,
            MyFrg.class,

    };

    @Override
    public int contentView() {
        return R.layout.activity_main;
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
    }

    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);
        //实例化TabHost对象，得到TabHost
        mTabHost = (MyFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
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
            //设置Tab按钮的背景
//			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
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
                        ((TextView) tabW.findViewById(R.id.tv_tab_title)).setTextColor(getResources().getColor(R.color.bottom_text_nomal));
                    }
                }

            }
        });
    }
    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab_img);

        imageView.setImageResource(drables[index]);

        TextView textView = (TextView) view.findViewById(R.id.tv_tab_title);
        textView.setText(mTabList.get(index));

        ImageView tab_item_isnew = (ImageView) view.findViewById(R.id.tab_item_isnew);
        tab_item_isnew.setVisibility(View.GONE);
        return view;
    }


    private void setTabTextColor(View tabW) {
            ((TextView) tabW.findViewById(R.id.tv_tab_title)).setTextColor(getResources().getColor(R.color.bottom_text_blue));
    }
}
