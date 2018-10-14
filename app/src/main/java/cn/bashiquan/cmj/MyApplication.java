package cn.bashiquan.cmj;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bashiquan.cmj.sdk.bean.UserBean;
import cn.bashiquan.cmj.sdk.bean.WXTokenBean;
import cn.bashiquan.cmj.sdk.bean.WXUserBean;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.http.RequestCallback;
import cn.bashiquan.cmj.sdk.http.RequestUrl;
import cn.bashiquan.cmj.sdk.service.CoreService;
import cn.bashiquan.cmj.sdk.utils.Constants;

import java.io.File;
import java.io.IOException;

import cn.bashiquan.cmj.sdk.utils.SPUtils;
import cn.bashiquan.cmj.utils.SysConstants;
import cn.bashiquan.cmj.utils.Utils;
import de.greenrobot.event.EventBus;

/**
 * Created by mocf on 2018/9/26
 */
public class MyApplication extends Application {

    private Context mContext;
    public static int VersionCode = 0;
    public static IWXAPI mWxApi;
    private static MyApplication instance;
    public static UserBean userBean;
    public static WXTokenBean wxTokenBean;
    public static WXUserBean wxUserBean;
    public static MyApplication getApplication(){
        if(instance == null){
            instance = new MyApplication();
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initCoreService();
        initImageLoader();
        registerToWX();
//        getToken();
    }

    public Context getContext(){
        return this;

    }

    private void initCoreService() {
        String versionName = "zhidian_" + Utils.getVersionName(this);
        CoreService.getInstance().start(this.getBaseContext(), versionName);
        CoreService.getInstance().initHost(this.getBaseContext(), versionName, Constants.SERVER_ADDR, Constants.SERVER_PORT);
    }

    private void initImageLoader() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
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

    private void registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp( Constants.WEIXIN_APP_ID);
    }

    public IWXAPI getWxApi(){
        return mWxApi;
    }

    // 临时获取token
    public void getToken(){
        String url = "/user/login1";
        HttpClient.getInstance().sendGetRequest(url, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject dataJson = jsonObject.getJSONObject("data");
                    String token = dataJson.getString("token");
                    String userId = dataJson.getString("user_id");
                    SPUtils.put(getApplicationContext(), Constants.SP_LOGINTOKEN,"cmj_session=" + token);
//                    CoreService.getInstance().getLoginManager("userInfo").getUserInfo();
                    getUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });
    }

    public void getUserInfo() {
        HttpClient.getInstance().sendGetRequest(RequestUrl.USERINFO_URL, new RequestCallback() {
            @Override
            public void onResponse(String data) throws IOException {
                Gson mGson = new Gson();
                userBean = mGson.fromJson(data,UserBean.class);
            }

            @Override
            public void onFailure(Throwable cause) {
            }
        });
    }

}
