package com.wonear.common.widget.banner.loader;

import android.content.Context;
import android.widget.ImageView;

import com.wonear.common.utils.CommonImageLoader;

/**
 * @author wonear
 * @date : 2019/10/22 13:58
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        CommonImageLoader.load(path).round(10).into(imageView);
    }
}
