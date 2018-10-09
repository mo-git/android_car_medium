package cn.bashiquan.cmj.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.MediaListBean;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.utils.ImageUtils;

/**
 * Created by mocf on 2018/9/28
 */
public class MediaListAdapter extends BaseAdapter {
    private List<MediaListBean.MediaBean> datas;
    private Context mContext;
    private MediaListener mediaListener;
    public MediaListAdapter(Context mContext, List<MediaListBean.MediaBean> mDatas, MediaListener mediaListener){
        this.mContext = mContext;
        this.datas = mDatas;
        this.mediaListener = mediaListener;
    }

    public void setData(List<MediaListBean.MediaBean> mDatas){
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

        MediaListBean.MediaBean data = datas.get(position);
        holder.tv_car_num.setText(data.getCar_number());
        String uri = Constants.IMAGE_URL + data.getFace_imgs().get(0).getPath();
        ImageLoader.getInstance().displayImage(uri,holder.iv_cion, ImageUtils.loadImage(0));

        holder.car_type.setText(data.getType());
        holder.tv_city.setText(data.getCity());
        if(TextUtils.isEmpty(data.getEnd_time())){
            holder.tv_time.setVisibility(View.GONE);
        }else{
            holder.tv_time.setText(data.getEnd_time());
            holder.tv_time.setVisibility(View.VISIBLE);
        }

        if(TextUtils.isEmpty(data.getAd_name())){
            holder.tv_right.setText("空置");
            holder.tv_right.setTextColor(mContext.getResources().getColor(R.color.color_3));
        }else{
            holder.tv_right.setText("在刊: " + data.getAd_name());
            holder.tv_right.setTextColor(mContext.getResources().getColor(R.color.color_red));
        }

        if(data.isAble_del()){
            holder.tv_cancle_task.setOnClickListener(new MyListener(0));
            holder.tv_cancle_task.setVisibility(View.VISIBLE);
        }else{
            holder.tv_cancle_task.setOnClickListener(null);
            holder.tv_cancle_task.setVisibility(View.GONE);
        }

        if(data.isAble_upload()){
            holder.tv_camare.setTextColor(mContext.getResources().getColor(R.color.deep_6));
            holder.tv_camare.setOnClickListener(new MyListener(1));
        }else{
            holder.tv_camare.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.tv_camare.setOnClickListener(null);
        }

        if(!TextUtils.isEmpty(data.getTask_id())){
            holder.tv_task.setOnClickListener(null);
            holder.tv_task.setTextColor(mContext.getResources().getColor(R.color.gray));
        }else{
            holder.tv_task.setOnClickListener(new MyListener(2));
            holder.tv_task.setTextColor(mContext.getResources().getColor(R.color.deep_6));
        }


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
