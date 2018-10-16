package cn.bashiquan.cmj.My.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.IntegralListBean;
import cn.bashiquan.cmj.sdk.bean.MyOrderListBean;

/**
 * Created by mocf on 2018/9/26
 */
public class IntegralAdapter extends BaseAdapter {
    private  List<IntegralListBean.IntegralBean> datas;
    private Context mContext;
    public IntegralAdapter(Context mContext, List<IntegralListBean.IntegralBean> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
    }

    public void setData(List<IntegralListBean.IntegralBean> mDatas){
        datas.clear();
        datas.addAll(mDatas);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_integral,null);
            holder = new ViewHolder();
            holder.tv_pont = (TextView)convertView.findViewById(R.id.tv_pont);
            holder.tv_type_name = (TextView)convertView.findViewById(R.id.tv_type_name);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        IntegralListBean.IntegralBean data = datas.get(position);
        holder.tv_pont.setText(data.getOp_point());
        holder.tv_type_name.setText(data.getRemark());
        holder.tv_time.setText(data.getUpdated_at());
        return convertView;
    }

    public class ViewHolder{
        TextView tv_pont;
        TextView tv_type_name;
        TextView tv_time;
    }

}
