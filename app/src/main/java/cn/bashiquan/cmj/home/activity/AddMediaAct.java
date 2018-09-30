package cn.bashiquan.cmj.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.MainActivity;
import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.GridpicAdapter;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.ImageUtils;
import cn.bashiquan.cmj.utils.SysConstants;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.GifMovieView;
import de.greenrobot.event.EventBus;

/**
 * Created by mo on 2018/9/28.
 * 添加媒体页
 */

public class AddMediaAct extends BaseAct  {
    private final static int PHOTO_REQUEST_CAREMA = 1;
    private String image_file_name; // 图片的名字
    private String photoPath; // 图片保存的路径

    private LinearLayout ll_pic; // 照片区域
    private ImageView iv_car_pic;
    private RelativeLayout rl_loading;
    private GifMovieView iv_gif;

    private LinearLayout ll_car_num; // 车牌号区域
    private EditText et_car_num;

    private  TextView tv_car_type;
    private  TextView tv_location;
    @Override
    public int contentView() {
        return R.layout.activity_add_media;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("车辆添加");
        setTitleLeft(true,"");
        Utils.creatCashFiles();
        initView();
        initTecentLoaction();
        initData();
    }

    private void initView() {
        tv_car_type = (TextView)findViewById(R.id.tv_car_type);
        tv_location = (TextView)findViewById(R.id.tv_location);

        ll_car_num = (LinearLayout) findViewById(R.id.ll_car_num);
        et_car_num = (EditText) findViewById(R.id.et_car_num);

        ll_pic = (LinearLayout) findViewById(R.id.ll_pic);
        iv_car_pic = (ImageView) findViewById(R.id.iv_car_pic);
        rl_loading = (RelativeLayout)findViewById(R.id.rl_loading);
        iv_gif = (GifMovieView) findViewById(R.id.iv_gif);

        findViewById(R.id.iv_add).setOnClickListener(this);
        findViewById(R.id.tv_que).setOnClickListener(this);
        findViewById(R.id.tv_finish).setOnClickListener(this);
        initTecentLoaction();
    }

    @Override
    public String getLocationCityAll(String address) {
        tv_location.setText(address);
        return super.getLocationAddress(address);
    }

    // 获取数据
    private void initData() {
//        for(int i = 0; i < 5; i++){
//            datas.add(i + "");
//        }
    }

    public void showPicAndCarNum(boolean isSuccess){
        //rl_loading.setVisibility(View.Gone); 上传完成后隐藏
        String uri = "file://" +  photoPath+image_file_name;
        ImageLoader.getInstance().displayImage(uri,iv_car_pic, ImageUtils.loadImage(0));
        // 识别牌号成功 显示号码  反之 可输入
        if(isSuccess){
            et_car_num.setText("11133445");
            setEditPre(et_car_num,false);
        }else{
            et_car_num.setText("");
            setEditPre(et_car_num,true);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_add:
                openCamera();
                break;
            case R.id.tv_que:
                // 提交成功后 跳转到任务 待审核
                EventBus.getDefault().post(new AddPicCloseEvent());
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.putExtra("index",2);
//                startActivity(intent);
                finish();
                break;
            case R.id.tv_finish:
                // 返回首页
                EventBus.getDefault().post(new AddPicCloseEvent());
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
                    // 选择完图片后 显示图片区域 和号码区域
                    ll_pic.setVisibility(View.VISIBLE);
                    ll_car_num.setVisibility(View.VISIBLE);
                    //rl_loading.setVisibility(View.VISIBLE); 上传完成后隐藏
//                    iv_gif.setMovieResource(R.drawable.loading);
                    // 调用上传接口
                    showPicAndCarNum(true);
                }
                break;
        }
    }

    // 设置EditText 是否可编辑
    public void setEditPre(EditText editText,boolean flag){
        if(flag){
            editText.setFocusable(true);
            editText.setCursorVisible(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        }else{
            editText.setCursorVisible(flag);
            editText.setFocusable(flag);
            editText.setFocusableInTouchMode(flag);
        }
    }
}
