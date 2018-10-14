package cn.bashiquan.cmj.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import cn.bashiquan.cmj.MainActivity;
import cn.bashiquan.cmj.MyApplication;
import cn.bashiquan.cmj.sdk.bean.WXTokenBean;
import cn.bashiquan.cmj.sdk.bean.WXUserBean;
import cn.bashiquan.cmj.sdk.event.HomeManagerEvent.WXEvent;
import cn.bashiquan.cmj.sdk.service.CoreService;
import de.greenrobot.event.EventBus;

/**
 *  微信登录注册
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean result = MyApplication.getApplication().getWxApi().handleIntent(getIntent(), this);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        try {
            if(!result){
                Toast.makeText(this,"参数不合法",Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            //ERR_AUTH_DENIED
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this, "拒绝授权", Toast.LENGTH_SHORT).show();
                finish();
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this, "取消授权", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                //拿到了微信返回的code,立马再去请求access_token
                String code = ((SendAuth.Resp) baseResp).code;
                CoreService.getInstance().getLoginManager("WXEntryActivity").getAccess_token(code);
                break;

            default:
                break;
        }
    }

    public void onEventMainThread(WXEvent event){
        finish();
//        switch (event.getEventType()){
//            case GET_WX_TOKEN_SUCCESS:
//                WXTokenBean wxTokenBean = event.getwTokenBean();
//                finish();
//                break;
//            case GET_WX_TOKEN_FAILED:
//                Toast.makeText(this,event.getMsg(),Toast.LENGTH_SHORT).show();
//                finish();
//                break;
//            case GET_WX_USER_SUCCESS:
//                finish();
//                break;
//            case GET_WX_USER_FAILED:
//                Toast.makeText(this,event.getMsg(),Toast.LENGTH_SHORT).show();
//                finish();
//                break;
//        }
    }

    @Override
    protected void onDestroy() {
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();

    }

}