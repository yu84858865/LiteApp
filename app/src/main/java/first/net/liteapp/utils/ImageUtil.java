package first.net.liteapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtil {


    /**
     * bitmap转成字节数组
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bitmapToByteArray(final Bitmap bmp,
                                           final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 根据data获取图片路径
     *
     * @param context
     * @param data
     * @param
     * @return
     */
    public static String getPicPathByData(Context context, Intent data) {
        Uri uri = data.getData();
        System.out.println("uri->" + uri);
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) context).managedQuery(uri, proj, null,
                null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    /**
     * 通过文件获取图片
     *
     * @param context
     * @param path
     * @return
     */
//    public static Bitmap getBitmapByFile(Context context, String path) {
//        if (FileUtil.isSDCardExist() && FileUtil.isHasSDCardPermission(context)
//                && context != null && !StringUtil.isBlank(path)) {
//            InputStream is = null;
//            Bitmap bitmap = null;
//            try {
//                is = new FileInputStream(new File(path));
//                if (is != null) {
//                    BitmapFactory.Options opts = new BitmapFactory.Options();
//                    opts.inTempStorage = new byte[100 * 1024];
//                    opts.inPreferredConfig = Bitmap.Config.RGB_565;
//                    opts.inPurgeable = true;
//                    opts.inSampleSize = 4;
//                    opts.inInputShareable = true;
//                    bitmap = BitmapFactory.decodeStream(is, null, opts);
//                    return bitmap;
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                if (is != null) {
//                    try {
//                        is.close();
//                        is = null;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return null;
//    }
    //  既然问题是图片的方向，我们就可以先获取到图片的方向角度，

    /**
     * @param path 图片路径
     * @return 角度
     * @brief 读取图片方向信息
     */
    public int readPhotoDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将bitmap位图保存到path路径下
     *
     * @param bitmap
     * @param path    保存路径
     * @param format  格式-Bitmap.CompressFormat.PNG或Bitmap.CompressFormat.JPEG.PNG
     * @param quality Hint to the compressor, 0-100. 0 meaning compress for small
     *                size, 100 meaning compress for max quality. Some formats, like
     *                PNG which is lossless, will ignore the quality setting
     * @return
     */

//    public static boolean saveBitmapToLocation(Context context, Bitmap bitmap,
//                                               String path, CompressFormat format, int quality) {
//        if (FileUtil.isSDCardExist() && FileUtil.isHasSDCardPermission(context)
//                && FileUtil.isHasAvailableMemory() && bitmap != null
//                && !StringUtil.isBlank(path) && format != null
//                && (quality >= 0 && quality <= 100)) {
//            try {
//                File file = new File(path);
//                File parent = file.getParentFile();
//                if (!parent.exists()) {
//                    parent.mkdirs();
//                }
//                FileOutputStream fos = new FileOutputStream(file);
//                boolean b = bitmap.compress(format, quality, fos);
//                fos.flush();
//                fos.close();
//                return b;
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }

    /**
     * 设置图片的长宽为3:2
     */
    public static ImageView setImagWidthAndHeight(Context mcx, ImageView img) {
        ViewGroup.LayoutParams lp = img.getLayoutParams();
        lp.height = mcx.getResources().getDisplayMetrics().widthPixels / 3 * 2;
        lp.width = mcx.getResources().getDisplayMetrics().widthPixels;
        img.setLayoutParams(lp);
        return img;
    }
    /**
     * 设置图片的长宽为3:2
     */
    public static View setWidthAndHeightHalf(Context mcx, View img) {
        ViewGroup.LayoutParams lp = img.getLayoutParams();
        lp.height = mcx.getResources().getDisplayMetrics().widthPixels / 16*9;
        lp.width = mcx.getResources().getDisplayMetrics().widthPixels;
        img.setLayoutParams(lp);
        return img;
    }
    /**
     * 设置图片的长宽为3:2
     */
    public static View setImagWidthAndHeight(Context mcx, View img, int dp) {
        ViewGroup.LayoutParams lp = img.getLayoutParams();
        lp.height = (mcx.getResources().getDisplayMetrics().widthPixels - DataTools.dip2px(mcx, 2 * dp)) / 3 * 2;
        lp.width = mcx.getResources().getDisplayMetrics().widthPixels - DataTools.dip2px(mcx, 2 * dp);
        img.setLayoutParams(lp);
        return img;
    }

    /**
     * 设置图片的长宽为3:2
     */
    public static View setImagWidthAndHeight(Context mcx, View img, int dp, int column) {
        ViewGroup.LayoutParams lp = img.getLayoutParams();
        lp.height = (mcx.getResources().getDisplayMetrics().widthPixels - DataTools.dip2px(mcx, 2 * dp)) / column / 3 * 2;
        lp.width = mcx.getResources().getDisplayMetrics().widthPixels - DataTools.dip2px(mcx, 2 * dp) / column;
        img.setLayoutParams(lp);
        return img;
    }

    /**
     * 设置图片的长宽为
     */
    public static View setViewWidthAndHeight(Context mcx, LinearLayout view, int dp, int column) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = (mcx.getResources().getDisplayMetrics().widthPixels - DataTools.dip2px(mcx, 2 * dp)) / column;
        lp.width = mcx.getResources().getDisplayMetrics().widthPixels - DataTools.dip2px(mcx, 2 * dp) / column;
        view.setLayoutParams(lp);
        return view;
    }

    public static View setViewWidthAndHight(View img, int width, int height) {
        ViewGroup.LayoutParams lp = img.getLayoutParams();
        lp.height = height;
        lp.width = width;
        img.setLayoutParams(lp);
        return img;
    }

    /**
     * 根据分辨率压缩图片
     */
    public static void setImgCompressByDip(String pathName, String toPath, int reqWidth) {
        saveBitmap2file(decodeSampledBitmapFromResource(pathName, reqWidth), pathName);
    }

    // 图片转为文件
    public static boolean saveBitmap2file(Bitmap bmp, String pathName) {
        CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(pathName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bmp.compress(format, quality, stream);
    }

    public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
        // 源图片的宽度
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            // 计算出实际宽度和目标宽度的比率
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 缩放，任意比
     */
    public static Bitmap scaleXAndY(Bitmap bitmap, float scaleX, float scaleY) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 缩放，等比
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float scale) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }
}
