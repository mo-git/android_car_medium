package cn.bashiquan.cmj.My.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.MyOrderInfotBean;
import cn.bashiquan.cmj.sdk.bean.MyOrderListBean;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.utils.ImageUtils;
import cn.bashiquan.cmj.utils.Utils;

/**
 * Created by mocf on 2018/9/26
 */
public class MyOrderInfoAdapter extends BaseAdapter {
    private  List<MyOrderInfotBean.OrderInfoBean> datas;
    private Context mContext;
    public MyOrderInfoAdapter(Context mContext,List<MyOrderInfotBean.OrderInfoBean> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
    }

    public void setData(List<MyOrderInfotBean.OrderInfoBean> mDatas){
        datas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_orderinfo,null);
            holder = new ViewHolder();
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_id = (TextView)convertView.findViewById(R.id.tv_id);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        MyOrderInfotBean.OrderInfoBean data = datas.get(position);
        holder.tv_name.setText(data.getDesc());
        holder.tv_id.setText(data.getToken());
        holder.iv_icon.setImageBitmap( Utils.base64strToBitmap(data.getImgcode()));
        return convertView;
    }

    public class ViewHolder{
        private TextView tv_name;
        private TextView tv_id;
        private ImageView iv_icon;
    }
}
