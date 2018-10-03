package cn.bashiquan.cmj.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.bashiquan.cmj.R;

/**
 * Created by mocf on 2018/9/28
 */
public class IntegralShopAdapter extends BaseAdapter {
    private List<String> datas;
    private Context mContext;
    public IntegralShopAdapter(Context mContext, List<String> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
    }

    public void setData(List<String> mDatas){
        this.datas = mDatas;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_list,null);
            holder = new ViewHolder();
            holder.iv_cion = (ImageView)convertView.findViewById(R.id.iv_cion);
            holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
            holder.tv_sales_num = (TextView)convertView.findViewById(R.id.tv_sales_num);
            holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public class ViewHolder{
        ImageView iv_cion;
        TextView tv_title;
        TextView tv_sales_num;
        TextView tv_price;
    }


}
