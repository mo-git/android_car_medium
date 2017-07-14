package com.zhidian.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.zhidian.app.MainActivity;
import com.zhidian.app.R;
import com.zhidian.app.base.BaseAct;
import com.zhidian.app.utils.ImageUtils;

/**
 * Created by mocf on 2017/7/11.
 */
public class SplashAct extends BaseAct {

    View view;
    @Override
    public int contentView() {
        return R.layout.activity_splash;
    }

    @Override
    public boolean titleBarVisible() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_splash, null);
        ImageView splash_logo = (ImageView) findViewById(R.id.splash_logo);
        ImageSize imageSize = new ImageSize(320,480);
        String imageUri = "drawable://" + R.drawable.splash_logo;
        ImageLoader.getInstance().displayImage(imageUri,new ImageViewAware(splash_logo), ImageUtils.loadImage(R.drawable.splash_logo),imageSize,null,null);
        initData();
    }

    private void initData() {
        new TextView(mContext).postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo();
            }
        },1000);

    }

    private void redirectTo() {
        // TODO 判断是跳转到登陆界面还是主界面
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
