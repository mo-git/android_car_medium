package cn.bashiquan.cmj.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import cn.bashiquan.cmj.home.adapter.TaskListAdapter;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.SysConstants;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.RefreshListView;
import de.greenrobot.event.EventBus;

/**
 * Created by mo on 2018/9/28.
 * 添加图片页
 */

public class AddPicAct extends BaseAct implements AdapterView.OnItemClickListener,GridpicAdapter.PicClickListener {
    private final static int PHOTO_REQUEST_CAREMA = 1;
    private String image_file_name; // 图片的名字
    private String photoPath; // 图片保存的路径
    private GridView gv_grid_view;
    private GridpicAdapter adapter;
    private List<String> datas = new ArrayList<>();
    private LinearLayout ll_grid;
    private  TextView tv_card_num;
    private  TextView tv_card_part;
    private  TextView tv_location;
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
        Utils.creatCashFiles();
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

    // 获取数据
    private void initData() {
//        for(int i = 0; i < 5; i++){
//            datas.add(i + "");
//        }
        initAdapter();
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
        Intent intent = new Intent(this,ImageBigActivity.class);
        intent.putStringArrayListExtra("datas",(ArrayList<String>) datas);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_add:
                openCamera();
                break;
            case R.id.tv_sub:
                // 提交成功后 跳转到任务 待审核
                EventBus.getDefault().post(new AddPicCloseEvent(1));
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("index",2);
                startActivity(intent);
                finish();
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
//                boolean isSuccess = Utils.saveBitmap(photoPath + image_file_name,image_file_name, SysConstants.FILE_upload_ROOT,150);
                String filePath;
//                if(isSuccess){
//                    filePath = SysConstants.FILE_upload_ROOT + image_file_name;
//                }else{
                   filePath = photoPath+image_file_name;
//                }

                if (!TextUtils.isEmpty(filePath)) {
                    datas.add(filePath);
                    initAdapter();
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
