package com.wonear.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppUtil {

    private AppUtil() {
    }

    /**
     * 获取包名
     *
     * @return 包名
     */
    public static String getPackageName() {
        Context context = ContextManager.getContext();
        return context.getPackageName();
    }

    /**
     * 获取VersionName(版本名称)
     *
     * @return 失败时返回""
     */
    public static String getVersionName() {
        Context context = ContextManager.getContext();
        PackageManager packageManager = getPackageManager(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取VersionCode(版本号)
     *
     * @return 失败时返回-1
     */
    public static int getVersionCode() {
        Context context = ContextManager.getContext();
        PackageManager packageManager = getPackageManager(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取所有安装的应用程序,不包含系统应用
     *
     * @return 所有安装的应用程序
     */
    public static List<PackageInfo> getInstalledPackages() {
        Context context = ContextManager.getContext();
        PackageManager packageManager = getPackageManager(context);
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<PackageInfo> packageInfoList = new ArrayList<PackageInfo>();
        for (int i = 0; i < packageInfos.size(); i++) {
            if ((packageInfos.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                packageInfoList.add(packageInfos.get(i));
            }
        }
        return packageInfoList;
    }

    /**
     * 获取应用程序的icon图标
     *
     * @return 当包名错误时，返回null
     */
    public static Drawable getApplicationIcon() {
        Context context = ContextManager.getContext();
        PackageManager packageManager = getPackageManager(context);
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 启动安装应用程序
     *
     * @param activity
     * @param path     应用程序路径
     */
    public static void installApk(Activity activity, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    /**
     * 获取PackageManager对象
     *
     * @param context
     * @return
     */
    private static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }


}
