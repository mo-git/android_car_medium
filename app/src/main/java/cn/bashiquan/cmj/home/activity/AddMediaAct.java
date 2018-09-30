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
import cn.bashiquan.cmj.sdk.bean.UpdatePicBean;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AddPicEvent;
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

    private LinearLayout ll_pic; // 照片区域
    private ImageView iv_car_pic;
    private RelativeLayout rl_loading;
    private GifMovieView iv_gif;

    private LinearLayout ll_car_num; // 车牌号区域
    private EditText et_car_num;

    private  TextView tv_car_type;
    private  TextView tv_location;

    private UpdatePicBean updatePicBean; // 图片的对象
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

    public void showPicAndCarNum(UpdatePicBean updatePicBean){
        if(updatePicBean.isUploadSuccess()){
            rl_loading.setVisibility(View.GONE);// 上传完成后隐藏
            ImageLoader.getInstance().displayImage(updatePicBean.getImageUrl(),iv_car_pic, ImageUtils.loadImage(0));
            if(updatePicBean.isSuccessCarNum()){
                et_car_num.setText("11133445");
                setEditPre(et_car_num,false);
            }else{
                et_car_num.setText("");
                setEditPre(et_car_num,true);
            }
        }else{
            rl_loading.setVisibility(View.VISIBLE);
            iv_gif.setMovieResource(R.drawable.loading);
            et_car_num.setText("");
            setEditPre(et_car_num,true);
        }

    }

    // 上传图片返回结果
    public void onEventMainThread(AddPicEvent event){
       switch (event.getEventType()){
           case GET_ADD_PIC_SUCCESS:
               showToast("success");
               break;
           case GET_ADD_PIC_FAILED:
               showToast("fail");
               break;

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
                // 提交成功后 跳转到首页
                EventBus.getDefault().post(new AddPicCloseEvent());
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
        image_file_name = System.currentTimeMillis() + ".jpg";
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断存储卡是否可以用，可用进行存储
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SysConstants.FILE_DCIM + image_file_name)));
        startActivityForResult(intentFromCapture, PHOTO_REQUEST_CAREMA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PHOTO_REQUEST_CAREMA:
                boolean isSuccess = Utils.saveBitmap(SysConstants.FILE_DCIM + image_file_name,image_file_name, SysConstants.FILE_upload_ROOT,150);
                String imagePath;
                if(isSuccess){
                    imagePath = SysConstants.FILE_upload_ROOT;
                }else{
                    imagePath = SysConstants.FILE_DCIM ;
                }
                updatePicBean = new UpdatePicBean();
                updatePicBean.setImageName(image_file_name);
                updatePicBean.setImagePath(imagePath);
                updatePicBean.setImageUrl("");
                updatePicBean.setUploadSuccess(false);
                updatePicBean.setSuccessCarNum(true);

                // 选择完图片后 显示图片区域 和号码区域
                ll_pic.setVisibility(View.VISIBLE);
                ll_car_num.setVisibility(View.VISIBLE);
                showPicAndCarNum(updatePicBean);

                getCoreService().getHomeManager(AddMediaAct.class.getName()).uplodeImage(UpdatePicBean.class,imagePath,image_file_name);

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
