package cn.bashiquan.cmj.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.map.geolocation.TencentPoi;

import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.event.BaseEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.LocationEvent;
import cn.bashiquan.cmj.sdk.service.CoreService;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.DialogListener;
import cn.bashiquan.cmj.utils.MyDialog;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.ProgressHUD;
import de.greenrobot.event.EventBus;

/**
 * Created by mocf on 2018/9/26.
 */
public abstract  class BaseAct extends FragmentActivity implements TencentLocationListener,View.OnClickListener {

    public TencentLocationManager locationManager;
    public TencentLocationRequest locationRequest;
    public abstract int contentView();
    public abstract boolean titleBarVisible();// 是否显示标题栏
    public Context mContext;

    // 标题
    private RelativeLayout title_bar;;
    private RelativeLayout rl_left;
    private TextView title_left;
    private TextView title;
    private ImageView title_iv_right;
    private TextView title_right;
    private ProgressHUD progressDialog;
    public BaseAct() {
        mContext = BaseAct.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if(titleBarVisible()){
            setContentView(R.layout.activity_base);
            initTitleView();
        }else{
            setContentView(contentView());
        }
        if(!EventBus.getDefault().isRegistered(mContext)){
            EventBus.getDefault().register(this);
        }
    }

    public CoreService getCoreService(){
        return CoreService.getInstance();
    }

    protected void initTitleView(){
        title = (TextView)findViewById(R.id.tv_title);
        title_right = (TextView)findViewById(R.id.title_right);
        title_left = (TextView)findViewById(R.id.title_left);
        title_iv_right = (ImageView) findViewById(R.id.title_iv_right);
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_left = (RelativeLayout) findViewById(R.id.rl_left);
        title_bar.setVisibility(titleBarVisible()? View.VISIBLE : View.GONE);
        LinearLayout base_layout = (LinearLayout)findViewById(R.id.base_layout);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(contentView(), null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
        contentView.setLayoutParams(layoutParams);
        if (null != base_layout) {
            base_layout.addView(contentView);
        }
    }

    // 设置标题
    public void setTitle(String strTitle){
        if(!TextUtils.isEmpty(strTitle) && title != null){
            title.setText(strTitle);
        }
    }
    //设置左边标题
    public void setTitleLeft(boolean isBack,String str){
        if(isBack){
            if(rl_left != null){
                rl_left.setVisibility(View.VISIBLE);
                rl_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }else if(!TextUtils.isEmpty(str) && title_left!= null){
            title_left.setText(str);
            title_left.setVisibility(View.VISIBLE);
            title_left.setOnClickListener(this);
        }
    }

    // 设置标题右侧图片
    public void setTitleRight(boolean isImageV,int imageId,String strRight){
        if(isImageV){
            if(title_iv_right != null && imageId != 0){
                title_iv_right.setVisibility(View.VISIBLE);
                title_iv_right.setImageResource(imageId);
                title_iv_right.setOnClickListener(this);
            }
        }else if(!TextUtils.isEmpty(strRight) && title_right != null){
            title_right.setText(strRight);
            title_right.setVisibility(View.VISIBLE);
            title_right.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left:
                onClickLeftText();
                break;
            case R.id.title_iv_right:
                onClickRightImage();
                break;
            case R.id.title_right:
                onClickRightText();
                break;
        }
    }
    public void onClickLeftText(){};
    public void onClickRightImage(){};
    public void onClickRightText(){};

    /**
     * 界面跳转
     * @param mClass 跳转的类
     * @param bundle 需要到的参数,如果没有传 null
     */
    public void startAct(Class mClass,Bundle bundle){
        Intent intent = new Intent(mContext,mClass);
        if(bundle != null){
            intent.putExtra("bundle",bundle);
        }
        startActivity(intent);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void onEventMainThread(BaseEvent event){}

    @Override
    protected void onDestroy() {
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();

    }


    // 获取定位
    public void initTecentLoaction() {

        locationManager = TencentLocationManager.getInstance(this);
        locationRequest = TencentLocationRequest.create();
        locationRequest.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
        locationManager.requestLocationUpdates(locationRequest, this);
    }


    // 定位省份 城市 区县
    public void getLocationCityAll(String province,String city,String district){
    }
    // 定位城市名
    public String getLocationCityName(String cityName){
        return cityName;
    }

    // 定位地址
    public String getLocationAddress(String address){
        return address;
    }

    public String getLng(String lng){
        return lng;
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int arg1, String arg2) {
        if (arg1 == TencentLocation.ERROR_OK) {
            if (tencentLocation == null || TextUtils.isEmpty(tencentLocation.getCity())) {
                return;
            }
            locationManager.removeUpdates(this);
            String cityName = tencentLocation.getCity(); // 城市名
            String address = ""; // 地址
            List<TencentPoi> tencentPois = tencentLocation.getPoiList();
            if(!CollectionUtils.isEmpty(tencentPois)){
                address = tencentPois.get(0).getAddress();
            }
            String rovince = tencentLocation.getProvince();// 省份
            String district = tencentLocation.getDistrict(); //区县
            double lat = tencentLocation.getLatitude();
            double longt = tencentLocation.getLongitude();
            getLocationCityName(cityName);
            getLocationAddress(address);
            String lng = String.valueOf(lat) + "," +  String.valueOf(longt);
            getLng(lng);
            getLocationCityAll(rovince,cityName,district);
            EventBus.getDefault().post(new LocationEvent(cityName));
        }

    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    private static Toast toast = null;

    public void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(mContext, msg,  Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);

        } else {
            toast.setText(msg);
        }
        toast.show();
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


    public void showDialog(){

        MyDialog.showDialogDetal2(this,getString(R.string.wxauto_msg),"提示","授权","取消",false, new DialogListener() {
            @Override
            public void onSelect() {
                Utils.weiXinLogin(BaseAct.this);
            }

            @Override
            public void onCancle() { }
        });
    }

}
