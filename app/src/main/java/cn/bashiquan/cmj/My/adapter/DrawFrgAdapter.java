package cn.bashiquan.cmj.My.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.MyDrawListBean;
import cn.bashiquan.cmj.utils.Utils;

/**
 * Created by mocf on 2018/9/26
 */
public class DrawFrgAdapter extends BaseAdapter {
    private List<MyDrawListBean.DrawBean> datas;
    private Context mContext;
    public DrawFrgAdapter(Context mContext, List<MyDrawListBean.DrawBean> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
    }

    public void setData(List<MyDrawListBean.DrawBean> mDatas){
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
            holder.tv_bottom = (TextView)convertView.findViewById(R.id.tv_bottom);

            holder.tv_draw_name = (TextView)convertView.findViewById(R.id.tv_draw_name);
            holder.tv_draw_num = (TextView)convertView.findViewById(R.id.tv_draw_num);
            holder.iv_draw_icon = (ImageView) convertView.findViewById(R.id.iv_draw_icon);
            holder.ll_draw = (LinearLayout) convertView.findViewById(R.id.ll_draw);
            holder.ll_pren = (LinearLayout) convertView.findViewById(R.id.ll_pren);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        MyDrawListBean.DrawBean data = datas.get(position);
        holder.tv_num_title.setText("编号:" + data.getNums());
        holder.tv_num.setText(data.getId());
        if(data.getLuck_config() != null){
            holder.tv_time.setText(data.getLuck_config().getName());
            holder.tv_take_num.setText("参与人数:" + data.getLuck_config().getJoin_num());
            if(data.getLuck_config().getOpen_config() != null){
                holder.tv_draw_time.setText("开奖周期:" + data.getLuck_config().getOpen_config().getLuck_range() + "分钟");
                if(TextUtils.isEmpty(data.getLuck_config().getOpen_config().getLuck_min_num())){
                    holder.tv_take_num_l.setText("最低参与人数:");
                }else{
                    holder.tv_take_num_l.setText("最低参与人数:" + data.getLuck_config().getOpen_config().getLuck_min_num());
                }

            }
        }
        // 中奖布局
//            holder.tv_draw_name.setText("");
//            holder.tv_draw_num.setText("");
//            holder.iv_draw_icon.setImageBitmap( Utils.base64strToBitmap(data.getImgcode()));
//            holder.ll_draw.setVisibility(View.VISIBLE);
//        holder.tv_pre_num.setText("开奖编号[" + data.getNums() + "]");
        holder.tv_pre_num.setText("开奖编号[]");
        if(data.isStatus()){
            holder.ll_pren.setVisibility(View.GONE);
            holder.tv_state.setText("等待开奖");

        }else{
            holder.ll_pren.setVisibility(View.VISIBLE);
            holder.tv_state.setText("未中奖");
        }

        if(position == datas.size() - 1){
            holder.tv_bottom.setVisibility(View.VISIBLE);
        }else{
            holder.tv_bottom.setVisibility(View.GONE);
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
        TextView tv_bottom;

        TextView tv_draw_name;
        TextView tv_draw_num;
        ImageView iv_draw_icon;
        LinearLayout ll_draw;
        LinearLayout ll_pren;


    }
}
