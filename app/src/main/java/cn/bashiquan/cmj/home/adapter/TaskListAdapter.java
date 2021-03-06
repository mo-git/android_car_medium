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
import cn.bashiquan.cmj.sdk.bean.TaskListBean;

/**
 * Created by mocf on 2018/9/28
 */
public class TaskListAdapter extends BaseAdapter {
    private List<TaskListBean.TaskBean> datas;
    private Context mContext;
    public TaskListAdapter(Context mContext, List<TaskListBean.TaskBean> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
    }

    public void setData(List<TaskListBean.TaskBean> mDatas){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task_list,null);
            holder = new ViewHolder();
            holder.tv_start_time = (TextView) convertView.findViewById(R.id.tv_start_time);
            holder.tv_index = (TextView) convertView.findViewById(R.id.tv_index);
            holder.tv_status = (TextView)convertView.findViewById(R.id.tv_status);
            holder.tv_end_time = (TextView)convertView.findViewById(R.id.tv_end_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        TaskListBean.TaskBean data = datas.get(position);
        holder.tv_start_time.setText("开始时间: " + data.getStart_time());
        holder.tv_end_time.setText("结束时间: " + data.getEnd_time());
        holder.tv_status.setText(data.getCstatus());
        holder.tv_index.setText("第" + data.getBatch_id() + "期");
        if(data.isOn()){
            holder.tv_index.setTextColor(mContext.getResources().getColor(R.color.deep_6));
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.tv_status.setBackgroundResource(R.drawable.circular4_bg);
        }else{
            holder.tv_index.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.tv_status.setBackgroundResource(R.drawable.circular8_bg);
            holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.white));

        }

        return convertView;
    }

    public class ViewHolder{
        TextView tv_start_time;
        TextView tv_index;
        TextView tv_status;
        TextView tv_end_time;

    }

}
