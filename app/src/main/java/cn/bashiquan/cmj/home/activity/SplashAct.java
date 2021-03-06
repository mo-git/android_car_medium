package cn.bashiquan.cmj.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import cn.bashiquan.cmj.R;

import cn.bashiquan.cmj.MainActivity;
import cn.bashiquan.cmj.base.BaseAct;
import cn.bashiquan.cmj.sdk.bean.UserBean;
import cn.bashiquan.cmj.utils.ImageUtils;

/**
 * Created by mocf on 2018/9/26
 */
public class SplashAct extends BaseAct {

    private String TAG = SplashAct.class.getSimpleName();
    View view;
    private UserBean currentUser;
    @Override
    public int contentView() {
        // 测试11111111111111111111
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
        ImageLoader.getInstance().displayImage(imageUri,ImageUtils.getImageViewAware(splash_logo), ImageUtils.loadImage(R.drawable.splash_logo),imageSize,null,null);

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
        if(currentUser == null){
            finish();

        }else{
            startAct(MainActivity.class,null);
            finish();
        }
    }
}
