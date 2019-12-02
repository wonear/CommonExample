package com.wonear.common.widget.download;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.FileProvider;
import android.text.TextUtils;

import com.wonear.common.utils.LogUtil;

import java.io.File;

public class InstallReceiver extends BroadcastReceiver {
    public static final String ACTION_DOWNLOAD_PROGRESS = "action_download_progress";
    private static final String TAG = "InstallReceiver";
    private String fileProvider;

    private DownloadProgressListener listener;


    /**
     * 传入xml中配置的provider
     *
     * @param fileProvider xml中配置的provider
     */
    public InstallReceiver(String fileProvider) {
        this.fileProvider = fileProvider;
    }


    public void setListener(DownloadProgressListener listener) {
        this.listener = listener;
    }

    // 安装下载接收器
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            if (listener != null)
                listener.onDownloadProgress(true, false, 0, 0);
            installApk(context);
        } else if (TextUtils.equals(intent.getAction(), ACTION_DOWNLOAD_PROGRESS)) {
            Bundle bundle = intent.getBundleExtra("bundle");
            boolean isComplete = bundle.getBoolean("isComplete");
            boolean isError = bundle.getBoolean("isError", false);
            int totalSize = bundle.getInt("totalSize");
            int downloadSize = bundle.getInt("downloadSize");
            if (listener != null)
                listener.onDownloadProgress(isComplete, isError, totalSize, downloadSize);
        }
    }

    // 安装Apk
    private void installApk(Context context) {

        try {
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            String filePath = CommonCons.APP_FILE_NAME;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, fileProvider, new File(filePath));
                intent1.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent1.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent1);
        } catch (Exception e) {
            LogUtil.e(TAG, "安装失败");
            e.printStackTrace();
        }
    }


    /**
     * 注册广播
     *
     * @param activity     上下文
     * @param listener     进度监听
     * @param fileProvider fileProvider路径
     */
    public static InstallReceiver register(Activity activity, DownloadProgressListener listener, String fileProvider) {
        InstallReceiver downLoadCompleteReceiver = new InstallReceiver(fileProvider);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        intentFilter.addAction(InstallReceiver.ACTION_DOWNLOAD_PROGRESS);
        downLoadCompleteReceiver.setListener(listener);
        activity.registerReceiver(downLoadCompleteReceiver, intentFilter);
        return downLoadCompleteReceiver;
    }
}