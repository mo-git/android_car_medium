package cn.bashiquan.cmj.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by mocf on 2017/7/11.
 */
public class FileUtils {

    /**
     * 日志存储目录
     */
    public static String savePath() {
        File sdRoot;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            sdRoot = Environment.getExternalStorageDirectory();
        } else {
            sdRoot = Environment.getDataDirectory();
        }
        return sdRoot.getAbsolutePath();
    }
}
