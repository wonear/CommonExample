package com.wonear.common.widget.download;

import android.os.Environment;

import java.io.File;

public class CommonCons {
    public final static String SAVE_APP_NAME = "download.apk";

    public final static String SAVE_APP_LOCATION = "/download";


    public final static String APP_FILE_NAME = Environment.getExternalStorageDirectory() + SAVE_APP_LOCATION + File.separator + SAVE_APP_NAME;

}
