package com.zhidian.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.zhidian.app.R;
import com.zhidian.app.base.BaseAct;

/**
 * Created by mocf on 2017/7/12.
 * 导师列表界面
 */
public class TutorAct extends BaseAct{
    @Override
    public int contentView() {
        return R.layout.activity_tutor;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("导师");
        setTitleLeft(true,"");
        TextView textView = (TextView)findViewById(R.id.tv_dd);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.tv_dd){
            showToast("dddddd");

        }
    }
}
