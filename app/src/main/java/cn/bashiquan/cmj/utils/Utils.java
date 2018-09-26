package cn.bashiquan.cmj.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by mocf on 2018/9/26.
 */
public class Utils {

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
}
