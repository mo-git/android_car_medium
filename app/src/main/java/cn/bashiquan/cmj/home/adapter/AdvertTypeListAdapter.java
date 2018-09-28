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
 * 广告类型adapter
 */
public class AdvertTypeListAdapter extends BaseAdapter {
    private List<String> datas;
    private Context mContext;
    private int selectIndex;
    private AdvertTypeListener advertTypeListener;
    public AdvertTypeListAdapter(Context mContext, List<String> mDatas, int selectIndex,AdvertTypeListener advertTypeListener){
        this.mContext = mContext;
        this.datas = mDatas;
        this.selectIndex = selectIndex;
        this.advertTypeListener = advertTypeListener;
    }

    public void setData(List<String> mDatas,int selectIndex){
        this.datas = mDatas;
        this.selectIndex = selectIndex;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_advert_type_list,null);
            holder = new ViewHolder();
            holder.iv_type_cion = (ImageView)convertView.findViewById(R.id.iv_type_cion);
            holder.iv_select_cion = (ImageView)convertView.findViewById(R.id.iv_select_cion);
            holder.tv_type_msg = (TextView)convertView.findViewById(R.id.tv_type_msg);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_select_cion.setTag(position);
        if(position == selectIndex){
            holder.iv_select_cion.setSelected(true);
        }else{
            holder.iv_select_cion.setSelected(false);
        }


        holder.iv_select_cion.setOnClickListener(new MyListener());

        return convertView;
    }

    public class ViewHolder{
        ImageView iv_type_cion;
        ImageView iv_select_cion;
        TextView tv_type_msg;

    }

    public class MyListener implements View.OnClickListener{
        public MyListener(){
        }

        @Override
        public void onClick(View view) {
            if(advertTypeListener != null){
                int index = (int)view.getTag();
                advertTypeListener.selectClick(index);

            }
        }
    }

    public interface AdvertTypeListener{
        void selectClick(int positon);
    }
}
