package cn.bashiquan.cmj.fragement;

import android.os.Bundle;
import android.view.View;

import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.base.BaseFrg;

/**
 * Created by mocf on 2018/9/26
 * 资讯界面
 */
public class MyFrg extends BaseFrg {
    private View contentView;

    @Override
    public int contentView() {
        return R.layout.frg_my;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentView = getContentView();
        setTitle("个人中心");
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }


}
