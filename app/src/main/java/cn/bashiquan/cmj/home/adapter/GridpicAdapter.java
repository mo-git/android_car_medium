package cn.bashiquan.cmj.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.sdk.bean.UpdatePicBean;
import cn.bashiquan.cmj.sdk.event.HomeEvent.AddPicEvent;
import cn.bashiquan.cmj.utils.ImageUtils;
import cn.bashiquan.cmj.utils.Utils;
import cn.bashiquan.cmj.utils.widget.GifMovieView;
import de.greenrobot.event.EventBus;

/**
 * Created by mocf on 2018/9/28
 */
public class GridpicAdapter extends BaseAdapter {
    private List<UpdatePicBean> datas;
    private Context mContext;
    private Activity activity;
    private int picWidth;
    private PicClickListener picClickListener;
    public GridpicAdapter(Context mContext,Activity activity,PicClickListener picClickListener, List<UpdatePicBean> mDatas){
        this.mContext = mContext;
        this.datas = mDatas;
        this.activity = activity;
        this.picClickListener = picClickListener;
        init();
    }

    public void setData(List<UpdatePicBean> mDatas){
        this.datas = mDatas;
        notifyDataSetChanged();
    }

    public void init(){
        int width = Utils.getEditWidth(activity);
        int dp = 15 + 15 + 10 + 10;
        int widthDp = Utils.dp2px(mContext,dp);
        picWidth = (width - widthDp)/3;
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
       final ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_add_pic,null);
            holder = new ViewHolder();
            holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.iv_delect = (ImageView) convertView.findViewById(R.id.iv_delect);
            holder.rl_loading = (RelativeLayout)convertView.findViewById(R.id.rl_loading);
            holder.rl_all = (RelativeLayout)convertView.findViewById(R.id.rl_all);
            holder.iv_gif = (GifMovieView)convertView.findViewById(R.id.iv_gif);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = picWidth;
            params.width = picWidth;
            holder.rl_all.setLayoutParams(params);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_delect.setTag(position);
        UpdatePicBean data = datas.get(position);
        if(!data.isUploadSuccess()){
            holder.rl_loading.setVisibility(View.VISIBLE);
            holder.iv_gif.setMovieResource(R.drawable.loading);
        }else{
            holder.rl_loading.setVisibility(View.GONE);
            String uri = data.getImageUrl();
            ImageLoader.getInstance().displayImage(uri,holder.iv_pic,ImageUtils.loadImage(0));
        }

        holder.iv_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = (int)view.getTag();
                if(picClickListener != null){
                    picClickListener.delectClick(index);
                }
            }
        });
        return convertView;
    }

    public class ViewHolder{
        ImageView iv_pic;
        ImageView iv_delect;
        RelativeLayout rl_loading;
        RelativeLayout rl_all;
        GifMovieView iv_gif;

    }

    public interface PicClickListener{
        void delectClick(int position);
    }

}
