package cn.bashiquan.cmj.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.event.BaseEvent;
import cn.bashiquan.cmj.sdk.service.CoreService;

import de.greenrobot.event.EventBus;

/**
 * Created by mocf on 2018/9/26
 */
public abstract  class BaseFrg extends Fragment implements View.OnClickListener {

    public abstract int contentView();
    public abstract void initView(Bundle savedInstanceState);//view的初始化，findviewbyid
    public abstract boolean titleBarVisible();// 是否显示标题栏
    public Context mContext;
    private View rootView;

    // 标题
    private RelativeLayout title_bar;;
    private RelativeLayout rl_left;
    private TextView title_left;
    private TextView title;
    private ImageView title_iv_right;
    private TextView title_right;

    public BaseFrg() {
        mContext = getActivity();
    }


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
            if (titleBarVisible()) {
                rootView = inflater.inflate(R.layout.activity_base, container, false);
                initView(rootView);
            } else {
                rootView = inflater.inflate(contentView(), container, false);
            }
            rootView.setTag(true);
            initView(savedInstanceState);
            onDetach();
        }

        return rootView;
    }

    public View getContentView() {
        return rootView;
    }

    public CoreService getCoreService(){
        return CoreService.getInstance();
    }

    protected void initView(View view){
        title = (TextView)view.findViewById(R.id.tv_title);
        title_right = (TextView)view.findViewById(R.id.title_right);
        title_left = (TextView)view.findViewById(R.id.title_left);
        title_iv_right = (ImageView)view.findViewById(R.id.title_iv_right);
        title_bar = (RelativeLayout)view.findViewById(R.id.title_bar);
        rl_left = (RelativeLayout)view.findViewById(R.id.rl_left);
        title_bar.setVisibility(titleBarVisible()? View.VISIBLE : View.GONE);
        LinearLayout base_layout = (LinearLayout)view.findViewById(R.id.base_layout);
        View contentView = View.inflate(getActivity(),contentView(), null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
        contentView.setLayoutParams(layoutParams);
        if (null != base_layout) {
            base_layout.addView(contentView);
        }
    }

    public void hideTitleBar(){
        if(title_bar != null){
            title_bar.setVisibility(View.GONE);
        }
    }

    public void showTitleBar(){
        if(title_bar != null){
            title_bar.setVisibility(View.VISIBLE);
        }
    }

    // 设置标题
    public void setTitle(String strTitle){
        if(!TextUtils.isEmpty(strTitle) && title != null){
            title.setText(strTitle);
        }
    }

    /**
     * 设置左边标题
     * @param isBack 是否是返回
     * @param str 左边的text
     */
    public void setTitleLeft(boolean isBack,String str){
        if(isBack){
            if(rl_left != null){
                rl_left.setVisibility(View.VISIBLE);
                rl_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Activity) mContext).finish();
                    }
                });
            }
        }else if(!TextUtils.isEmpty(str) && title_left!= null){
            title_left.setText(str);
            title_left.setVisibility(View.VISIBLE);
            title_left.setOnClickListener(this);
        }
    }

    /**
     * 设置右边标题
     * @param isImageV 是否是Image
     * @param imageId  Image的id
     * @param strRight  右边的text
     */
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

    public void showToat(String str){
        if(!TextUtils.isEmpty(str) && mContext != null){
            Toast.makeText(mContext,str,Toast.LENGTH_SHORT).show();
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
                onClickRightText(v);
                break;
        }
    }
    public void onClickLeftText(){};
    public void onClickRightImage(){};
    public void onClickRightText(View view){};


    @Override
    public void onDestroy() {
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();

    }

    public void onEventMainThread(BaseEvent event){}

}
