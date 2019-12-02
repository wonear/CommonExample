package com.wonear.common.widget.download;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.wonear.common.utils.ContextManager;
import com.wonear.common.utils.LogUtil;


/**
 * apk更新下载，需要请求内存卡读写权限
 */
public class UpdateAppManager {

    @SuppressWarnings("unused")
    private static final String TAG = "UpdateAppManager";
    private static long download_id;
    private DownloadManager downloadManager;
    private int totalSizeBytes;
    private int downloadSize;
    private boolean downloadComplete;


    public UpdateAppManager() {
        downloadManager = (DownloadManager)
                ContextManager.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
    }


    /**
     * 下载Apk, 并设置Apk地址,下载前需要请求内存卡读写权限
     * 默认位置: /storage/sdcard0/Download
     *
     * @param downLoadUrl 下载地址
     * @param infoName    通知名称
     * @param description 通知描述
     */
    @SuppressWarnings("unused")
    public void downloadApk(String downLoadUrl, String description, String infoName) {

        if (!isDownloadManagerAvailable()) {
            return;
        }

        String appUrl = downLoadUrl;
        if (appUrl == null || appUrl.isEmpty()) {

            return;
        }
        appUrl = appUrl.trim(); // 去掉首尾空格
        if (!appUrl.startsWith("http")) {
            appUrl = "http://" + appUrl; // 添加Http信息
        }


        DownloadManager.Request request;
        try {
            request = new DownloadManager.Request(Uri.parse(appUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        request.setTitle(infoName);
        request.setDescription(description);

        //在通知栏显示下载进度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        //sdcard目录下的download文件夹
        request.setDestinationInExternalPublicDir(CommonCons.SAVE_APP_LOCATION, CommonCons.SAVE_APP_NAME);
        Context appContext = ContextManager.getContext();
        download_id = downloadManager.enqueue(request);
        new ProgressThread().start();
    }

    // 最小版本号大于9
    private static boolean isDownloadManagerAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }


    // 查询下载进度，文件总大小多少，已经下载多少？
    private void query() {
        DownloadManager.Query downloadQuery = new DownloadManager.Query();
        downloadQuery.setFilterById(download_id);
        Cursor cursor = downloadManager.query(downloadQuery);
        if (cursor != null && cursor.moveToFirst()) {
            int totalSizeBytesIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int bytesDownloadSoFarIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);

            // 下载的文件总大小
            totalSizeBytes = cursor.getInt(totalSizeBytesIndex);
            downloadSize = cursor.getInt(bytesDownloadSoFarIndex);
            downloadComplete = (totalSizeBytes == downloadSize);
            LogUtil.d("download_progress", totalSizeBytes + "--" + downloadSize);
            boolean isComplete = totalSizeBytes == downloadSize && totalSizeBytes != 0;
            sendBroadcast(isComplete, false, totalSizeBytes, downloadSize);
            cursor.close();
        } else {
            sendBroadcast(false, true, 0, 0);
        }
    }

    private void sendBroadcast(boolean isComplete, boolean isError, int totlaSize, int downloadSize) {
        Context context = ContextManager.getContext();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isComplete", isComplete);
        bundle.putBoolean("isError", isError);
        bundle.putInt("totalSize", totlaSize);
        bundle.putInt("downloadSize", downloadSize);
        Intent intent = new Intent(InstallReceiver.ACTION_DOWNLOAD_PROGRESS);
        intent.putExtra("bundle", bundle);
        context.sendBroadcast(intent);
    }

    class ProgressThread extends Thread {
        @Override
        public void run() {
            while (!downloadComplete) {
                query();
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
