package com.wonear.common.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.SurfaceHolder;

import com.wonear.common.callback.OnUiCallback;

import java.io.IOException;

/**
 * @author wonear
 * @date : 2019/9/29 15:40
 */
public class MediaPlayerHelper implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private MediaPlayer mPlayer;
    private boolean hasPrepared;
    private PlayProgressListener progressListener;
    private long currentPosition, totlaDuration;
    private String currentPlayPath;
    private Thread timeThread;

    private static class MediaPlayerHolder {
        private static final MediaPlayerHelper INSTANCE = new MediaPlayerHelper();
    }

    public static MediaPlayerHelper getInstance() {
        return MediaPlayerHolder.INSTANCE;
    }

    private MediaPlayerHelper() {
        if (null == mPlayer) {
            mPlayer = new MediaPlayer();
            mPlayer.setOnErrorListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnPreparedListener(this);
        }
    }


    public MediaPlayerHelper setProgressListener(PlayProgressListener progressListener) {
        this.progressListener = progressListener;
        return this;
    }

    public void play(Context context, String dataSource) {
        currentPlayPath = dataSource;
        hasPrepared = false; // 开始播放前讲Flag置为不可操作
        totlaDuration = 0;
        currentPosition = 0;
        try {
            mPlayer.reset();
            mPlayer.setDataSource(context, Uri.parse(dataSource)); // 设置曲目资源
            mPlayer.prepareAsync(); // 异步的准备方法
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        // release()会释放player、将player置空，所以这里需要判断一下
        totlaDuration = mPlayer.getDuration();
        if (null != mPlayer && hasPrepared) {
            mPlayer.start();
            startTimeThread();
        }
    }

    public void pause() {
        if (null != mPlayer && hasPrepared) {
            mPlayer.pause();
        }
    }

    public void seekTo(int position) {
        if (null != mPlayer && hasPrepared) {
            mPlayer.seekTo(position);
        }
    }

    // 对于播放视频来说，通过设置SurfaceHolder来设置显示Surface。这个方法不需要判断状态、也不会改变player状态
    public void setDisplay(SurfaceHolder holder) {
        if (null != mPlayer) {
            mPlayer.setDisplay(holder);
        }
    }

    public void release() {
        totlaDuration = 0;
        currentPosition = 0;
        hasPrepared = false;
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        hasPrepared = true; // 准备完成后回调到这里
        start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        hasPrepared = false;
        // 通知调用处，调用play()方法进行下一个曲目的播放
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        hasPrepared = false;
        return false;
    }

    private void startTimeThread() {
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if (mPlayer == null) return;
                    while (mPlayer.isPlaying()) {
                        currentPosition = mPlayer.getCurrentPosition();
                        RxJavaUtls.runOnUiThread(new OnUiCallback() {
                            @Override
                            public void onUiThread() {
                                if (progressListener != null) {
                                    progressListener.playProgress(currentPlayPath, totlaDuration, currentPosition);
                                }
                            }
                        });


                        Thread.sleep(100);
                        continue;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
    }


    public interface PlayProgressListener {
        void playProgress(String path, long totalDuration, long currentDutation);
    }

}
