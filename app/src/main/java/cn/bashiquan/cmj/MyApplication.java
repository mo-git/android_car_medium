package cn.bashiquan.cmj;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import cn.bashiquan.cmj.sdk.service.CoreService;
import cn.bashiquan.cmj.sdk.utils.Constants;

import java.io.File;

import cn.bashiquan.cmj.utils.SysConstants;
import cn.bashiquan.cmj.utils.Utils;

/**
 * Created by mocf on 2018/9/26
 */
public class MyApplication extends Application {

    private Context mContext;
    public static int VersionCode = 0;
    private static MyApplication instance;
    public static MyApplication getApplication(){
        if(instance == null){
            instance = new MyApplication();
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initCoreService();
        initImageLoader();
    }

    private void initCoreService() {
        String versionName = "zhidian_" + Utils.getVersionName(this);
        CoreService.getInstance().start(this.getBaseContext(), versionName);
        CoreService.getInstance().initHost(this.getBaseContext(), versionName, Constants.SERVER_ADDR, Constants.SERVER_PORT);
    }

    private void initImageLoader() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(mContext);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.diskCache(new UnlimitedDiskCache(new File(SysConstants.DATA_DIR_ROOT, ".files"), null, new Md5FileNameGenerator()));
        config.memoryCache(new LRULimitedMemoryCache(10 * 1024 * 1024));
        config.memoryCacheExtraOptions(480, 800);
        config.imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000));
//		config.writeDebugLogs(); // 是否后台输出日志

        ImageLoader.getInstance().init(config.build());
    }

}
