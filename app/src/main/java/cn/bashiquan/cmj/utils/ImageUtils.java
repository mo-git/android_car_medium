package cn.bashiquan.cmj.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * Created by mocf on 2018/9/26.
 */
public class ImageUtils {
    private static DisplayImageOptions DIO_LOAD_IMAGE;

    /**
     * loadingImgeId 和 faildImaeId 不一样
     * @param loadingImageId
     * @param faildImageId
     * @return
     */
    public static DisplayImageOptions loadImage(int loadingImageId,int faildImageId) {
        DIO_LOAD_IMAGE = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadingImageId)
                .showImageForEmptyUri(faildImageId)
                .showImageOnFail(faildImageId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return DIO_LOAD_IMAGE;
    }


    /**
     * loadingImgeId 和 faildImaeId 一样
     * @return
     */
    public static DisplayImageOptions loadImage(int defalImageId) {
        DIO_LOAD_IMAGE = new DisplayImageOptions.Builder()
                .showImageOnLoading(defalImageId)
                .showImageForEmptyUri(defalImageId)
                .showImageOnFail(defalImageId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        return DIO_LOAD_IMAGE;
    }

    /**
     * 带圆角
     * @return
     */
    public static DisplayImageOptions loadRoundImagePic(int defalImageId,int round) {
        DIO_LOAD_IMAGE = new DisplayImageOptions.Builder()
                .showImageOnLoading(defalImageId)
                .showImageForEmptyUri(defalImageId)
                .showImageOnFail(defalImageId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(round))
                .build();
        return DIO_LOAD_IMAGE;
    }
}
