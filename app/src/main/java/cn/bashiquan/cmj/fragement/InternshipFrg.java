package cn.bashiquan.cmj.fragement;

import android.os.Bundle;
import android.view.View;

import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.base.BaseFrg;

/**
 * Created by mocf on 2017/7/11.
 * 实习界面
 */
public class InternshipFrg extends BaseFrg {
    private View contentView;
    @Override
    public int contentView() {
        return R.layout.frg_intentship;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentView = getContentView();
        setTitle("实习");
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }
}
