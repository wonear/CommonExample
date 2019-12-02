package com.wonear.common.utils;

import com.wonear.common.callback.OnUiCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wonear
 * @date : 2019/11/18 16:34
 */
public class FileUtil {

    /**
     * 存放书本的文件夹
     */
    private static final String book_dir = ContextManager.getContext().getFilesDir().getAbsolutePath() + "/gbks";


    /**
     * 保存文件,在子线程中执行
     *
     * @param inputStream
     */
    public static void saveFiles(InputStream inputStream, final String fileName, final DownloadListener listener) {
        File d = new File(book_dir);
        if (!d.exists()) {
            d.mkdir();
            File f = new File(d, fileName);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // ProgressBar 设置最大值
        //读写的进度
        int count = 0;
        try {
            //输出流
            final File file = new File(book_dir, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            LogUtil.e(file.getAbsolutePath());
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                LogUtil.e(bytes.length + "");
                fileOutputStream.write(bytes, 0, len);
                //传递当前读写的进度
                count += len;
            }

            fileOutputStream.close();
            inputStream.close();

            RxJavaUtls.runOnUiThread(new OnUiCallback() {
                @Override
                public void onUiThread() {
                    listener.downloadSuccess(file.getPath());
                    CommonToast.toast("下载完成");
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface DownloadListener {
        void downloadSuccess(String path);
    }
}
