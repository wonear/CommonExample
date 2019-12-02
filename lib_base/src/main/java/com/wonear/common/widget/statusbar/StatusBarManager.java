package com.wonear.common.widget.statusbar;

import android.app.Activity;

import com.wonear.common.R;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

/**
 * @author 王一阳
 * @description : 修改沉浸式状态栏的封装类
 * @date : 2019/9/18 16:50
 */
public class StatusBarManager {

    public static void setColor(Activity activity, int statusColor, boolean darkFont) {
        setColor(activity, statusColor, darkFont, false);
    }

    public static void setColor(Activity activity, String statusColor, boolean darkFont) {
        setColor(activity, statusColor, darkFont, true);
    }

    public static void setWhiteStatus(Activity activity) {
        setColor(activity, "#ffffff", true);
    }


    public static void setWhiteStatusEnableKeyboard(Activity activity) {
        setColor(activity, "#ffffff", true, true);
    }

    public static void setPrimaryStatus(Activity activity) {
        setColor(activity, R.color.colorPrimary, false);
    }


    public static void setDartStarus(Activity activity) {
        setColor(activity, "#000000", false);
    }

    public static void setTransparent(Activity activity) {
        ImmersionBar.with(activity)
                .fitsSystemWindows(false)
                .fullScreen(true)
                .transparentBar()
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .transparentNavigationBar()
                .init();
    }


    public static void setColor(Activity activity, int statusColor, boolean darkFont, boolean enableKeyboard) {
        ImmersionBar.with(activity)
                .fitsSystemWindows(true)
                .statusBarColor(statusColor)
                .keyboardEnable(enableKeyboard)
                .statusBarDarkFont(darkFont)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init();
    }

    public static void setColor(Activity activity, String statusColor, boolean darkFont, boolean enableKeyboard) {
        ImmersionBar.with(activity)
                .fitsSystemWindows(true)
                .statusBarColor(statusColor)
                .keyboardEnable(enableKeyboard)
                .statusBarDarkFont(darkFont)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init();
    }
}
