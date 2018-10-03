package cn.bashiquan.cmj.My.adapter;

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
 * Created by mocf on 2018/9/26
 */
public class TextAdapter extends BaseAdapter {
    private List<String> datas;
    private Context mContext;
    public TextAdapter(Context mContext, List<String> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
    }

    public void setData(List<String> mDatas){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_notice,null);
            holder = new ViewHolder();
            holder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            holder.tv_notice_title = (TextView)convertView.findViewById(R.id.tv_notice_title);
            holder.tv_notice_content = (TextView)convertView.findViewById(R.id.tv_notice_content);
            holder.tv_invite= (TextView)convertView.findViewById(R.id.tv_invite);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public class ViewHolder{
        ImageView iv_icon;
        TextView tv_time;
        TextView tv_notice_title;
        TextView tv_notice_content;
        TextView tv_invite;

    }
}
