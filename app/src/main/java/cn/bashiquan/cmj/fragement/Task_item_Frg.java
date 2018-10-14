package cn.bashiquan.cmj.fragement;

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

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.home.activity.AddPicAct;
import cn.bashiquan.cmj.home.adapter.TaskFrgItemAdapter;
import cn.bashiquan.cmj.sdk.bean.TaskFrbListBean;
import cn.bashiquan.cmj.sdk.event.TaskManagerEvent.TaskFrgListEvent;
import cn.bashiquan.cmj.sdk.service.CoreService;
import cn.bashiquan.cmj.utils.widget.ProgressHUD;
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
    private List<TaskFrbListBean.TaskFrgBean> datas = new ArrayList<>();
    private Context mContext;
    private  View rootView;
    private int currentIndex= 0;
    private int defdaltIndex =  0;
    private ProgressHUD progressDialog;
    private int typeIndex = 0; // 0 需上传  ，1 待审核 ，2 已完成， 3 已过期 ， 4 全部



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

    public void setCurrentIndex(int index,int defdaltIndex){
        typeIndex = index;
        this.defdaltIndex = defdaltIndex;
    }

    public void initView(Bundle savedInstanceState) {
        lv_listview = (RefreshListView) rootView.findViewById(R.id.lv_listview);
        rl_no_data = (RelativeLayout)rootView.findViewById(R.id.rl_no_data);
        lv_listview.setOnItemClickListener(this);
        lv_listview.setOnRefreshListener(this);
        showProgressDialog(getActivity(),"",false);
        if(typeIndex == defdaltIndex){
            initData();
        }

    }

    private void initData() {
        CoreService.getInstance().getTaskManager(TAG).getTaskList(typeIndex,10,currentIndex * 10);
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
        showProgressDialog(getActivity(),"",false);
        CoreService.getInstance().getTaskManager(TAG).getTaskList(typeIndex,10,currentIndex * 10);
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

    public void onEventMainThread(TaskFrgListEvent event){
        disProgressDialog();
        switch (event.getEventType()){
            case GET_TASKFRG_SUCCESS:

               TaskFrbListBean bean = event.getTaskFrbListBean();
                if(bean != null && bean.getData() != null && bean.getData().getList() != null){
                    if(currentIndex == 0){
                        datas.clear();
                    }
                     datas.addAll(bean.getData().getList());
                    setAdapter();
                    if(bean.getData().getList().size() >= 10){
                        lv_listview.setPushEnable(true);
                    }else{
                        lv_listview.setPushEnable(false);
                    }
                }

                break;
            case GET_TASKFRG_FAILED:
                Toast.makeText(getActivity(), event.getMsg(),Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(datas.get(i).is_able_upload()){
            Intent intent = new Intent(getActivity(),AddPicAct.class);
            intent.putExtra("id",datas.get(i).getId());
            intent.putExtra("cardNum",datas.get(i).getCar_number());
            startActivity(intent);
        }
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
