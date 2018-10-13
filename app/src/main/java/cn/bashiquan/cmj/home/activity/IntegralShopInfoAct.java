package cn.bashiquan.cmj.home.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.home.adapter.IntegralShopAdapter;
import cn.bashiquan.cmj.sdk.bean.ProductBean;
import cn.bashiquan.cmj.sdk.bean.ProductListBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.AddPicCloseEvent;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.ShopEvent;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.ImageUtils;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.FixFlowLayout;
import cn.bashiquan.cmj.utils.widget.RefreshListView;

/**
 * Created by mo on 2018/9/28.
 * 积分商城详情
 */

public class IntegralShopInfoAct extends BaseAct{
    private String className = IntegralShopInfoAct.class.getName();
    private WebView wv_view;
    private ImageView iv_icon;

    private TextView tv_select;
    private RelativeLayout rl_bottom_view;
    private FixFlowLayout ll_productname;
    private FixFlowLayout ll_productinfo;


    private List<ProductListBean.ProductBean> datas = new ArrayList<>();
    private int id = 0;
    private String cover;
    private  LinearLayout.LayoutParams contentLp;
    private  ViewGroup.MarginLayoutParams lp;
    private ProductBean.Product product;// 产品信息


    @Override
    public int contentView() {
        return R.layout.activity_integral_info;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("商品");
        setTitleLeft(true,"");
        wv_view = (WebView) findViewById(R.id.wv_view);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_select = (TextView) findViewById(R.id.tv_select);
        rl_bottom_view = (RelativeLayout) findViewById(R.id.rl_bottom_view);
        ll_productname = (FixFlowLayout) findViewById(R.id.ll_productname);
        ll_productinfo = (FixFlowLayout) findViewById(R.id.ll_productinfo);

        findViewById(R.id.tv_select).setOnClickListener(this);
        findViewById(R.id.tv_cancle).setOnClickListener(this);
        findViewById(R.id.tv_que).setOnClickListener(this);
        findViewById(R.id.rl_bottom_view).setOnClickListener(this);
        showProgressDialog(this,"",false);
        contentLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        contentLp.setMargins(Utils.dp2px(mContext, 15), 0, Utils.dp2px(mContext, 15), 0);
        lp = new ViewGroup.MarginLayoutParams( ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        lp.setMargins(Utils.dp2px(mContext, 5), Utils.dp2px(mContext, 5), Utils.dp2px(mContext, 5), Utils.dp2px(mContext, 5));

        ll_productinfo.setLayoutParams(contentLp);
        initData();
    }

    // 获取数据
    private void initData() {
        id = getIntent().getIntExtra("id",0);
        cover = getIntent().getStringExtra("cover");
        ImageLoader.getInstance().displayImage(cover,iv_icon, ImageUtils.loadImage(0));
        getCoreService().getHomeManager(className).getProductInfo(id);
    }

    // 显示网页内容
    public void setData(ProductBean.ProductsInfoBean data){
        wv_view.loadDataWithBaseURL(null, data.getContent(), "text/html", "utf-8",null);
        product = data.getProduct();
    }

    // 显示所有产品
    private void setProduct() {
        if(product != null &&  product.getAbc() != null && !CollectionUtils.isEmpty( product.getAbc().get(0))){
            ll_productname.setLayoutParams(contentLp);
            List<ProductBean.AbcBean> abcBeans = product.getAbc().get(0);
            ll_productname.removeAllViews();
            for(int i = 0; i < abcBeans.size(); i++){
                ll_productname.addView(initProduct(abcBeans.get(i).getName(),i),lp);
            }
        }



    }

    // 显示产品所对应的信息
    private void setProductInfo(int selectProductIndex) {
        int productId = product.getAbc().get(0).get(selectProductIndex).getId();
        List<ProductBean.inputDataBean> inputDataBeans = product.getInputData();
        for(int i = 0; i < inputDataBeans.size(); i++){
//            inputDataBeans.get(i).getIdkey()

        }
        ll_productinfo.removeAllViews();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()){
            case R.id.tv_select:
                if(product != null &&  product.getAbc() != null && !CollectionUtils.isEmpty( product.getAbc().get(0))){
                    setProduct();
                    rl_bottom_view.setVisibility(View.VISIBLE);
                    tv_select.setVisibility(View.GONE);
                }
                break;
            case R.id.rl_bottom_view:
            case R.id.tv_cancle:
                rl_bottom_view.setVisibility(View.GONE);
                tv_select.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_que:

                break;
        }
    }


    public void onEventMainThread(ShopEvent event){
        disProgressDialog();
        switch (event.getEventType()){
            case GET_PROTECTINFO_SUCCESS:
                ProductBean productBean = event.getProductBean();
                if(productBean != null){
                    setData(productBean.getData());
                }
                break;
            case GET_PROTECTINFO_FAILED:
                showToast(event.getMsg());
                break;
        }
    }

    public TextView initProduct(String name,int index) {
        TextView view = new TextView(mContext);
        view.setText(name);
        view.setTag(index);
        view.setSingleLine();
        view.setPadding(10, 10, 10, 10);
        view.setGravity(Gravity.CENTER);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        view.setTextColor(getResources().getColor(R.color.text_blue));
        view.setBackgroundResource(R.drawable.circular3_bg);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectIndex = (int)v.getTag();

            }
        });
        return view;
    }



}
