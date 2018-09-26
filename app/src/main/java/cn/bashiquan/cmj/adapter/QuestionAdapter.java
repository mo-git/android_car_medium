package cn.bashiquan.cmj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bashiquan.cmj.R;

import java.util.List;

/**
 * Created by mocf on 2018/9/26
 */
public class QuestionAdapter extends BaseAdapter {
    private List<String> datas;
    private Context mContext;
    public QuestionAdapter(Context mContext,List<String> mDatas){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_question,null);
            holder = new ViewHolder();
            holder.userIcon = (ImageView)convertView.findViewById(R.id.ques_user_icon);
            holder.user = (TextView)convertView.findViewById(R.id.ques_user);
            holder.time = (TextView)convertView.findViewById(R.id.ques_time);
            holder.countent = (TextView)convertView.findViewById(R.id.ques_countent);
            holder.guanzhu= (TextView)convertView.findViewById(R.id.ques_guanzhu);
            holder.renling= (TextView)convertView.findViewById(R.id.ques_renling);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.guanzhu.setTag(position);
        holder.renling.setTag(position);

        holder.user.setText("作者_"+datas.get(position));

        holder.guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                Toast.makeText(mContext,"关注"+index,Toast.LENGTH_SHORT).show();
            }
        });

        holder.renling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int) v.getTag();
                Toast.makeText(mContext,"认领" + index,Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public class ViewHolder{
        ImageView userIcon;
        TextView user;
        TextView time;
        TextView countent;
        TextView guanzhu;
        TextView renling;

    }
}
