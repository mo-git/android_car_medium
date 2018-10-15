package cn.bashiquan.cmj.My.adapter;

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order,null);
            holder = new ViewHolder();
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_num_right = (TextView)convertView.findViewById(R.id.tv_num_right);
            holder.tv_type_name = (TextView)convertView.findViewById(R.id.tv_type_name);
            holder.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            holder.tv_total_price = (TextView)convertView.findViewById(R.id.tv_total_price);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            holder.tv_button = (TextView)convertView.findViewById(R.id.tv_button);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_button.setTag(position);

        holder.tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = (int)view.getTag();
                if(myOrderListener != null){
                    myOrderListener.seeClick(index);
                }
            }
        });


        return convertView;
    }

    public class ViewHolder{
        TextView tv_name;
        TextView tv_num_right;
        TextView tv_type_name;
        TextView tv_num;
        TextView tv_total_price;
        TextView tv_time;
        TextView tv_button;
    }

    public interface MyOrderListener{
        void seeClick(int index); // 查看
    }
}
