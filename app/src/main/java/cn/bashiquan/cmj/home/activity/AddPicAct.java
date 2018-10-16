package cn.bashiquan.cmj.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.MainActivity;
import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.GridpicAdapter;
import cn.bashiquan.cmj.sdk.bean.AddPicBean;
import cn.bashiquan.cmj.sdk.bean.RequestSubmitTaskBean;
import cn.bashiquan.cmj.sdk.bean.TaskInfoReposeBean;
import cn.bashiquan.cmj.sdk.bean.UpdatePicBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddPicEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.TaskEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.FileUtils;
import cn.bashiquan.cmj.utils.SysConstants;
import cn.bashiquan.cmj.utils.Utils;
import de.greenrobot.event.EventBus;

/**
 * Created by mo on 2018/9/28.
 * 添加图片页
 */

public class AddPicAct extends BaseAct implements AdapterView.OnItemClickListener,GridpicAdapter.PicClickListener {
    private String className = AddMediaAct.class.getName();
    private final static int PHOTO_REQUEST_CAREMA = 1;
    private String image_file_name; // 图片的名字
    private String photoPath; // 图片保存的路径
    private GridView gv_grid_view;
    private GridpicAdapter adapter;
    private List<UpdatePicBean> datas = new ArrayList<>();
    private LinearLayout ll_grid;
    private  TextView tv_card_num;
    private  TextView tv_card_part;
    private  TextView tv_location;
    private int id;
    private  String lng = "";;
    private String cardNum;
    @Override
    public int contentView() {
        return R.layout.activity_add_pic;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("监测");
        setTitleLeft(true,"");
        FileUtils.creatCashFiles();
        initView();
        initData();
    }

    private void initView() {
        tv_card_num = (TextView)findViewById(R.id.tv_card_num);
        tv_location = (TextView)findViewById(R.id.tv_location);
        tv_card_part = (TextView)findViewById(R.id.tv_card_part);
        ll_grid = (LinearLayout) findViewById(R.id.ll_grid);
        gv_grid_view = (GridView)findViewById(R.id.gv_grid_view);
        gv_grid_view.setOnItemClickListener(this);
        findViewById(R.id.iv_add).setOnClickListener(this);
        findViewById(R.id.tv_sub).setOnClickListener(this);
        initTecentLoaction();
    }

    @Override
    public String getLocationAddress(String address) {
        tv_location.setText(address);
        return super.getLocationAddress(address);
    }

    @Override
    public String getLng(String lng) {
        this.lng = lng;
        return super.getLng(lng);
    }

    // 获取数据
    private void initData() {
        id = getIntent().getIntExtra("id",0);
        cardNum = getIntent().getStringExtra("cardNum");
        tv_card_num.setText(cardNum);
        getCoreService().getHomeManager(className).getTaskInfo(id);
    }

    public void initAdapter(){
        if(CollectionUtils.isEmpty(datas)){
            ll_grid.setVisibility(View.GONE);
        }else{
            ll_grid.setVisibility(View.VISIBLE);
        }
        if(adapter == null){
            adapter = new GridpicAdapter(this,AddPicAct.this,this,datas);
            gv_grid_view.setAdapter(adapter);
        }else{
            adapter.setData(datas);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ArrayList<String> urls = new ArrayList<>();
        for(UpdatePicBean updatePicBean : datas){
            urls.add(updatePicBean.getImageUrl());

        }
        Intent intent = new Intent(this,ImageBigActivity.class);
        intent.putStringArrayListExtra("datas",urls);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_add:
                if(datas.size() >= 6){
                    showToast("最多只能选6张图片哦!");
                }else {
                    openCamera();
                }

                break;
            case R.id.tv_sub:
                // 提交成功后 跳转到任务 待审核
                if(!CollectionUtils.isEmpty(datas) && datas.size() >= 2){
                    showProgressDialog(this,"",false);
                    RequestSubmitTaskBean submitTaskBean = new RequestSubmitTaskBean();
                    submitTaskBean.setId(id);
                    submitTaskBean.setAddress(tv_location.getText().toString().trim());
                    submitTaskBean.setLng(lng);
                    List<RequestSubmitTaskBean.PicPath> picPaths = new ArrayList<>();
                    for(UpdatePicBean updatePicBean : datas){
                        RequestSubmitTaskBean.PicPath picPath = new RequestSubmitTaskBean.PicPath();
                        picPath.setPath(updatePicBean.getImageUrlPath());
                        picPaths.add(picPath);
                    }
                    submitTaskBean.setImgs(picPaths);
                    getCoreService().getHomeManager(className).submitTask(submitTaskBean);

                }else{
                    showToast("请添加至少2张照片");
                }

                break;
        }
    }

