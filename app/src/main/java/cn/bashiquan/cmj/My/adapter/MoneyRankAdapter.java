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

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.MyDrawListBean;
import cn.bashiquan.cmj.sdk.bean.RangListBean;
import cn.bashiquan.cmj.utils.ImageUtils;
import cn.bashiquan.cmj.utils.Utils;

/**
 * Created by mocf on 2018/9/26
 */
public class MoneyRankAdapter extends BaseAdapter {
    private List<RangListBean.RangBean> datas;
    private Context mContext;
    public MoneyRankAdapter(Context mContext, List<RangListBean.RangBean> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
    }

    public void setData(List<RangListBean.RangBean> mDatas){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_money_rank_hean_view,null);
            holder = new ViewHolder();
            holder.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_money = (TextView)convertView.findViewById(R.id.tv_money);
            holder.ic_icon = (ImageView) convertView.findViewById(R.id.ic_icon);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        RangListBean.RangBean data = datas.get(position);
        holder.tv_num.setText(String.valueOf(position + 1));
        holder.tv_name.setText(data.getUser().getNickname());
        holder.tv_money.setText(data.getSum());
        ImageLoader.getInstance().displayImage(data.getUser().getAvatar_url(),holder.ic_icon, ImageUtils.loadRoundImagePic(0,360));

        return convertView;
    }

    public class ViewHolder{
        TextView tv_num;
        TextView tv_name;
        TextView tv_money;
        ImageView ic_icon;


    }
}
