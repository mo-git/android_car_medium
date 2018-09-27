package cn.bashiquan.cmj.sdk.http;

import android.content.Context;
import android.os.Build;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import cn.bashiquan.cmj.sdk.utils.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alan on 2015/5/19.
 */
public class HttpClient {
    private final static Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private final static String HTTP_DNS_URL = "http://119.29.29.29/d?dn=";
    private final static String CONTENT_TYPE = "application/x-protobuf; charset=utf-8";



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
        client = new OkHttpClient();
        client.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        client.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        client.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
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




    public String buildPbRequest(String url, BaseRequest request) {


        request.url = url;
        request.token = token == null ? "" : token;
        request.version = apiVersion;
        request.osName = Constants.OS_NAME;
        request.osVersion = Build.VERSION.RELEASE;
        String jsonString = mGson.toJson(request);
        return jsonString;
    }

    public void sendGetRequest(final String url, String requestString, final RequestCallback callback) {
//        final String requestJson = buildPbRequest(url, request);
        String requestUrl = hostUrl + url;
        final Request httpRequest = new Request.Builder()
                .url(requestUrl)

                .build();

        client.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Request httpRequest, IOException e) {
                logger.error("Send request failed!", e);

                ResponseError error = new ResponseError(url, 500, Constants.MSG_CANNOT_CONNECT_TO_SERVER);

                if (callback != null) {
                    callback.onFailure(error);
                }
            }

            @Override
            public void onResponse(Response httpResponse) throws IOException {
                logger.debug("Url: {}, Response status: {}, message: {}", url, httpResponse.code(), httpResponse.message());
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
                                    logger.error("Error when handle callback", e);
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

    public void sendGetRequest(final String url, final RequestCallback callback) {
        String requestUrl = hostUrl + url;

        final Request httpRequest = new Request.Builder()
                .url(requestUrl)
                .build();

        client.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Request httpRequest, IOException e) {
                ResponseError error = new ResponseError(url, 500, Constants.MSG_CANNOT_CONNECT_TO_SERVER);

                if (callback != null) {
                    callback.onFailure(error);
                }
            }

            @Override
            public void onResponse(Response httpResponse) throws IOException {
                logger.debug("Url: {}, Response status: {}, message: {}", url, httpResponse.code(), httpResponse.message());
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
     * @param hosturl
     * @param url
     * @param callback
     */
    public void sendPostRequest(String hosturl, final String url, BaseRequest request, final RequestCallback callback) {
        logger.debug("Send request: {}", url);
        String requestJson = buildPbRequest(url, request);
        RequestBody requestBody = RequestBody.create( MediaType.parse(CONTENT_TYPE), requestJson);
        final Request httpRequest = new Request.Builder()
                .url(hosturl)
                .post(requestBody)
                .build();

        client.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Request httpRequest, IOException e) {
                logger.error("Send request failed!", e);

                ResponseError error = new ResponseError(url, 500, Constants.MSG_CANNOT_CONNECT_TO_SERVER);

                if (callback != null) {
                    callback.onFailure(error);
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                logger.debug("Url: {}, Response status: {}, message: {}", url, response.code(), response.message());
                switch (response.code()) {
                    case HTTP_STATUS_OK:
                        try {

                            if (callback != null) {
                                if (response.code() == Constants.RESPONSE_STATUS.OK) {
                                    try {
                                        if (response.body() != null) {
                                            callback.onResponse(response.body().toString());
                                        } else {
                                            callback.onResponse(null);
                                        }
                                    } catch (Exception e) {
                                        logger.error("Error when handle callback", e);
                                        callback.onFailure(new ResponseError(url, 500, e.toString()));
                                    }
                                } else {
                                    callback.onFailure(new ResponseError(url, response.code(), response.message()));
                                }
                            }
                        } catch (Exception e) {
                            logger.error("Process http response error", e);

                            if (callback != null) {
                                callback.onFailure(new ResponseError(url, 500, Constants.MSG_CANNOT_CONNECT_TO_SERVER));
                            }
                        }
                        break;
                    default:
                        if (callback != null) {
                            callback.onFailure(new ResponseError(url, response.code(), "网络连接失败，请稍后再试"));
                        }
                        break;
                }

            }
        });
    }

    public byte[] sendRequest(String url,  BaseRequest request) throws IOException, ResponseError {
        logger.debug("Send request: {}", url);
        String jsonRequest = buildPbRequest(url, request);
        RequestBody requestBody = RequestBody.create(MediaType.parse(CONTENT_TYPE), jsonRequest);
        final Request httpRequest = new Request.Builder()
                .url(hostUrl)
                .post(requestBody)
                .build();

        Response httpResponse = client.newCall(httpRequest).execute();

        logger.debug("Url: {}, Response status: {}, message: {}",
                url,
                httpResponse.code(),
                httpResponse.message());

        if (httpResponse.code() != HTTP_STATUS_OK) {
            throw new ResponseError(url, httpResponse.code(), httpResponse.message());
        }

        logger.debug("Request url: {}, Response status: {}, msg: {}",
                url,
                httpResponse.code(),
                httpResponse.message());

        if (httpResponse.code() == Constants.RESPONSE_STATUS.OK) {
            return httpResponse.body().bytes();
        }

        return null;
    }
    public String getToken() {
        return token;
    }



}
