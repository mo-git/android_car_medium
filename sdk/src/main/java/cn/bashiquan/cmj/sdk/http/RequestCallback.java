package cn.bashiquan.cmj.sdk.http;


import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Alan on 2015/5/19.
 */
public interface RequestCallback {

    public abstract void onResponse(String data) throws IOException, JSONException;

    public abstract void onFailure(Throwable cause);
}
