package cn.bashiquan.cmj.My.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/10/15.
 * 个人积分
 */

public class MyIntegralAct extends BaseAct{

    private TextView tv_num; // 积分
    private RelativeLayout rl_no_data;
    private RefreshListView listView;

    private int currentIndex = 0;

    @Override
    public int contentView() {
        return R.layout.activity_my_integral;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("个人积分");
        setTitleLeft(true,"");

        tv_num = (TextView) findViewById(R.id.tv_num);
        listView = (RefreshListView) findViewById(R.id.lv_listview);
        rl_no_data = (RelativeLayout)findViewById(R.id.rl_no_data);
        findViewById(R.id.tv_reflect).setOnClickListener(this);
        rl_no_data.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_reflect:
                // 体现

                break;
        }
    }
}
