package cn.bashiquan.cmj.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.TaskFrbListBean;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.utils.ImageUtils;

/**
 * Created by mocf on 2018/9/28
 */
public class TaskFrgItemAdapter extends BaseAdapter {
    private List<TaskFrbListBean.TaskFrgBean> datas;
    private Context mContext;
    public TaskFrgItemAdapter(Context mContext, List<TaskFrbListBean.TaskFrgBean> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
    }

    public void setData(List<TaskFrbListBean.TaskFrgBean> mDatas){
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
            holder.iv_camera = (ImageView)convertView.findViewById(R.id.iv_camera);
            holder.tv_car_num = (TextView)convertView.findViewById(R.id.tv_car_num);
            holder.tv_ad_type = (TextView)convertView.findViewById(R.id.tv_ad_type);
            holder.tv_state = (TextView)convertView.findViewById(R.id.tv_state);
            holder.tv_task_num = (TextView)convertView.findViewById(R.id.tv_task_num);
            holder.tv_task_time = (TextView)convertView.findViewById(R.id.tv_task_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        TaskFrbListBean.TaskFrgBean data = datas.get(position);
        String uri = Constants.IMAGE_URL + data.getAd().getImgs().get(0).getPath();
        ImageLoader.getInstance().displayImage(uri,holder.iv_cion, ImageUtils.loadImage(R.drawable.defal_image));
        holder.tv_car_num.setText(data.getCar_number());
        holder.tv_ad_type.setText(data.getName());
        holder.tv_state.setText(data.getCstatus());
        holder.tv_task_num.setText(String.valueOf(data.getTask_id()));
        holder.tv_task_time.setText(data.getStart_time() + "-" + data.getEnd_time());

        if(data.is_able_upload()){
            holder.iv_camera.setVisibility(View.VISIBLE);
        }else{
            holder.iv_camera.setVisibility(View.GONE);
        }

        return convertView;
    }

    public class ViewHolder{
        ImageView iv_cion;
        ImageView iv_camera;
        TextView tv_ad_type;
        TextView tv_car_num;
        TextView tv_state;
        TextView tv_task_num;
        TextView tv_task_time;
    }

}
