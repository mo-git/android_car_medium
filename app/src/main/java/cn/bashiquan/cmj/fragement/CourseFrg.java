package cn.bashiquan.cmj.fragement;

import android.os.Bundle;
import android.view.View;

import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.base.BaseFrg;

/**
 * Created by mocf on 2017/7/11.
 * 资讯界面
 */
public class CourseFrg extends BaseFrg {
    private View contentView;

    @Override
    public int contentView() {
        return R.layout.frg_course;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentView = getContentView();
        setTitle("课程");
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }


}
