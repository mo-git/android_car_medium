package cn.bashiquan.cmj.sdk.service;

import android.content.Context;
import cn.bashiquan.cmj.sdk.http.HttpClient;
import cn.bashiquan.cmj.sdk.manager.HomeManager;
import cn.bashiquan.cmj.sdk.manager.LoginManager;
import cn.bashiquan.cmj.sdk.manager.iml.HomeManagerIml;
import cn.bashiquan.cmj.sdk.manager.iml.LoginManagerIml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mocf on 2018/9/26.
 */
public class CoreService {
    private final static Logger logger = LoggerFactory.getLogger(CoreService.class);
    private Context mContext;
    private static CoreService instance;
    private CoreService(){};
    public static CoreService getInstance(){
        if(instance == null){
            instance = new CoreService();
        }
        return instance;
    }

    private HttpClient httpClient = HttpClient.getInstance();
    private LoginManager loginManager = LoginManagerIml.getInstance();
    private HomeManager homeManager = HomeManagerIml.getInstance();

    public void start(Context context, String version) {
        this.mContext= context;
        initComponents();
    }

    public void initHost(Context context, String version, String host, int port) {
        this.mContext = context;
        httpClient.init(this.mContext, version);
        httpClient.setHostAddress(host, port);
    }

    //初始化manager
    public void initComponents(){
        loginManager.init(mContext);
        homeManager.init(mContext);
    }

    public LoginManager getLoginManager(String className){
        loginManager.setClassName(className);
        return loginManager;
    }

    public HomeManager getHomeManager(String className){
        homeManager.setClassName(className);
        return homeManager;
    }


}
