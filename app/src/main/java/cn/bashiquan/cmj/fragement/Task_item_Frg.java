package cn.bashiquan.cmj.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseFrg;
import cn.bashiquan.cmj.home.adapter.NoticeAdapter;
import cn.bashiquan.cmj.home.adapter.TaskFrgItemAdapter;
import cn.bashiquan.cmj.utils.widget.RefreshListView;
import de.greenrobot.event.EventBus;

/**
 * Created by mocf on 2018/9/26.
 * 任务列表的 fragment
 */
public class Task_item_Frg extends Fragment implements AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener {
    private String TAG = Task_item_Frg.class.getSimpleName();
    private TaskFrgItemAdapter adapter;
    private RefreshListView lv_listview;
    private RelativeLayout rl_no_data;
    private List<String> datas;
    private Context mContext;
    private  View rootView;
    private int currentIndex= 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
//        if(!EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().register(this);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null && (Boolean) rootView.getTag()) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.frg_item_task, container, false);
            rootView.setTag(true);
            initView(savedInstanceState);
            onDetach();
        }
        return rootView;
    }

    public void setCurrentIndex(int index){
        currentIndex = index;
    }

    public void initView(Bundle savedInstanceState) {
        datas = new ArrayList<String>();
        lv_listview = (RefreshListView) rootView.findViewById(R.id.lv_listview);
        rl_no_data = (RelativeLayout)rootView.findViewById(R.id.rl_no_data);
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
            adapter = new TaskFrgItemAdapter(mContext,datas);
            lv_listview.setAdapter(adapter);
        }else{
            adapter.setData(datas);
        }
        lv_listview.onRefreshComplete(true);
    }

    // 搜索 请求数据
    public void setData(String searchStr){
        Toast.makeText(mContext,"关键字_" + currentIndex + searchStr,Toast.LENGTH_SHORT).show();
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    @Override
    public void onDestroy() {
//        if(EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().unregister(this);
//        }
        super.onDestroy();

    }

}
