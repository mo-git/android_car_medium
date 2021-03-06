package cn.bashiquan.cmj.home.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bashiquan.cmj.R;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.utils.ImageUtils;
import cn.bashiquan.cmj.utils.widget.MyViewPager;

public class ImageBigActivity extends BaseAct implements ViewPager.OnPageChangeListener {

    private MyViewPager viewPager;
    private ImageAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    /**
     * 初始化ViewPager
     */
   private List<String> mImages = new ArrayList<>();
    private int curIndex = 0;

    @Override
    public int contentView() {
        return R.layout.activity_rv_preview;
    }

    @Override
    public boolean titleBarVisible() {
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleLeft(true,"");
        viewPager = (MyViewPager) findViewById(R.id.pager);
        viewPager.setOnPageChangeListener(this);
        mImages = getIntent().getStringArrayListExtra("datas");
        setTitle("1/" + mImages.size());
        mAdapter = new ImageAdapter();
        viewPager.setAdapter(mAdapter);
        curIndex = getIntent().getIntExtra("curIndex",0);
        viewPager.setOnPageChangeListener(this);
        if (curIndex > 0) {
            viewPager.setCurrentItem(curIndex);
        }

    }

    public class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imgDisplay;

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.item_image, container,
                    false);
            imgDisplay = (ImageView) viewLayout.findViewById(R.id.wivPhoto);

            String path = mImages.get(position);
            ImageLoader.getInstance().displayImage(path, ImageUtils.getImageViewAware(imgDisplay),ImageUtils.loadImage(R.drawable.defal_image));
            imgDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            container.addView(viewLayout);
            return viewLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {}

    @Override
    public void onPageSelected(int pos) {
        curIndex = pos;
            setTitle((pos + 1) + "/" + mImages.size());
    }

}
