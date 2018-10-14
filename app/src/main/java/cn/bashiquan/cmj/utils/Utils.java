package cn.bashiquan.cmj.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Locale;

import cn.bashiquan.cmj.MyApplication;
import cn.bashiquan.cmj.sdk.bean.WXTokenBean;
import cn.bashiquan.cmj.sdk.utils.Constants;
import cn.bashiquan.cmj.sdk.utils.SPUtils;

/**
 * Created by mocf on 2018/9/26.
 */
public class Utils {

    public static String carnumRegex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";


    /**
     * 获取版本name
     */

    public static String getVersionName(Context activity) {
        PackageManager manager = activity.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(
                    activity.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    // 获取显示区域的宽度
    public static int getEditWidth(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        int width = outRect1.width();
        return width;
    }

    /**
     * dp转px
     */
    public static int dp2px(Context ctx, float dp) {
        float density = ctx.getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5f);// 4.9->5 4.4->4
        return px;
    }

    public static float px2dp(Context ctx, int px) {
        float density = ctx.getResources().getDisplayMetrics().density;
        float dp = px / density;

        return dp;
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void closeKeybord(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    // 车牌号码规则
    public static boolean isCarnumberNO(String carnumber) {
        boolean flag = false;
        if (!TextUtils.isEmpty(carnumber)){
            flag =  carnumber.matches(carnumRegex);
        }
        return flag;
    }

    /**
     * 手机号的正则
     *
     * @param cellphone
     * @return
     */
    public static boolean isCellphone(String cellphone) {
        String reg = "^1\\d{10}$";
        return cellphone.matches(reg);
    }

    // 调用微信登陆
    public static void weiXinLogin(Context mContext){
        if (MyApplication.mWxApi != null && MyApplication.mWxApi.isWXAppInstalled()) {
            //微信登录
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "diandi_wx_login";
            //像微信发送请求
            MyApplication.mWxApi.sendReq(req);
        }else{
            Toast.makeText(mContext,"您还未安装微信客户端",Toast.LENGTH_SHORT).show();
        }
    }

    // 微信是否授权 0 未授权  1 未登陆  2 已登陆
    public static int isAuthorizeLogin(Context mContext){
        int flag = 0;
        String wxToken = (String)SPUtils.get(mContext, Constants.SP_WXTOKEN,"");
        String loginToken = (String)SPUtils.get(mContext, Constants.SP_LOGINTOKEN,"");
        if(TextUtils.isEmpty(wxToken)){
            flag = 0;
        }else if(TextUtils.isEmpty(loginToken)){
            flag = 1;
        }else{
            flag = 3;
        }
        return flag;
    }





}
