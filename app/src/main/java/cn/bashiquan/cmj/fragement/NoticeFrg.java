package cn.bashiquan.cmj.fragement;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import cn.bashiquan.cmj.R;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.home.adapter.NoticeAdapter;
import cn.bashiquan.cmj.base.BaseFrg;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mocf on 2018/9/26.
 * 公告页面
 */
public class NoticeFrg extends BaseFrg implements AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener {
    private String TAG = NoticeFrg.class.getSimpleName();
    private View contentView;
    private NoticeAdapter adapter;
    private RefreshListView lv_listview;
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
        setTitle("公告");
        datas = new ArrayList<String>();
        lv_listview = (RefreshListView) contentView.findViewById(R.id.lv_listview);
        lv_listview.setOnItemClickListener(this);
        lv_listview.setOnRefreshListener(this);
        initData();
    }

    private void initData() {
        for(int i = 0; i < 20; i++){
            datas.add(i + "");
        }
        setAdapter();
    }

    private void setAdapter() {
        if(adapter == null){
            adapter = new NoticeAdapter(mContext,datas);
            lv_listview.setAdapter(adapter);
        }else{
            adapter.setData(datas);
        }
        lv_listview.onRefreshComplete(true);
    }

    @Override
    public void onRefresh() {
        new TextView(mContext).postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();

            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        new TextView(mContext).postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 1000);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        showToat("详情" + position);
    }


}
