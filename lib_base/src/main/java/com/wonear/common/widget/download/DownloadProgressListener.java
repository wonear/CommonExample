package com.wonear.common.widget.download;

public interface DownloadProgressListener {
    void onDownloadProgress(boolean isComplete, boolean isError, int totalSize, int downloadSize);
}
