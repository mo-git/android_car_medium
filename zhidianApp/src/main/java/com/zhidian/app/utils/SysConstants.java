package com.zhidian.app.utils;

/**
 * Created by mocf on 2017/7/11.
 */
public class SysConstants {


    /**
     * Preference key
     */
    public static final String REFRESHLASTTIME = "refreshLastTime"; // listView最后刷新的时间

    /**
     * 日志存储目录
     */
    public static final String DATA_DIR_ROOT = FileUtils.savePath() + "/zhidian";

     // 图片存储目录
    public static final String FILE_DIR_ROOT = DATA_DIR_ROOT + "/.files/";
    public static final String FILE_TEMP_DIR_ROOT = DATA_DIR_ROOT + "/image/";
    public static final String FILE_DIR_VIDEO = DATA_DIR_ROOT + "/video/";
    public static final String FILE_upload_ROOT = DATA_DIR_ROOT + "/.uploads/";
    public static final String FILE_CIRCLE_BG_ROOT = DATA_DIR_ROOT + "/.circlebg/";
    public static final String FILE_DCIM = FileUtils.savePath() + "/DCIM/Camera/";
    public static final String FILE_PICTURES = FileUtils.savePath() + "/Pictures/";
    public static final String FILE_INCREMENT = DATA_DIR_ROOT + "/apk/";
    public static final String FILE_PATCH = DATA_DIR_ROOT + "/patch/";


}
