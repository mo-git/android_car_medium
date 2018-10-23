package cn.bashiquan.cmj.My.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.My.adapter.DrawFrgAdapter;
import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.home.activity.AddPicAct;
import cn.bashiquan.cmj.home.adapter.TaskFrgItemAdapter;
import cn.bashiquan.cmj.sdk.bean.MyDrawListBean;
import cn.bashiquan.cmj.sdk.bean.TaskFrbListBean;
import cn.bashiquan.cmj.sdk.event.MyManager.DrawEvent;
import cn.bashiquan.cmj.sdk.event.TaskManagerEvent.TaskFrgListEvent;
import cn.bashiquan.cmj.sdk.service.CoreService;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.widget.ProgressHUD;
import cn.bashiquan.cmj.utils.widget.RefreshListView;
import de.greenrobot.event.EventBus;

/**
 * Created by mocf on 2018/9/26.
 * 任务列表的 fragment
 */
public class Draw_item_Frg extends Fragment implements AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener {
    private String TAG = Draw_item_Frg.class.getSimpleName();
    private DrawFrgAdapter adapter;
    private RefreshListView lv_listview;
    private RelativeLayout rl_no_data;
    private List<MyDrawListBean.DrawBean> datas = new ArrayList<>();
    private Context mContext;
    private  View rootView;
    private int currentIndex= 0;
    private ProgressHUD progressDialog;
    private int typeIndex = 0; // 0 全部 1 已中奖 2 未中奖
    private String typeName = "";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
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
        typeIndex = index;
        if(index == 0){
            typeName = "全部";
        }else if(index == 1){
            typeName = "已中奖";
        }else{
            typeName = "未中奖";
        }
    }


    public void initView(Bundle savedInstanceState) {
        lv_listview = (RefreshListView) rootView.findViewById(R.id.lv_listview);
        rl_no_data = (RelativeLayout)rootView.findViewById(R.id.rl_no_data);
        lv_listview.setOnItemClickListener(this);
        lv_listview.setOnRefreshListener(this);
        showProgressDialog(getActivity(),"",false);
        if(typeIndex == 0){
            initData();
        }
    }

    private void initData() {
        CoreService.getInstance().getMyManager("Draw_item_frg").getLuckList(10,currentIndex*10,typeName);
    }

    private void setAdapter() {
        if(CollectionUtils.isEmpty(datas)){
            lv_listview.setVisibility(View.GONE);
            rl_no_data.setVisibility(View.VISIBLE);
        }else{
            lv_listview.setVisibility(View.VISIBLE);
            rl_no_data.setVisibility(View.GONE);
        }
        if(adapter == null){
            adapter = new DrawFrgAdapter(mContext,datas);
            lv_listview.setAdapter(adapter);
        }else{
            adapter.setData(datas);
        }
        lv_listview.onRefreshComplete(true);
    }

    //  请求数据
    public void setData(){
        showProgressDialog(getActivity(),"",false);
        currentIndex = 0;
        CoreService.getInstance().getMyManager("Draw_item_frg").getLuckList(10,currentIndex * 10,typeName);
    }

    @Override
    public void onRefresh() {
        currentIndex = 1;
        initData();
    }

    @Override
    public void onLoadMore() {
       currentIndex ++;
        initData();
    }

    public void onEventMainThread(DrawEvent event){
        disProgressDialog();
        switch (event.getEvent()){
            case GET_DRAWLIST_SUCCESS:
               MyDrawListBean bean = event.getMyDrawListBean();
                if(bean != null && bean.getData() != null && bean.getData().getLuck() != null){
                    if(currentIndex == 0){
                        datas.clear();
                    }
                     datas.addAll(bean.getData().getLuck());
                    setAdapter();
                    if(bean.getData().getLuck().size() >= 10){
                        lv_listview.setPushEnable(true);
                    }else{
                        lv_listview.setPushEnable(false);
                    }
                }

                break;
            case GET_DRAWLIST_FAILED:
                lv_listview.onRefreshComplete(true);
                Toast.makeText(getActivity(), event.getMsg(),Toast.LENGTH_SHORT).show();
                break;
            case JOIN_DRAW_SUCCESS:
                currentIndex = 0;
                initData();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }


    @Override
    public void onDestroy() {
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();

    }

    /**
     * 显示等待加载框
     *
     * @param context
     * @param message
     * @param cancelable
     */
    public void showProgressDialog(Context context, CharSequence message, boolean cancelable) {
        if (progressDialog == null || (progressDialog != null && !progressDialog.isShowing())) {
            progressDialog = ProgressHUD.show(context, message, cancelable);
        }
    }

    /**
     * 取消等待框
     */
    public void disProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
