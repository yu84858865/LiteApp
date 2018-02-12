package first.net.liteapp.utils;


import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

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

}
