package com.wonear.common.utils;

import android.util.Log;

/**
 * @author wonear
 * @date : 2019/9/29 17:47
 */
public class LogUtil {

    private static final String TAG = "app_log";
    public static boolean isDebug;

    public static void i(String tag, String message) {
        if (isDebug)
            Log.i(tag, message);
    }

    public static void v(String tag, String message) {
        if (isDebug)
            Log.v(tag, message);
    }

    public static void e(String tag, String message) {
        if (isDebug)
            Log.e(tag, message);
    }

    public static void d(String tag, String message) {
        if (isDebug)
            Log.d(tag, message);
    }

    public static void w(String tag, String message) {
        if (isDebug)
            Log.w(tag, message);
    }

    public static void wtf(String tag, String message) {
        if (isDebug)
            Log.wtf(tag, message);
    }


    public static void i(String message) {
        if (isDebug)
            Log.i(TAG, message);
    }

    public static void v(String message) {
        if (isDebug)
            Log.v(TAG, message);
    }

    public static void e(String message) {
        if (isDebug)
            Log.e(TAG, message);
    }

    public static void d(String message) {
        if (isDebug)
            Log.d(TAG, message);
    }

    public static void w(String message) {
        if (isDebug)
            Log.w(TAG, message);
    }

    public static void wtf(String message) {
        if (isDebug)
            Log.wtf(TAG, message);
    }


}
