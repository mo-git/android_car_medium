package cn.bashiquan.cmj.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONObject;

import java.io.IOException;

import cn.bashiquan.cmj.MyApplication;
import cn.bashiquan.cmj.sdk.utils.Constants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 * <p>
 * 03 微信登录注册
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        MyApplication.getApplication().getWxApi().handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp baseResp) {
        Toast.makeText(this , "执行了 onResp" ,Toast.LENGTH_SHORT).show();
        switch (baseResp.errCode) {
            //ERR_AUTH_DENIED
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this , "用户拒绝" ,Toast.LENGTH_SHORT).show();
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this , "用户取消" ,Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_OK:
                //拿到了微信返回的code,立马再去请求access_token
                String code = ((SendAuth.Resp) baseResp).code;
                Toast.makeText(this , "成功" ,Toast.LENGTH_SHORT).show();
                //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯 获取参数 ，注意是get请求

                getAccess_token(code);
                break;

            default:
                break;
        }

    }


    private void getAccess_token(final String code) {

        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + MyApplication.mWxApi
                + "&secret="
                + Constants.APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";
        //1,创建OKHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //2,创建一个Request
        Request request = new Request.Builder().url(path).build();
        //3,创建一个call对象
        Call call = mOkHttpClient.newCall(request);
        //4,将请求添加到调度中
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                response.optString( "openid")
            }
        });





    }



}
