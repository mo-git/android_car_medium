package cn.bashiquan.cmj.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.Login;
import cn.bashiquan.cmj.sdk.event.BaseEvent;
import cn.bashiquan.cmj.sdk.event.login.LoginEvent;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.adapter.QuestionAdapter;
import cn.bashiquan.cmj.base.BaseFrg;
import cn.bashiquan.cmj.utils.widget.PullToRefreshView;

/**
 * Created by mocf on 2017/7/11.
 * 咨询界面
 */
public class ConsultFrg extends BaseFrg implements PullToRefreshView.OnPullToRefreshListener {
    private String TAG = ConsultFrg.class.getSimpleName();
    private View contentView;
    private ListView listView;
    private QuestionAdapter adapter;
    private PullToRefreshView pullView;
    private List<String> datas;

    @Override
    public int contentView() {
        return R.layout.frg_consult;
    }
    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        contentView = getContentView();
        setTitle("咨询");
        setTitleLeft(false,"免费/咨询");
        setTitleRight(false,0,"导师");
        datas = new ArrayList<String>();
        listView = (ListView)contentView.findViewById(R.id.consult_listview);
        pullView = (PullToRefreshView)contentView.findViewById(R.id.pull_view);
        pullView.setOnPullToRefreshListener(this);
        pullView.headerRefreshing();

//        initData();
    }

    private void initData() {
        for(int i = 0; i < 20; i++){
            datas.add(i + "");
        }
        setAdapter();
    }

    private void setAdapter() {
        if(adapter == null){
            adapter = new QuestionAdapter(mContext,datas);
            listView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickLeftText() {
        super.onClickLeftText();
//        showToat("免费/咨询");
        getCoreService().getLoginManager(TAG).login("dd","cc", Login.class);
    }

    @Override
    public void onClickRightText(View view) {
        super.onClickRightText(view);
        showToat("导师");
    }

    @Override
    public void onRefresh(PullToRefreshView view) {
        new TextView(mContext).postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                setAdapter();
                pullView.stopRefresh();
            }
        }, 1000);

    }

    @Override
    public void onLoad(PullToRefreshView view) {
        new TextView(mContext).postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                pullView.stopLoadMore();
            }
        }, 1000);
    }

//    public void onEventMainThread(LoginEvent event){
//        super
//
//
//    }


    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        LoginEvent loginEvent = (LoginEvent)event;
        switch (loginEvent.getEvent()){
            case LOGIN_SUCCESS:
                Login login = loginEvent.getLogin();
                String name = loginEvent.getName();
                String className = loginEvent.getClassName();
                String msg = loginEvent.getMsg();
                showToat("name = " + login.userName + "  className = " + login.passWord + "  msg = " + msg);
                break;
            case LOGIN_FAILD:
                break;
        }
    }
}
