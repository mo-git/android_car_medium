package cn.bashiquan.cmj.sdk.http;

import android.content.Context;
import android.os.Build;
import com.google.gson.Gson;

import cn.bashiquan.cmj.sdk.event.login.UnauthenticatedEvent;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.sdk.utils.SPUtils;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * Created by mocf on 2018/8/27.
 */
public class HttpClient {
    private final static Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private final static String CONTENT_TYPE = "application/json; charset=utf-8";



    private static final int HTTP_STATUS_OK = 200;

    private static final int CONNECT_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 15;

    private static HttpClient instance;

    private OkHttpClient client;
    private String hostUrl;
    private String host;
    private int port;
    private volatile String token;
    private Context context;
    private Gson mGson;
    private String apiVersion;

    /**
     * appGateway地址
     */
    private final String GROW_GATE_NAME = "app_gateway";
    public String mAppGatewayHostUrl;

    public synchronized static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }

        return instance;
    }

    private HttpClient() {
        mGson = new Gson();

        client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }



    public void init(Context context, String version) {
        this.context = context;

        apiVersion = version;
    }


    public void setHostAddress(String host, int port) {
        this.host = host;
        this.port = port;
            hostUrl = host;
    }


    public String buildPbRequest(BaseRequest request) {

        String jsonString = mGson.toJson(request);
        return jsonString;
    }


    // get请求无参数
    public void sendGetRequest(final String url, final RequestCallback callback) {
        final String requestUrl = hostUrl + url;

        final Request httpRequest = new Request.Builder()
                .url(requestUrl)
                .addHeader("Cookie",(String) SPUtils.get(context.getApplicationContext(),Constants.SP_LOGINTOKEN,""))
                .build();

        client.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ResponseError error = new ResponseError(requestUrl, 500, Constants.MSG_CANNOT_CONNECT_TO_SERVER);

                if (callback != null) {
                    callback.onFailure(error);
                }
            }

            @Override
            public void onResponse(Call call, Response httpResponse) throws IOException {
                if (httpResponse.code() != HTTP_STATUS_OK) {
                    if (callback != null) {
                        if(httpResponse.code() == 401){
                            if(url.contains("/user/appLogin?unionid=")){
                                EventBus.getDefault().post(new UnauthenticatedEvent(UnauthenticatedEvent.EventType.LOIGN_SUCCESS));
                            }else{
                                EventBus.getDefault().post(new UnauthenticatedEvent(UnauthenticatedEvent.EventType.LOGIN_NO_SUCCESS));
                            }

                        }else{
                            callback.onFailure(new ResponseError(requestUrl, httpResponse.code(),"网络连接失败，请稍后再试"));
                        }
                    }
                } else {
                    try {

                        if (callback != null) {
                            if (httpResponse.code() == Constants.RESPONSE_STATUS.OK) {
                                try {
                                    if(httpResponse.body() != null){
                                        callback.onResponse(httpResponse.body().string());
                                    }else{
                                        callback.onResponse(null);
                                    }
                                } catch (Exception e) {
                                    callback.onFailure(new ResponseError(requestUrl, 500,e.toString()));
                                }
                            } else {
                                callback.onFailure(new ResponseError(requestUrl, httpResponse.code(), httpResponse.message()));
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Process http response error", e);
                        if (callback != null) {
                            callback.onFailure(new ResponseError(requestUrl, 500,Constants.MSG_CANNOT_CONNECT_TO_SERVER));
                        }
                    }
                }

            }
        });
    }

    // 微信登陆
    public void sendGetRequestWX(final String url, final RequestCallback callback) {

        final Request httpRequest = new Request.Builder()
                .url(url)
                .build();

        client.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ResponseError error = new ResponseError(url, 500, Constants.MSG_CANNOT_CONNECT_TO_SERVER);

                if (callback != null) {
                    callback.onFailure(error);
                }
            }

            @Override
            public void onResponse(Call call, Response httpResponse) throws IOException {
                if (httpResponse.code() != HTTP_STATUS_OK) {
                    if (callback != null) {
                        callback.onFailure(new ResponseError(url, httpResponse.code(),"网络连接失败，请稍后再试"));
                    }
                } else {
                    try {

                        if (callback != null) {
                            if (httpResponse.code() == Constants.RESPONSE_STATUS.OK) {
                                try {
                                    if(httpResponse.body() != null){
                                        callback.onResponse(httpResponse.body().string());
                                    }else{
                                        callback.onResponse(null);
                                    }
                                } catch (Exception e) {
                                    callback.onFailure(new ResponseError(url, 500,e.toString()));
                                }
                            } else {
                                callback.onFailure(new ResponseError(url, httpResponse.code(), httpResponse.message()));
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Process http response error", e);
                        if (callback != null) {
                            callback.onFailure(new ResponseError(url, 500,Constants.MSG_CANNOT_CONNECT_TO_SERVER));
                        }
                    }
                }

            }
        });
    }


    /**
     * @param url
     * @param callback
     */
    public void sendPostRequest(final String url, BaseRequest request, final RequestCallback callback) {
        final String requestUrl = hostUrl + url;
        String requestJson = buildPbRequest(request);
        RequestBody requestBody = RequestBody.create( MediaType.parse(CONTENT_TYPE), requestJson);
        final Request httpRequest = new Request.Builder()
                .url(requestUrl)
                .post(requestBody)
                .addHeader("Cookie",(String) SPUtils.get(context.getApplicationContext(),Constants.SP_LOGINTOKEN,""))
                .build();

        client.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                ResponseError error = new ResponseError(requestUrl, 500, Constants.MSG_CANNOT_CONNECT_TO_SERVER);

                if (callback != null) {
                    callback.onFailure(error);
                }
            }

            @Override
            public void onResponse(Call call, Response httpResponse) throws IOException {
                switch (httpResponse.code()) {
                    case HTTP_STATUS_OK:
                        try {

                            if (callback != null) {
                                if (httpResponse.code() == Constants.RESPONSE_STATUS.OK) {
                                    try {
                                        if (httpResponse.body() != null) {
                                            callback.onResponse(httpResponse.body().string());
                                        } else {
                                            callback.onResponse(null);
                                        }
                                    } catch (Exception e) {
                                        callback.onFailure(new ResponseError(requestUrl, 500, e.toString()));
                                    }
                                } else {
                                    callback.onFailure(new ResponseError(requestUrl, httpResponse.code(), httpResponse.message()));
                                }
                            }
                        } catch (Exception e) {

                            if (callback != null) {
                                callback.onFailure(new ResponseError(requestUrl, 500, Constants.MSG_CANNOT_CONNECT_TO_SERVER));
                            }
                        }
                        break;
                    default:
                        if (callback != null) {
                            callback.onFailure(new ResponseError(requestUrl, httpResponse.code(), "网络连接失败，请稍后再试"));
                        }
                        break;
                }

            }
        });
    }

    // 上传图片
    public void uplodeImage(String url,String filePath,String imageName, final RequestCallback callback) {
        final String requestUrl = hostUrl + url;
        File file = new File(filePath + imageName);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageName, RequestBody.create(MediaType.parse("image/png"), file))
                .build();


        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("Cookie",(String) SPUtils.get(context.getApplicationContext(),Constants.SP_LOGINTOKEN,""))
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                ResponseError error = new ResponseError(requestUrl, 500, Constants.MSG_CANNOT_CONNECT_TO_SERVER);

                if (callback != null) {
                    callback.onFailure(error);
                }
            }

            @Override
            public void onResponse(Call call, Response httpResponse) throws IOException {
                switch (httpResponse.code()) {
                    case HTTP_STATUS_OK:
                        try {
                            if (callback != null) {
                                if (httpResponse.code() == Constants.RESPONSE_STATUS.OK) {
                                    try {
                                        if (httpResponse.body() != null) {
                                            callback.onResponse(httpResponse.body().string());
                                        } else {
                                            callback.onResponse(null);
                                        }
                                    } catch (Exception e) {
                                        callback.onFailure(new ResponseError(requestUrl, 500, e.toString()));
                                    }
                                } else {
                                    callback.onFailure(new ResponseError(requestUrl, httpResponse.code(), httpResponse.message()));
                                }
                            }
                        } catch (Exception e) {
                            logger.error("Process http response error", e);

                            if (callback != null) {
                                callback.onFailure(new ResponseError(requestUrl, 500, Constants.MSG_CANNOT_CONNECT_TO_SERVER));
                            }
                        }
                        break;
                    default:
                        if (callback != null) {
                            callback.onFailure(new ResponseError(requestUrl, httpResponse.code(), "网络连接失败，请稍后再试"));
                        }
                        break;
                }

            }
        });
    }
    public String getToken() {
        return token;
    }



}
