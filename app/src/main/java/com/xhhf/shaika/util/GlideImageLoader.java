package com.xhhf.shaika.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.ninegrid.NineGridView;
import com.xhhf.shaika.R;

import java.io.File;

/**
 * 图片工具类
 * 作者：Eric on 2016/11/16 15:14
 * 邮箱：845505772@qq.com
 */
public class GlideImageLoader implements ImageLoader, NineGridView.ImageLoader {
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)//
                .placeholder(R.drawable.ic_default_color)// 这行貌似是glide的bug,在部分机型上会导致第一次图片不在中间
                .error(R.drawable.ic_default_color)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(new File(path))//
                .placeholder(R.drawable.ic_default_color)//
                .error(R.drawable.ic_default_color)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}
