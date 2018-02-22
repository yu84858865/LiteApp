package first.net.liteapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;

import first.net.liteapp.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by 10960 on 2018/2/22.
 */

public class GlideUtils {

    /**
     * 普通加载图片,用其他图片作为缩略图
     * */
    public static void displayNormalImgByOther(Context context, String imgUrl, ImageView iv){
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(context)
                .load(R.mipmap.ic_launcher);
        Glide.with(context)
                .load(imgUrl)
                .thumbnail(thumbnailRequest)
                .into(iv);
    }

    /**
     * 普通加载图片,用其他图片作为缩略图
     * */
    public static void displayNormalImgByOneself(Context context, String imgUrl, ImageView iv){
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(context)
                .load(R.mipmap.ic_launcher);
        Glide.with(context)
                .load(imgUrl)
                .thumbnail(0.1f)
                .into(iv);
    }

    public static void diaplayTransforImg(Context context, String imgUrl, ImageView iv,int flag){
        if(flag == 0x01){//圆形裁剪
            Glide.with(context)
                    .load(imgUrl)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(iv);
        }else if(flag == 0x02){//圆角处理
            Glide.with(context)
                    .load(imgUrl)
                    .bitmapTransform(new RoundedCornersTransformation(context,30,0, RoundedCornersTransformation.CornerType.ALL))
                    .into(iv);
        }else if(flag == 0x03){//灰度处理
            Glide.with(context)
                    .load(imgUrl)
                    .bitmapTransform(new GrayscaleTransformation(context))
                    .into(iv);
        }
    }

}
