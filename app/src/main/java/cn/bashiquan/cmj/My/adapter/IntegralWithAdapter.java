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

/**
 * Created by mocf on 2018/9/26
 */
public class IntegralWithAdapter extends BaseAdapter {
    private  List<IntegralListBean.IntegralBean> datas;
    private Context mContext;
    public IntegralWithAdapter(Context mContext, List<IntegralListBean.IntegralBean> mDatas){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_integral_with,null);
            holder = new ViewHolder();
            holder.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            holder.tv_integral_num = (TextView)convertView.findViewById(R.id.tv_integral_num);
            holder.tv_bl = (TextView)convertView.findViewById(R.id.tv_bl);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            holder.tv_state = (TextView)convertView.findViewById(R.id.tv_state);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

//        IntegralListBean.IntegralBean data = datas.get(position);
        return convertView;
    }

    public class ViewHolder{
        TextView tv_num;
        TextView tv_integral_num;
        TextView tv_bl;
        TextView tv_time;
        TextView tv_state;
    }

}
