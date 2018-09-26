package cn.bashiquan.cmj.My.activity;

import android.os.Bundle;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;

/**
 * Created by mo on 2018/9/26.
 */

public class textAct extends BaseAct {
    @Override
    public int contentView() {
        return R.layout.activity_text;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("测试页面");
        setTitleLeft(true,"");
    }
}
