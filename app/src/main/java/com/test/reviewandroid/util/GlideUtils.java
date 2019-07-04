package com.test.reviewandroid.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.reviewandroid.R;

/**
 * Created by mdw on 2016/4/20.
 */
public class GlideUtils {


    /**
     * 加载方形头部图片
     *
     * @param url
     * @param context
     * @param imageView
     */
    public static void loadImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .placeholder(R.color.bg_gray)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载图片（圆形）
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCicleImage(Context context, ImageView imageView, String url) {

        Glide.with(context)
                .load(url)
                .dontAnimate()
                .placeholder(R.mipmap.icon_head)
                .error(R.mipmap.icon_head)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }


    /**
     * 是否禁止磁盘缓存加载图片
     *
     * @param url
     * @param context
     * @param imageView
     * @param type      缓存的类型
     *                  <li>磁盘缓存全部 DiskCacheStrategy.ALL</li>
     *                  <li>磁盘禁止缓存DiskCacheStrategy.NONE</li>
     */
    public static void loadImage(Context context, ImageView imageView,String url,
                                 DiskCacheStrategy type) {
        Glide.with(context).load(url).diskCacheStrategy(type).into(imageView);
    }

    /**
     * 是否禁止内存缓存加载图片
     *
     * @param url
     * @param context
     * @param imageView
     * @param skipMemoryCache 禁止内存缓存 true为禁止
     */
    public static void loadImage(Context context, ImageView imageView, String url, boolean
            skipMemoryCache) {
        Glide.with(context).load(url).skipMemoryCache(skipMemoryCache).into(imageView);
    }

    /**
     * 是否禁止内存/磁盘缓存加载图片
     *
     * @param url
     * @param context
     * @param imageView
     * @param type            缓存的类型
     *                        <li>磁盘缓存全部 DiskCacheStrategy.ALL</li>
     *                        <li>磁盘禁止缓存DiskCacheStrategy.NONE</li>
     * @param skipMemoryCache 禁止内存缓存 true为禁止
     */
    public static void loadImage(Context context, ImageView imageView,String url,
                                 DiskCacheStrategy type,
                                 boolean skipMemoryCache) {
        Glide.with(context).load(url).skipMemoryCache(skipMemoryCache).diskCacheStrategy(type)
                .into(imageView);
    }

    /**
     * 清除内存中的缓存 必须在UI线程中调用
     *
     * @param context
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清除磁盘中的缓存 必须在后台线程中调用，建议同时clearMemory()
     *
     * @param context
     */
    public static void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    /**
     * 优先级加载图片
     *
     * @param url
     * @param context
     * @param imageView
     * @param priority  优先级  Priority.LOW/Priority.HIGH
     */
    public static void loadImageWithPriority(Context context, ImageView imageView,String url,
                                             Priority priority) {
        Glide.with(context).load(url).priority(priority).into(imageView);
    }


}
