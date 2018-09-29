package cn.bashiquan.cmj.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

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




    // 创建缓存目录
    public static void creatCashFiles() {
        File fileTuxing = new File(SysConstants.DATA_DIR_ROOT);
        File fileUpload = new File(SysConstants.FILE_upload_ROOT);
        File fileIncrement = new File(SysConstants.FILE_INCREMENT);
        creatCashFile(fileTuxing);
        creatCashFile(fileUpload);
        creatCashFile(fileIncrement);
    }

    public static void creatCashFile(File file) {
        if (file.exists()) {
            if (!file.isDirectory()) {
                deleteFile(file);
                file.mkdirs();
            }
        } else {
            file.mkdirs();
        }
    }

    public static void deleteFile(File file) {
        if(file.isDirectory()){
            for(File child : file.listFiles()){
                deleteFile(child);
            }
            file.delete();
        }else if(file.isFile()){
            file.delete();
        }
    }

    /*******************************压缩图片*******************************/
    /**
     * 根据图片路径进行压缩图片
     *
     * @return
     */
    public static boolean saveBitmap(String imagePath, String bitName, String savePath, int maxSize) {
        Bitmap bitmap = null;
        boolean isSuccess = true;
        Double fileSize = getFileSize(new File(imagePath));
        try {
            if (fileSize < 0.2) {
                bitmap = BitmapFactory.decodeFile(imagePath);
            } else {
                BitmapFactory.Options newOpts = new BitmapFactory.Options();
                //开始读入图片，此时把options.inJustDecodeBounds 设回true了,表示只返回宽高
                newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
                newOpts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imagePath, newOpts);//此时返回bm为空
                int longSide = 1280;
                int shortSide = 960;
                int opWidth = newOpts.outWidth;
                int opHeight = newOpts.outHeight;
                int width = 0, height = 0;

                if (opWidth <= opHeight) {
                    height = opHeight > longSide ? longSide : opHeight;
                    width = height * opWidth / opHeight;
                } else {
                    width = opWidth > shortSide ? shortSide : opWidth;
                    height = width * opHeight / opWidth;
                }
                newOpts.inSampleSize = calculateInSampleSize(newOpts, width, height);
                newOpts.inJustDecodeBounds = false;

                bitmap = BitmapFactory.decodeFile(imagePath, newOpts);
                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                int degree = getImageSpinAngle(imagePath);
                if (bitmap != null && degree > 0) {
                    bitmap = rotaingImageView(degree, bitmap);
                }
            }
            isSuccess = compressImage(bitmap, bitName, savePath, maxSize);//压缩好比例大小后再进行质量压缩
        } catch (Exception e) {
            isSuccess = false;
//            MyLog.getLogger("Utils").d("压缩图片失败 e= " + e.toString());
        } catch (OutOfMemoryError e) {
            compressImage(bitmap, bitName, savePath, maxSize);
//            MyLog.getLogger("Utils").d("压缩图片失败OOM e= " + e.toString());
        }
        return isSuccess;
    }

    /**
     * 计算像素压缩的缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    /**
     * 压缩图片
     *
     * @param image
     * @param size
     * @return
     */
    private static boolean compressImage(Bitmap image, String bitName, String savePath, int size) {
        boolean isSuccess = true;
        try {
            if (image != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
                int options = 100;

                while ((baos.toByteArray().length / 1024) > size && options > 20) {  //循环判断如果压缩后图片是否大于等于size,大于等于继续压缩
                    baos.reset();//重置baos即清空baos
                    options -= 5;//每次都减少5
                    image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                }

                if (baos.toByteArray().length > 0) {
                    File foder = new File(savePath);
                    if (!foder.exists()) {
                        foder.mkdirs();
                    }

                    File myCaptureFile = new File(savePath, bitName);
                    if (!myCaptureFile.exists()) {
                        myCaptureFile.createNewFile();
                    }

                    FileOutputStream fos = new FileOutputStream(myCaptureFile);
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();
                } else {
                    isSuccess = false;
                }
            } else {
                isSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }


    /**
     * 旋转图片
     */

    public static int getImageSpinAngle(String imagePath) {
        int imageDegree = 0;
        if (!TextUtils.isEmpty(imagePath)) {

            try {
                ExifInterface exifInterface = new ExifInterface(imagePath);
                int orientation = exifInterface.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        imageDegree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        imageDegree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        imageDegree = 270;
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageDegree;
    }

    // 然后将图片旋转回去

    public static Bitmap rotaingImageView(int angle, Bitmap mBitmap) {
        if (angle > 0 && mBitmap != null) {
            Matrix matrix = new Matrix();
            matrix.setRotate(angle);
            Bitmap rotateBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
                    mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
            if (rotateBitmap != null) {
                mBitmap.recycle();
                mBitmap = rotateBitmap;
            }
        }
        return mBitmap;
    }

    public static Double getFileSize(File f) {
        double size = 0;
        FileInputStream fis = null;
        try {
            if (f.exists()) {
                fis = new FileInputStream(f);
                size = ((double) fis.available() / 1048576);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return size;
    }

}
