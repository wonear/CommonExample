package com.wonear.common.callback;


public interface OnThreadCallback {
    void onBackgroundThread();

    void onUiThread();
}
