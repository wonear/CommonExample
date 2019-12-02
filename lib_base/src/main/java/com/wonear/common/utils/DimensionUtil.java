package com.wonear.common.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;

import java.lang.reflect.Method;

/**
 * Created by WYiang on 2017/10/2.
 */

public class DimensionUtil {


    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWith() {
        Resources resources = ContextManager.getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度，这边拿到的会烧掉状态栏的高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHight() {
        Resources resources = ContextManager.getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }


    /**
     * 获取全屏高度
     *
     * @param activity 上下文
     * @return 屏幕高度
     */
    public static int getFullScreenHight(Activity activity) {
        int dpi = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 数字
     */
    public static int dip2px(float dpValue) {
        final float scale = ContextManager.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue 数字
     */
    public static int px2dip(float pxValue) {
        final float scale = ContextManager.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp转换为px
     *
     * @param spValue 数字
     * @return 数字
     */
    public static int sp2px(float spValue) {
        final float scale = ContextManager.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 将px转换为sp
     *
     * @param spValue 数字
     * @return 数字
     */
    public static int px2sp(float spValue) {
        final float scale = ContextManager.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue / scale + 0.5f);
    }


}
