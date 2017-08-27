package com.github.qiu.sosimplebanner.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.github.qiu.sosimplebanner.R;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * @author qcl
 * @time 2017/8/7
 * @email 13203727217@163.com
 * 图片加载使用glide 4.0,可在本类修改为其他框架
 */
public class ImageUtil {

    /**
     * 显示网络图片(可显示Gif)
     */
    public static void display(Context context, String url, ImageView imageView) {
        display(context, url, imageView, R.color.colorAccent);
    }

    /**
     * 显示网络图片(可显示Gif)
     */
    public static void display(Context context, String url, ImageView imageView, int defaultRes) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(defaultRes)
                .error(defaultRes);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 显示本地图片(Gif)
     */
    public static void display(Context context, int resource, ImageView imageView) {
            Glide.with(context).load(resource).into(imageView);
    }

}
