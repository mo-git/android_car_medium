package cn.bashiquan.cmj.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bashiquan.cmj.R;

/**
 * Created by mocf on 2018/9/28
 */
public class MediaListAdapter extends BaseAdapter {
    private List<String> datas;
    private Context mContext;
    private MediaListener mediaListener;
    public MediaListAdapter(Context mContext, List<String> mDatas,MediaListener mediaListener){
        this.mContext = mContext;
        this.datas = mDatas;
        this.mediaListener = mediaListener;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_media_list,null);
            holder = new ViewHolder();
            holder.iv_cion = (ImageView)convertView.findViewById(R.id.iv_cion);
            holder.tv_car_num = (TextView)convertView.findViewById(R.id.tv_car_num);
            holder.tv_right = (TextView)convertView.findViewById(R.id.tv_right);
            holder.car_type = (TextView)convertView.findViewById(R.id.car_type);
            holder.tv_city= (TextView)convertView.findViewById(R.id.tv_city);
            holder.tv_time_name= (TextView)convertView.findViewById(R.id.tv_time_name);
            holder.tv_time= (TextView)convertView.findViewById(R.id.tv_time);

            holder.tv_cancle_task= (TextView)convertView.findViewById(R.id.tv_cancle_task);
            holder.tv_camare= (TextView)convertView.findViewById(R.id.tv_camare);
            holder.tv_task= (TextView)convertView.findViewById(R.id.tv_task);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_camare.setTag(position);
        holder.tv_cancle_task.setTag(position);
        holder.tv_task.setTag(position);

        holder.tv_car_num.setText(datas.get(position));

        holder.tv_cancle_task.setOnClickListener(new MyListener(0));
        holder.tv_camare.setOnClickListener(new MyListener(1));
        holder.tv_task.setOnClickListener(new MyListener(2));

        return convertView;
    }

    public class ViewHolder{
        ImageView iv_cion;
        TextView tv_car_num;
        TextView tv_right;
        TextView car_type;
        TextView tv_city;
        TextView tv_time_name;
        TextView tv_time;

        TextView tv_cancle_task;
        TextView tv_camare;
        TextView tv_task;
    }

    public class MyListener implements View.OnClickListener{
        private int flag = 0; // 0 取消任务  1 拍照 2 接受任务
        public MyListener(int flag){
            this.flag = flag;
        }

        @Override
        public void onClick(View view) {
            if(mediaListener != null){
                int index = (int)view.getTag();
                if(flag == 0){
                    mediaListener.cancleTaskClick (index);
                }else if(flag == 1){
                    mediaListener.camcarClick(index);
                }else if(flag == 2){
                    mediaListener.taskClick(index);
                }
            }
        }
    }

    public interface MediaListener{
        void cancleTaskClick(int positon);// 取消任务
        void camcarClick(int positon); // 拍照
        void taskClick(int positon); // 接受任务
    }
}
