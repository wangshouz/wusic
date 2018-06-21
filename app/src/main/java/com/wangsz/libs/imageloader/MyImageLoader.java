package com.wangsz.libs.imageloader;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wangsz.wusic.R;

/**
 * author: wangsz
 * date: On 2018/4/9 0009
 */
public class MyImageLoader {

    /**
     * 展示头像
     *
     * @param album
     * @param imageView
     */
    public static void diaplayAlbum(String album, ImageView imageView) {
        Picasso.get()
                .load(album)
                .placeholder(R.mipmap.album_placeholder)
                .error(R.mipmap.album_placeholder)
                .into(imageView);
    }


    /**
     * 普通展示图片
     *
     * @param url
     * @param imageView
     */
    public static void diaplay(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.placeholder)
                .into(imageView);
    }

}
