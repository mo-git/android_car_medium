package cn.bashiquan.cmj.My.adapter;

import android.app.admin.DeviceAdminInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.MyOrderListBean;
import cn.bashiquan.cmj.sdk.bean.TaskFrbListBean;

/**
 * Created by mocf on 2018/9/26
 */
public class MyOrderAdapter extends BaseAdapter {
    private  List<MyOrderListBean.OrderBean> datas;
    private Context mContext;
    private MyOrderListener myOrderListener;
    public MyOrderAdapter(Context mContext,MyOrderListener myOrderListener, List<MyOrderListBean.OrderBean> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
        this.myOrderListener = myOrderListener;
    }

    public void setData(List<MyOrderListBean.OrderBean> mDatas){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order,null);
            holder = new ViewHolder();
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_num_right = (TextView)convertView.findViewById(R.id.tv_num_right);
            holder.tv_type_name = (TextView)convertView.findViewById(R.id.tv_type_name);
            holder.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            holder.tv_total_price = (TextView)convertView.findViewById(R.id.tv_total_price);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            holder.tv_see = (TextView)convertView.findViewById(R.id.tv_see);
            holder.tv_frund = (TextView)convertView.findViewById(R.id.tv_frund);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_see.setTag(position);
        MyOrderListBean.OrderBean data = datas.get(position);
        holder.tv_name.setText(data.getKname());
        holder.tv_num_right.setText(data.getId());
        holder.tv_type_name.setText(data.getInfo().getKey());
        holder.tv_num.setText(data.getNum());
        holder.tv_total_price.setText(data.getPoint());
        holder.tv_time.setText(data.getCreated_at());
        if(data.getOtype() == 1){
            holder.tv_see.setVisibility(View.VISIBLE);
            holder.tv_frund.setVisibility(View.GONE);
            holder.tv_see.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = (int)view.getTag();
                    if(myOrderListener != null){
                        myOrderListener.seeClick(index);
                    }
                }
            });
        }else{
            holder.tv_see.setVisibility(View.GONE);
            holder.tv_frund.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public class ViewHolder{
        TextView tv_name;
        TextView tv_num_right;
        TextView tv_type_name;
        TextView tv_num;
        TextView tv_total_price;
        TextView tv_time;
        TextView tv_see;
        TextView tv_frund;
    }

    public interface MyOrderListener{
        void seeClick(int index); // 查看
    }
}
