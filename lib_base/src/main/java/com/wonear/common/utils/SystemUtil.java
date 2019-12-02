package com.wonear.common.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.UUID;

/**
 * @author wonear
 * @date : 2019/10/9 10:50
 */
public class SystemUtil {
    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceManufacturer() {
        return android.os.Build.MANUFACTURER;
    }


    /**
     * 获取品牌
     *
     * @return
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取设备名
     *
     * @return
     */
    public static String getDeviceName() {
        return android.os.Build.DEVICE;
    }

    /**
     * 获取序列号
     *
     * @return
     */
    public static String getDeviceSerial() {
        return android.os.Build.SERIAL;
    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPadFun1() {
        return (ContextManager.getContext().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * 通过计算设备尺寸大小的方法来判断是手机还是平板
     *
     * @return
     */
    public static boolean isPadFun2() {
        WindowManager wm = (WindowManager) ContextManager.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
// 屏幕宽度
        float screenWidth = display.getWidth();
// 屏幕高度
        float screenHeight = display.getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
// 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
// 大于6尺寸则为Pad
        if (screenInches >= 6.0) {
            return true;
        }
        return false;
    }


    public static String getDeviceUnique() {
        StringBuilder builder = new StringBuilder();
        builder.append("android-")
                .append(getDeviceManufacturer())
                .append("-").append(getSystemModel())
                .append("-").append(getDeviceBrand())
                .append("-").append(getDeviceSerial());

        String info = builder.toString();
        LogUtil.i(info);
        return StringUtil.md5(info);
    }

    /**
     * 自定义获取设备id
     *
     * @return 设备id
     */
    public static String getDeviceId() {
        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            // API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // serial需要一个初始化
            serial = "android-yxteacher"; // 随便一个初始化
        }
        // 使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();

    }
}
