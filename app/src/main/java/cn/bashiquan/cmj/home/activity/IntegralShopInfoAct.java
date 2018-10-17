package cn.bashiquan.cmj.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    private LinearLayout content_ll;


    private int id = 0;
    private String cover;
    private ProductBean.Product product;// 产品信息
    private int selectIndex = -1;
    private ProductBean.ProductsInfoBean data;
    private HashMap<Integer, ProductBean.AbcBean> selectValue = new HashMap<>(); //选中的key

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
        content_ll = (LinearLayout) findViewById(R.id.content_ll);
        rl_bottom_view.getBackground().setAlpha(100);
        findViewById(R.id.tv_select).setOnClickListener(this);
        findViewById(R.id.tv_cancle).setOnClickListener(this);
        findViewById(R.id.tv_que).setOnClickListener(this);
        findViewById(R.id.ll_view).setOnClickListener(null);
        rl_bottom_view.setOnClickListener(this);
        showProgressDialog(this,"",false);
        initData();
    }

    // 获取数据
    private void initData() {
        id = getIntent().getIntExtra("id",0);
        cover = getIntent().getStringExtra("cover");
        ImageLoader.getInstance().displayImage(cover,ImageUtils.getImageViewAware(iv_icon), ImageUtils.loadImage(R.drawable.defal_image));
        getCoreService().getHomeManager(className).getProductInfo(id);
    }

    // 显示网页内容
    public void setData(ProductBean.ProductsInfoBean data){
        wv_view.loadDataWithBaseURL(null, data.getContent(), "text/html", "utf-8",null);
        product = data.getProduct();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()){
            case R.id.tv_select:
                if(product != null &&  product.getAbc() != null && !CollectionUtils.isEmpty( product.getAbc())){
                    setProduct(data);
                    checkClick(getSelectIds());
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
                if(TextUtils.isEmpty(getSelectIds()) || selectValue.size() != product.getAbc().size()){
                    showToast("请选择一个产品");
                }else{
                    String selectIds = getSelectIds();
                    selectIds = selectIds.substring(0,selectIds.length() - 1);
                    for(ProductBean.InputDataBean inputDataBean : product.getInputData()){
                        if(checkIds(selectIds,inputDataBean)){
                            Intent intent = new Intent(this,IntegralShopPayAct.class);
                            intent.putExtra("name",data.getName());
                            intent.putExtra("id",data.getId());
                            intent.putExtra("data",inputDataBean);
                            startActivity(intent);
                           break;
                        }
                    }
                    finish();
                }
                break;
        }
    }

    public boolean checkIds(String selectIds,ProductBean.InputDataBean inputDataBean){
        boolean flag = true;
        String idkey = inputDataBean.getIdkey();
        String[] ids = selectIds.split(",");
        for(String str : ids){
            if(!idkey.contains(str)){
                flag = false;
                break;
            }
        }
        return flag;
    }


    public void onEventMainThread(ShopEvent event){
        disProgressDialog();
        switch (event.getEventType()){
            case GET_PROTECTINFO_SUCCESS:
                ProductBean productBean = event.getProductBean();
                if(productBean != null && productBean.getData() != null){
                    data = productBean.getData();
                    setData(productBean.getData());
                }
                break;
            case GET_PROTECTINFO_FAILED:
                showToast(event.getMsg());
                break;
        }
    }


    // 显示产品
    private void setProduct(ProductBean.ProductsInfoBean bean) {
        content_ll.removeAllViews();
        ProductBean.Product info = bean.getProduct();
        for (int i = 0; i < info.getAbc().size(); i++) {
            List<ProductBean.AbcBean> list = info.getAbc().get(i);
            FixFlowLayout linearLayout = new FixFlowLayout(this);
            ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(p);
            for (ProductBean.AbcBean neiBean : list) {
                TextView textView = new TextView(this);
                LinearLayout.LayoutParams tvp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tvp.setMargins(20, 20, 20, 0);
                textView.setLayoutParams(tvp);
                textView.setPadding(10,10,10,10);
                textView.setText(neiBean.getName());
                textView.setTag(R.id.tag_id, neiBean.getId());
                textView.setTextColor(getResources().getColor(R.color.text_blue));
                textView.setBackgroundResource(R.drawable.angle_bg);
                textView.setTag(R.id.tag_check, true);
                textView.setTag(R.id.tag_type, i);
                textView.setTag(R.id.tag_bean, neiBean);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClickView(v);
                    }
                });
                linearLayout.addView(textView);
            }
            content_ll.addView(linearLayout);
        }
    }


    private void ClickView(View v) {
        //控件id
        int clickid = Integer.valueOf(v.getTag(R.id.tag_id).toString());
        //是否可以选中
        Boolean boolen = Boolean.valueOf(v.getTag(R.id.tag_check).toString());
        //是哪一个分组
        int type = Integer.valueOf(v.getTag(R.id.tag_type).toString());
        //选中的实体
        ProductBean.AbcBean abc = (ProductBean.AbcBean) (v.getTag(R.id.tag_bean));
        if (!boolen) {
            return;
        }

        String ids = getSelectIds();
        if (ids.contains(clickid+"")) {
            selectValue.remove(type);
        } else {
            selectValue.put(type, abc);
        }
        checkClick(getSelectIds());
    }


    private String getSelectIds() {
        Set<Integer> types = selectValue.keySet();
        StringBuffer sb = new StringBuffer();
        for (Integer id : types) {
            ProductBean.AbcBean abcBean = selectValue.get(id);
            sb.append(abcBean.getId() + ",");
        }
        return sb.toString();
    }



    private String getSelectIds(int type) {
        Set<Integer> types = selectValue.keySet();
        StringBuffer sb = new StringBuffer();
        for (Integer id : types) {
            ProductBean.AbcBean abcBean = selectValue.get(id);
            if(id!=type)
                sb.append(abcBean.getId() + ",");
        }
        return sb.toString();
    }
    /**
     * 验证key
     * @param ids
     * @return
     */

    private Boolean checkClick(String ids) {
        Boolean flg = true;
        for (int i = 0; i < content_ll.getChildCount(); i++) {
            FixFlowLayout linearLayout = (FixFlowLayout) content_ll.getChildAt(i);
            for (int j = 0; j < linearLayout.getChildCount(); j++) {
                TextView tv = (TextView) linearLayout.getChildAt(j);
                int id = Integer.valueOf(tv.getTag(R.id.tag_id).toString());
                int type = Integer.valueOf(tv.getTag(R.id.tag_type).toString());
                String types =  getSelectIds(type);
                if (checkCanClick(types + id)) {
                    tv.setTag(R.id.tag_check, true);
                    if (ids.contains(id + "")) {
                        //已选中
                        tv.setBackgroundResource(R.drawable.angle_b2);
                        tv.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        //未选中
                        tv.setBackgroundResource(R.drawable.angle_bg);
                        tv.setTextColor(getResources().getColor(R.color.text_blue));
                    }
                } else {
                    tv.setTag(R.id.tag_check, false);
                    tv.setBackgroundResource(R.drawable.angle_b3);
                    tv.setTextColor(getResources().getColor(R.color.white));
                }
            }

        }
        return flg;
    }


    /**
     * 判断当前的View是否可以点击
     *
     * @param ids
     * @return
     */
    private Boolean checkCanClick(String ids) {

        Boolean click = false;
        String[] idsArray = ids.split(",");
        ProductBean.Product info = data.getProduct();
        List<ProductBean.InputDataBean> inputs = info.getInputData();
        for (ProductBean.InputDataBean inputDataBean : inputs) {
            Boolean contain = true;
            for (String id : idsArray) {
                if (!inputDataBean.getIdkey().contains(id)) {
                    contain = false;
                    break;
                }
            }
            if (contain) {
                click = true;
                break;
            }
        }
        return click;
    }



}
