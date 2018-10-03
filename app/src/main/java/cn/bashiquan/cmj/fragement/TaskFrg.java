package cn.bashiquan.cmj.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.base.BaseFrg;
import cn.bashiquan.cmj.utils.widget.MyViewPager;

/**
 * Created by mocf on 2018/9/26.
 * 任务界面
 */
public class TaskFrg extends BaseFrg implements ViewPager.OnPageChangeListener {
    private View contentView;
    private TextView tv_upload;
    private TextView tv_upload_line;
    private TextView tv_audit;
    private TextView tv_audit_line;
    private TextView tv_finish;
    private TextView tv_finish_line;
    private TextView tv_time_out;
    private TextView tv_time_out_line;
    private TextView tv_all;
    private TextView tv_all_line;

    private EditText et_search;

    private MyViewPager viewPager;

    private int currentIndex = 0;// 0 需上传 1 待审核 2 已完成 3 已过期 4 全部
    private String searchStr = ""; // 搜索关键字

    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public int contentView() {
        return R.layout.frg_course;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentView = getContentView();
        setTitle("任务");

        tv_upload_line = (TextView) contentView.findViewById(R.id.tv_upload_line);
        tv_audit_line = (TextView) contentView.findViewById(R.id.tv_audit_line);
        tv_finish_line = (TextView) contentView.findViewById(R.id.tv_finish_line);
        tv_time_out_line = (TextView) contentView.findViewById(R.id.tv_time_out_line);
        tv_all_line = (TextView) contentView.findViewById(R.id.tv_all_line);
        et_search = (EditText) contentView.findViewById(R.id.et_search);
        viewPager = (MyViewPager) contentView.findViewById(R.id.view_pager);

        currentIndex = getActivity().getIntent().getIntExtra("currentIndex",0);
        initListener();
        initViewPager();
    }



    @Override
    public boolean titleBarVisible() {
        return true;
    }
    private void initListener() {
        contentView.findViewById(R.id.tv_upload).setOnClickListener(this);
        contentView.findViewById(R.id.tv_audit).setOnClickListener(this);
        contentView.findViewById(R.id.tv_finish).setOnClickListener(this);
        contentView.findViewById(R.id.tv_time_out).setOnClickListener(this);
        contentView.findViewById(R.id.tv_all).setOnClickListener(this);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                searchStr = editable.toString();
                ((Task_item_Frg)mFragmentList.get(currentIndex)).setData(searchStr);
            }
        });
    }

    // 初始化viewpager
    private void initViewPager() {
        viewPager.setIsScroll(false);
        for(int i = 0; i < 5; i++){
            Task_item_Frg fragement = new Task_item_Frg();
            fragement.setCurrentIndex(i);
            mFragmentList.add(fragement);
        }

        MyAdapter mAdapter = new MyAdapter(getChildFragmentManager(), mFragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(currentIndex);
        viewPager.setOnPageChangeListener(this);
        changeView(currentIndex);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_upload:
                //需上传
                if(currentIndex != 0){
                    currentIndex = 0;
                    viewPager.setCurrentItem(currentIndex);
                }

                break;
            case R.id.tv_audit:
                //待审核
                if(currentIndex != 1){
                    currentIndex = 1;
                    viewPager.setCurrentItem(currentIndex);
                }

                break;
            case R.id.tv_finish:
                // 已完成
                if(currentIndex != 2){
                    currentIndex = 2;
                    viewPager.setCurrentItem(currentIndex);
                }

                break;
            case R.id.tv_time_out:
                // 已过期
                if(currentIndex != 3){
                    currentIndex = 3;
                    viewPager.setCurrentItem(currentIndex);
                }

                break;
            case R.id.tv_all:
                // 全部
                if(currentIndex != 4){
                    currentIndex = 4;
                    viewPager.setCurrentItem(currentIndex);
                }

                break;
        }
    }

    // view的改变
    public void changeView(int index){
        switch (index){
            case 0:
                tv_upload_line.setVisibility(View.VISIBLE);
                tv_audit_line.setVisibility(View.GONE);
                tv_finish_line.setVisibility(View.GONE);
                tv_time_out_line.setVisibility(View.GONE);
                tv_all_line.setVisibility(View.GONE);
                break;
            case 1:
                tv_upload_line.setVisibility(View.GONE);
                tv_audit_line.setVisibility(View.VISIBLE);
                tv_finish_line.setVisibility(View.GONE);
                tv_time_out_line.setVisibility(View.GONE);
                tv_all_line.setVisibility(View.GONE);
                break;
            case 2:
                tv_upload_line.setVisibility(View.GONE);
                tv_audit_line.setVisibility(View.GONE);
                tv_finish_line.setVisibility(View.VISIBLE);
                tv_time_out_line.setVisibility(View.GONE);
                tv_all_line.setVisibility(View.GONE);
                break;
            case 3:
                tv_upload_line.setVisibility(View.GONE);
                tv_audit_line.setVisibility(View.GONE);
                tv_finish_line.setVisibility(View.GONE);
                tv_time_out_line.setVisibility(View.VISIBLE);
                tv_all_line.setVisibility(View.GONE);
                break;
            case 4:
                tv_upload_line.setVisibility(View.GONE);
                tv_audit_line.setVisibility(View.GONE);
                tv_finish_line.setVisibility(View.GONE);
                tv_time_out_line.setVisibility(View.GONE);
                tv_all_line.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((Task_item_Frg)mFragmentList.get(currentIndex)).setData(searchStr);
        changeView(position);

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
}
