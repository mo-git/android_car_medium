package com.zhidian.app.fragement;

import android.os.Bundle;
import android.view.View;
import com.zhidian.app.R;
import com.zhidian.app.base.BaseFrg;

/**
 * Created by mocf on 2017/7/11.
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
        setTitle("我");
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }


}
