package cn.bashiquan.cmj.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.sdk.bean.CarTypeListBean;
import cn.bashiquan.cmj.sdk.bean.MediaPicBean;
import cn.bashiquan.cmj.sdk.bean.ProvinceBean;
import cn.bashiquan.cmj.sdk.bean.RequestSubmitmmMediaBean;
import cn.bashiquan.cmj.sdk.bean.UpdatePicBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddMeidaEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddPicEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.FileUtils;
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
    private String className = AddMediaAct.class.getName();
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
    private List<CarTypeListBean.CarType> cartypes;
    private int selectCarTypeIndex = 0;

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private String province,city,district; // 省市区


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

        initDate();

        initView();
        initTecentLoaction();
    }

    private void initDate() {
        FileUtils.creatCashFiles();
        parseJson();
        getCoreService().getHomeManager(className).getCarType();
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

        tv_car_type.setOnClickListener(this);
        findViewById(R.id.iv_add).setOnClickListener(this);
        findViewById(R.id.tv_que).setOnClickListener(this);
        findViewById(R.id.tv_finish).setOnClickListener(this);
        findViewById(R.id.rl_select_city).setOnClickListener(this);
        initTecentLoaction();
    }

    @Override
    public void getLocationCityAll(String province,String city,String district) {
        this.province = province;
        this.city = city;
        this.district = district;
        tv_location.setText(province + " " + city + " " + district);
    }

    public void showPicAndCarNum(UpdatePicBean updatePicBean){
        if(updatePicBean.isUploadSuccess()){
            rl_loading.setVisibility(View.GONE);// 上传完成后隐藏
            ImageLoader.getInstance().displayImage(updatePicBean.getImageUrl(),iv_car_pic, ImageUtils.loadImage(0));
            if(updatePicBean.isSuccessCarNum()){
                et_car_num.setText(updatePicBean.getCarNum());
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
             MediaPicBean addMediaPicBean =  event.getAddMediaPicBean();
               if(addMediaPicBean != null){
                   if(!TextUtils.isEmpty(addMediaPicBean.getMsg())){
                       showToast(addMediaPicBean.getMsg());
                   }
                   updatePicBean.setUploadSuccess(true);
                   updatePicBean.setSuccessCarNum(addMediaPicBean.isState());
                   updatePicBean.setImageUrl(addMediaPicBean.getImg().getUrl());
                   updatePicBean.setImageUrlPath(addMediaPicBean.getImg().getPath());
                   updatePicBean.setCarNum(addMediaPicBean.getNum());
                   showPicAndCarNum(updatePicBean);
               }
               break;
           case GET_ADD_PIC_FAILED:
               updatePicBean = null;
               ll_pic.setVisibility(View.GONE);
               showToast(event.getMsg());
               break;

       }
    }

    public void onEventMainThread(AddMeidaEvent event){
        switch (event.getEventType()){
            case CHECK_CAR_NUM_SUCCESS:
                if(tv_car_type.getText().toString().equals("请选择")){
                    showToast("请选择一种车类型");
                }else if(TextUtils.isEmpty(tv_location.getText().toString())){
                    showToast("请选择所在城市");
                } else{
                    RequestSubmitmmMediaBean requestBean = new RequestSubmitmmMediaBean();
                    requestBean.setProvince(province);
                    requestBean.setCity(city);
                    requestBean.setDistrict(district);
                    requestBean.setCar_number(et_car_num.getText().toString().trim());
                    requestBean.setCtype(cartypes.get(selectCarTypeIndex).getId());
                    if(updatePicBean != null){
                        List<RequestSubmitmmMediaBean.PicPath> picList = new ArrayList<>();
                        RequestSubmitmmMediaBean.PicPath picPath = new RequestSubmitmmMediaBean.PicPath();
                        picPath.setPath(updatePicBean.getImageUrlPath());
                        picList.add(picPath);
                        requestBean.setFace_imgs(picList);
                    }
                    getCoreService().getHomeManager(className).addMedia(requestBean);
                }

                break;
            case CHECK_CAR_NUM_FAILED:
                showToast(event.getMsg());
                break;
            case ADD_MEDIA_SUCCESS:
                EventBus.getDefault().post(new AddPicCloseEvent());
                finish();
                break;
            case ADD_MEDIA_FAILED:
                showToast(event.getMsg());
                break;
            case GET_CAR_TYPE_SUCCESS:
                CarTypeListBean carTypeListBean = event.getCarTypeListBean();
                if(carTypeListBean != null && !CollectionUtils.isEmpty(carTypeListBean.getData())){
                    cartypes = carTypeListBean.getData();
                }
                break;
            case GETE_CAR_TYPE_FAILED:
                showToast(event.getMsg());
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
            case R.id.tv_finish:
                // 返回首页
                EventBus.getDefault().post(new AddPicCloseEvent());
                finish();
                break;
            case R.id.rl_select_city:
                showCityPickerView();
                break;
            case R.id.tv_car_type:
                showCarTypePickerView();
                break;
            case R.id.tv_que:
                if(updatePicBean == null){
                    showToast("请点击右边的加号,添加照片");
                }else if(TextUtils.isEmpty(et_car_num.getText().toString().trim())){
                    showToast("请输入车牌号");
                }else if(!Utils.isCarnumberNO(et_car_num.getText().toString().trim())){
                    showToast("车牌号不正确请重新输入");
                }else{
                    getCoreService().getHomeManager(className).checkCarNumReal(et_car_num.getText().toString().trim());
                }

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
                if(resultCode == RESULT_OK){
                    et_car_num.setText("");
                    boolean isSuccess = FileUtils.saveBitmap(SysConstants.FILE_DCIM + image_file_name,image_file_name, SysConstants.FILE_upload_ROOT,150);
                    String imagePath;
                    if(isSuccess){
                        imagePath = SysConstants.FILE_upload_ROOT;
                    }else{
                        imagePath = SysConstants.FILE_DCIM ;
                    }
                    updatePicBean = new UpdatePicBean();
                    updatePicBean.setImageName(image_file_name);
                    updatePicBean.setImagePath(imagePath);
                    updatePicBean.setImageUrlPath("");
                    updatePicBean.setImageUrl("");
                    updatePicBean.setUploadSuccess(false);
                    updatePicBean.setSuccessCarNum(true);
                    updatePicBean.setCarNum("");

                    // 选择完图片后 显示图片区域 和号码区域
                    ll_pic.setVisibility(View.VISIBLE);
                    ll_car_num.setVisibility(View.VISIBLE);
                    showPicAndCarNum(updatePicBean);

                    getCoreService().getHomeManager(AddMediaAct.class.getName()).uplodeMediaImage(UpdatePicBean.class,imagePath,image_file_name);

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


    /*****************************************/

    private void showCarTypePickerView() {// 弹出选择器
        if(CollectionUtils.isEmpty(cartypes)){
            return;
        }
        final ArrayList<ProvinceBean> tempTypes = new ArrayList<>();
        for(CarTypeListBean.CarType careType : cartypes){
            ProvinceBean provinceBean = new ProvinceBean();
            provinceBean.setName(careType.getName());
            tempTypes.add(provinceBean);
        }

        OptionsPickerView pvOptions = new OptionsPickerView(this);
        pvOptions.setTitle("");
        pvOptions.setPicker(tempTypes);//三级选择器
        pvOptions.setCyclic(false);
        pvOptions.show();
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                selectCarTypeIndex = options1;
                String carType = tempTypes.get(options1).getPickerViewText();
                tv_car_type.setText(carType);
                showToast(cartypes.get(options1).getId() + "");
            }
        });
    }

    private void showCityPickerView() {// 弹出选择器
        if(CollectionUtils.isEmpty(options1Items) && CollectionUtils.isEmpty(options2Items) && CollectionUtils.isEmpty(options3Items)){
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerView(this);
        pvOptions.setTitle("城市选择");
        pvOptions.setPicker(options1Items, options2Items, options3Items,true);//三级选择器
        pvOptions.setCyclic(false, false, false);
        pvOptions.show();
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(option2);
                district = options3Items.get(options1).get(option2).get(options3);
               tv_location.setText(province + " " + city + " " + district);
            }
        });
    }

    //  解析json填充集合
    public void parseJson() {
        String JsonData = new FileUtils().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<ProvinceBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

    }


    public ArrayList<ProvinceBean> parseData(String result) {//Gson 解析
        ArrayList<ProvinceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


}