    public void onEventMainThread(TaskEvent event){
        switch (event.getEventType()){
            case GET_TASKINFO_SUCCESS:
                TaskInfoReposeBean taskInfoReposeBean = event.getTaskInfoReposeBean();
                if(taskInfoReposeBean != null && !CollectionUtils.isEmpty(taskInfoReposeBean.getData().getImgs())){
                    for(TaskInfoReposeBean.PicPath picPath : taskInfoReposeBean.getData().getImgs()){
                        UpdatePicBean updatePicBean = new UpdatePicBean();
                        updatePicBean.setImageName("");
                        updatePicBean.setImagePath("");
                        updatePicBean.setImageUrl(picPath.getUrl());
                        updatePicBean.setImageUrlPath(picPath.getPath());
                        updatePicBean.setUploadSuccess(true);
                        updatePicBean.setSuccessCarNum(false);
                        datas.add(updatePicBean);
                    }
                    initAdapter();
                }

                break;
            case GET_TASKINFO_FAILED:
                showToast(event.getMsg());
                break;
            case GET_SUBMIT_SUCCESS:
                disProgressDialog();
                EventBus.getDefault().post(new AddPicCloseEvent(1));
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("index",2);
                intent.putExtra("isBunndle",true);
                startActivity(intent);
                finish();
                break;
            case GET_SUBMIT_FAILED:
                showToast(event.getMsg());
                break;
        }
    }

    public void onEventMainThread(AddPicEvent event){
        switch (event.getEventType()){
            case GET_ADD_PIC_SUCCESS:
                AddPicBean addPicBean = event.getAddPicBean();
                if(addPicBean != null){
                    for(UpdatePicBean data : datas){
                        if(data.getImageName().equals(event.getImageName())){
                            data.setImageUrl(addPicBean.getData().getUrl());
                            data.setImageUrlPath(addPicBean.getData().getPath());
                            data.setUploadSuccess(true);
                            initAdapter();
                            break;
                        }
                    }
                }
                break;
            case GET_ADD_PIC_FAILED:
                showToast(event.getMsg());
                break;
        }
    }


    public void openCamera() {

        photoPath = SysConstants.FILE_DCIM;
        image_file_name = System.currentTimeMillis() + ".jpg";
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断存储卡是否可以用，可用进行存储
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoPath + image_file_name)));
        startActivityForResult(intentFromCapture, PHOTO_REQUEST_CAREMA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PHOTO_REQUEST_CAREMA:
                if(resultCode == RESULT_OK) {
//                    boolean isSuccess = FileUtils.saveBitmap(SysConstants.FILE_DCIM + image_file_name,image_file_name, SysConstants.FILE_upload_ROOT,150);
                    String imagePath;
//                    if(isSuccess){
//                        imagePath = SysConstants.FILE_upload_ROOT;
//                    }else{
                        imagePath = SysConstants.FILE_DCIM ;
//                    }
                    UpdatePicBean updatePicBean = new UpdatePicBean();
                    updatePicBean.setImageName(image_file_name);
                    updatePicBean.setImagePath(imagePath);
                    updatePicBean.setImageUrl("");
                    updatePicBean.setImageUrlPath("");
                    updatePicBean.setUploadSuccess(false);
                    updatePicBean.setSuccessCarNum(false);
                    datas.add(updatePicBean);
                    initAdapter();
                    getCoreService().getHomeManager(AddMediaAct.class.getName()).uplodeTaskImage(UpdatePicBean.class,imagePath,image_file_name);
                }

                break;
        }
    }

    @Override
    public void delectClick(int position) {
        datas.remove(position);
        initAdapter();
    }
}
