package first.net.liteapp.utils;


import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import first.net.liteapp.LiteApplication;

/**
 * Created by 10960 on 2018/2/12.
 */

public class BitmapUtils {

    private static Bitmap bitmap;

    public static Bitmap urlConvertToBitmap(String url){
        Glide.with(LiteApplication.getContext()).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                bitmap = resource;
            }
        });
        return bitmap;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        if(bmp==null)return  null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
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

    public static byte[] resizeBitmap(byte[] pic, int newWidth, int newHeight) {
        if (pic == null) {
            return null;
        }
        Bitmap oldPhoto = BitmapFactory.decodeByteArray(pic, 0, pic.length);
        int oldWidth = oldPhoto.getWidth();
        int oldHeight = oldPhoto.getHeight();
        float scaleWidth = ((float) newWidth) / oldWidth;
        float scaleHeight = ((float) newHeight) / oldHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newPhoto = Bitmap.createBitmap(oldPhoto, 0, 0, oldWidth, oldHeight, matrix, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        newPhoto.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        try {
            baos.close();
            return bytes;
        } catch (IOException e) {
            return null;
        }
    }

    public static Bitmap fromByteArray(byte[] pic) {
        if (pic == null || pic.length == 0) {
            return null;
        }
        Bitmap bm = BitmapFactory.decodeByteArray(pic, 0, pic.length);
        return bm;
    }

}
