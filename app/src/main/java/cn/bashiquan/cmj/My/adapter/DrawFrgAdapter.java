package cn.bashiquan.cmj.My.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.TaskFrbListBean;

/**
 * Created by mocf on 2018/9/26
 */
public class DrawFrgAdapter extends BaseAdapter {
    private List<TaskFrbListBean.TaskFrgBean> datas;
    private Context mContext;
    public DrawFrgAdapter(Context mContext, List<TaskFrbListBean.TaskFrgBean> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
    }

    public void setData(List<TaskFrbListBean.TaskFrgBean> mDatas){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_draw,null);
            holder = new ViewHolder();
            holder.tv_num_title = (TextView)convertView.findViewById(R.id.tv_num_title);
            holder.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            holder.tv_state = (TextView)convertView.findViewById(R.id.tv_state);
            holder.tv_draw_time = (TextView)convertView.findViewById(R.id.tv_draw_time);
            holder.tv_take_num_l = (TextView)convertView.findViewById(R.id.tv_take_num_l);
            holder.tv_take_num = (TextView)convertView.findViewById(R.id.tv_take_num);
            holder.tv_pre_num = (TextView)convertView.findViewById(R.id.tv_pre_num);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public class ViewHolder{
        TextView tv_num_title;
        TextView tv_num;
        TextView tv_time;
        TextView tv_state;
        TextView tv_draw_time;
        TextView tv_take_num_l;
        TextView tv_take_num;
        TextView tv_pre_num;

    }
}
