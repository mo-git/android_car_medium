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

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.NoticeListBean;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.utils.CollectionUtils;
import cn.bashiquan.cmj.utils.ImageUtils;

import java.util.List;

/**
 * Created by mocf on 2018/9/26
 */
public class NoticeAdapter extends BaseAdapter {
    private List<NoticeListBean.NoticeBean> datas;
    private Context mContext;
    public NoticeAdapter(Context mContext, List<NoticeListBean.NoticeBean> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
    }

    public void setData(List<NoticeListBean.NoticeBean> mDatas){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_notice,null);
            holder = new ViewHolder();
            holder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            holder.tv_notice_content = (TextView)convertView.findViewById(R.id.tv_notice_content);
            holder.tv_name= (TextView)convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        NoticeListBean.NoticeBean data = datas.get(position);
        if(!CollectionUtils.isEmpty(data.getPics()) && !TextUtils.isEmpty(data.getPics().get(0).getPath())){
            String uri = Constants.IMAGE_URL + data.getPics().get(0).getPath();
            ImageLoader.getInstance().displayImage(uri,holder.iv_icon, ImageUtils.loadImage(0));
        }

        holder.tv_notice_content.setText(data.getContent());
        holder.tv_name.setText(data.getName());
        holder.tv_time.setText(data.getCreated_at());

        return convertView;
    }

    public class ViewHolder{
        ImageView iv_icon;
        TextView tv_time;
        TextView tv_notice_content;
        TextView tv_name;

    }
}
