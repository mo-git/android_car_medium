package com.zhidian.app.fragement;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.zhidian.app.R;
import com.zhidian.app.base.BaseFrg;

import java.util.ArrayList;
import java.util.List;

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
