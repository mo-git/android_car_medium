package cn.bashiquan.cmj.My.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.sdk.bean.JoinUserBean;
import cn.bashiquan.cmj.sdk.event.MyManager.DrawEvent;
import cn.bashiquan.cmj.sdk.service.CoreService;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.ImageUtils;
import cn.bashiquan.cmj.utils.widget.FixFlowLayout;

/**
 * Created by mo on 2018/9/26.
 */

public class MyJoinDrawAct extends BaseAct {

    private String className = MyJoinDrawAct.class.getName();
    private ImageView iv_icon;
    private TextView tv_draw;
    private TextView tv_draw_num;
    private TextView tv_join_num;
    private TextView tv_no_user;
    private LinearLayout ll_join_user;
    private WebView webView;


    @Override
    public int contentView() {
        return R.layout.activity_join_draw;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("参与抽奖");
        setTitleLeft(true,"");

        iv_icon = (ImageView)findViewById(R.id.iv_icon);
        tv_draw = (TextView) findViewById(R.id.tv_draw);
        tv_draw_num = (TextView) findViewById(R.id.tv_draw_num);
        tv_no_user = (TextView) findViewById(R.id.tv_no_user);
        tv_join_num = (TextView) findViewById(R.id.tv_join_num);
        ll_join_user = (LinearLayout) findViewById(R.id.ll_join_user);
        webView = (WebView) findViewById(R.id.webView);

        findViewById(R.id.tv_share).setOnClickListener(this);
        showProgressDialog(this,"",false);
        getData();
    }

    public void getData() {
        getCoreService().getMyManager(className).getLuckjoinUser();
    }

    public void setData(JoinUserBean.JoinBean bean){
        if(TextUtils.isEmpty(bean.getJoinId())){
            tv_draw_num.setVisibility(View.GONE);
        }else{
            tv_draw_num.setVisibility(View.VISIBLE);
            tv_draw_num.setText("您的开奖编号: " + bean.getJoinId());
        }


        if(bean.getDescribe() != null){
            webView.loadDataWithBaseURL(null, bean.getDescribe().getContent(), "text/html", "utf-8",null);
            if(!CollectionUtils.isEmpty(bean.getDescribe().getPics())){
                ImageLoader.getInstance().displayImage(bean.getDescribe().getPics().get(0),iv_icon, ImageUtils.loadImage(R.drawable.defal_image));
            }
        }

        if(bean.getLuck_config() != null){
            tv_join_num.setText(bean.getLuck_config().getJoin_num() + "人参与");
        }

        if(bean.isJoinAble()){
            tv_draw.setText("参与抽奖");
            tv_draw.setOnClickListener(this);
        }else{
            tv_draw.setText("等待开奖");
            tv_draw.setOnClickListener(this);
        }

        if(bean.getLuck() != null && !CollectionUtils.isEmpty(bean.getLuck())){
            tv_no_user.setVisibility(View.GONE);
            ll_join_user.setVisibility(View.VISIBLE);
            ll_join_user.removeAllViews();
            FixFlowLayout linearLayout = new FixFlowLayout(this);
            ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(p);
            for (JoinUserBean.LuckbeBean lubkBean : bean.getLuck()) {
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams tvp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tvp.setMargins(0, 0, 20, 0);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader.getInstance().displayImage(lubkBean.getUser().getAvatar_url(),imageView, ImageUtils.loadImage(R.drawable.defal_image));
                imageView.setLayoutParams(tvp);
                linearLayout.addView(imageView);
            }
            ll_join_user.addView(linearLayout);
        }else{
            tv_no_user.setVisibility(View.VISIBLE);
            ll_join_user.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_draw:
                showProgressDialog(this,"",false);
                CoreService.getInstance().getMyManager("Draw_item_frg").luckjoin();
                break;
            case R.id.tv_share:
                break;
        }
    }

    public void onEventMainThread(DrawEvent event){
        disProgressDialog();
        switch (event.getEvent()){
            case GET_JOIN_USER_SUCCESS:
                JoinUserBean bean = event.getJoinUserBean();
                if(bean != null && bean.getData() != null){
                    setData(bean.getData());
                }
                break;
            case GET_JOIN_USER_FAILED:
                showToast(event.getMsg());
                break;
            case JOIN_DRAW_SUCCESS:
                getData();
                break;
            case JOIN_DRAW_FAILED:

                break;
        }

    }

}
