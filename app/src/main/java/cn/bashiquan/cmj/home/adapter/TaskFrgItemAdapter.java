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
public class TaskFrgItemAdapter extends BaseAdapter {
    private List<String> datas;
    private Context mContext;
    public TaskFrgItemAdapter(Context mContext, List<String> mDatas){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tesk,null);
            holder = new ViewHolder();
            holder.iv_cion = (ImageView)convertView.findViewById(R.id.iv_cion);
            holder.tv_car_num = (TextView)convertView.findViewById(R.id.tv_car_num);
            holder.tv_ad_type = (TextView)convertView.findViewById(R.id.tv_ad_type);
            holder.tv_state = (TextView)convertView.findViewById(R.id.tv_state);
            holder.tv_task_num = (TextView)convertView.findViewById(R.id.tv_task_num);
            holder.tv_task_time = (TextView)convertView.findViewById(R.id.tv_task_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public class ViewHolder{
        ImageView iv_cion;
        TextView tv_ad_type;
        TextView tv_car_num;
        TextView tv_state;
        TextView tv_task_num;
        TextView tv_task_time;
    }

}
