package cn.bashiquan.cmj.My.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.TaskListEvent;
import cn.bashiquan.cmj.utils.widget.MyViewPager;

/**
 * Created by mo on 2018/10/15.
 * 我参与的抽奖
 */

public class MyDrawAct extends BaseAct implements ViewPager.OnPageChangeListener {
    private TextView tv_draw_y;
    private TextView tv_draw_y_line;
    private TextView tv_draw_n;
    private TextView tv_draw_n_line;
    private TextView tv_all;
    private TextView tv_all_line;

    private MyViewPager viewPager;
    private int currentIndex = 0;//0 全部 1 已中奖 2 未中奖
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public int contentView() {
        return R.layout.activity_my_draw;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我参与的抽奖");
        setTitleLeft(true, "");
        viewPager = (MyViewPager) findViewById(R.id.view_pager);
        tv_draw_y = (TextView) findViewById(R.id.tv_draw_y);
        tv_draw_y_line = (TextView) findViewById(R.id.tv_draw_y_line);
        tv_draw_n = (TextView) findViewById(R.id.tv_draw_n);
        tv_draw_n_line = (TextView) findViewById(R.id.tv_draw_n_line);
        tv_all = (TextView) findViewById(R.id.tv_all);
        tv_all_line = (TextView) findViewById(R.id.tv_all_line);
        initListener();
        initViewPager();

    }

    private void initViewPager() {
        viewPager.setIsScroll(false);
        for (int i = 0; i < 3; i++) {
            Draw_item_Frg fragement = new Draw_item_Frg();
            fragement.setCurrentIndex(i);
            mFragmentList.add(fragement);
        }

        MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(currentIndex);
        viewPager.setOnPageChangeListener(this);
        changeView(currentIndex);
    }

    private void initListener() {
        tv_draw_y.setOnClickListener(this);
        tv_draw_n.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        findViewById(R.id.tv_draw).setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((Draw_item_Frg) mFragmentList.get(currentIndex)).setData();
        changeView(position);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_all:
                if (currentIndex != 0) {
                    currentIndex = 0;
                    changeView(0);
                    viewPager.setCurrentItem(currentIndex);
                }

                break;
            case R.id.tv_draw_y:
                if (currentIndex != 1) {
                    currentIndex = 1;
                    changeView(1);
                    viewPager.setCurrentItem(currentIndex);
                }

                break;
            case R.id.tv_draw_n:
                if (currentIndex != 2) {
                    currentIndex = 2;
                    changeView(2);
                    viewPager.setCurrentItem(currentIndex);
                }

                break;
            case R.id.tv_draw:
                Intent intent = new Intent(this,MyJoinDrawAct.class);
                startActivity(intent);
                break;
        }
    }

    // view的改变
    public void changeView(int index) {
        switch (index) {
            case 0:
                tv_all.setTextColor(getResources().getColor(R.color.text_blue));
                tv_draw_y.setTextColor(getResources().getColor(R.color.deep_6));
                tv_draw_n.setTextColor(getResources().getColor(R.color.deep_6));
                tv_all_line.setVisibility(View.VISIBLE);
                tv_draw_y_line.setVisibility(View.GONE);
                tv_draw_n_line.setVisibility(View.GONE);

                break;
            case 1:
                tv_all.setTextColor(getResources().getColor(R.color.deep_6));
                tv_draw_y.setTextColor(getResources().getColor(R.color.text_blue));
                tv_draw_n.setTextColor(getResources().getColor(R.color.deep_6));
                tv_all_line.setVisibility(View.GONE);
                tv_draw_y_line.setVisibility(View.VISIBLE);
                tv_draw_n_line.setVisibility(View.GONE);

                break;
            case 2:
                tv_all.setTextColor(getResources().getColor(R.color.deep_6));
                tv_draw_y.setTextColor(getResources().getColor(R.color.deep_6));
                tv_draw_n.setTextColor(getResources().getColor(R.color.text_blue));
                tv_all_line.setVisibility(View.GONE);
                tv_draw_y_line.setVisibility(View.GONE);
                tv_draw_n_line.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }


    static class MyAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public MyAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }


    public void onEventMainThread(TaskListEvent event) {


    }
}
