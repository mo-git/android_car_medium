package cn.bashiquan.cmj.My.activity;

import android.os.Bundle;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;

/**
 * Created by mo on 2018/10/15.
 * 个人积分
 */

public class MyIntegralAct extends BaseAct{
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
    }
}
