package com.pccb.newapp.imgloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pccb.newapp.R;

/**
 * Glide图片加载工具类
 * @author cgc
 * @created 2017-11-02
 */
public class GlideImageLoader {


    /**
     * 默认加载
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void displayImageView(Context context, Object path, ImageView imageView) {

        if (path instanceof String) {
            if (((String) path).contains("https")) {
                path = ((String) path).replace("https", "http");
            }
        }

//        Logger.d("--------图片  path-----------"+path.toString());

        Glide.with(context)
                .load(path)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.mipmap.defautl_home_ad_loadding)
                .error(R.mipmap.defautl_home_ad_loadding)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 默认加载--不剪裁
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void displayImageViewNoCrop(Context context, Object path, ImageView imageView) {

        if (path instanceof String) {
            if (((String) path).contains("https")) {
                path = ((String) path).replace("https", "http");
            }
        }

        Glide.with(context)
                .load(path)
                //.centerCrop()
                .dontAnimate()
                .placeholder(R.mipmap.defautl_home_ad_loadding)
                .error(R.mipmap.defautl_home_ad_loadding)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }



    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

}
