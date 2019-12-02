package com.wonear.common.utils;

import android.animation.ValueAnimator;
import android.media.MediaRecorder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.animation.LinearInterpolator;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author wonear
 * @date : 2019/9/29 15:01
 */
public class AudioRecordHelper {

    private MediaRecorder mMediaRecorder;
    private File audioFile;
    private ValueAnimator valueAnimator;
    private int second;//录音时间
    private RecordListener recordTimeListener;
    private boolean isRecording = false;

    private static class AudioRecordHolder {
        private static final AudioRecordHelper INSTANCE = new AudioRecordHelper();
    }

    private AudioRecordHelper() {
        valueAnimator = ValueAnimator.ofInt(0, 500);//定义最多500s
        valueAnimator.setDuration(500 * 1000);//
        valueAnimator.setInterpolator(new LinearInterpolator());

    }

    public static AudioRecordHelper getInstance() {
        return AudioRecordHolder.INSTANCE;
    }


    public AudioRecordHelper setRecordListener(RecordListener recordTimeListener) {
        this.recordTimeListener = recordTimeListener;
        return this;
    }

    public void startAudioRecord() {

        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
        } else {
            //实例化MediaRecorder对象
            mMediaRecorder = new MediaRecorder();
        }

        try {
            // 设置麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            //设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            String fileName = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".m4a";
            audioFile = new File(getRootPath(), fileName);
            if (!audioFile.exists()) audioFile.createNewFile();
            LogUtil.d("startAudioRecord", audioFile.getPath());
            mMediaRecorder.setOutputFile(audioFile.getPath());
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isRecording = true;
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    second = (int) animation.getAnimatedValue();
                    LogUtil.d("onAnimationUpdate", second + "");
                    if (recordTimeListener == null) return;
                    recordTimeListener.recordTime(second);
                }
            });
            valueAnimator.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isRecording) {
//                            int maxAmplitude = mMediaRecorder.getMaxAmplitude() * 13 / 0x7FFF;
                            int maxAmplitude = mMediaRecorder.getMaxAmplitude() * 26 / 0x7FFF;
                            LogUtil.e("maxAmplitude", maxAmplitude + "");
                            if (recordTimeListener == null) return;
                            recordTimeListener.maxAmplitude(maxAmplitude);
                            SystemClock.sleep(100);
                        }
                    } catch (Exception e) {
                        // from the crash report website, found one NPE crash from
                        // one android 4.0.4 htc phone
                        // maybe handler is null for some reason
                        LogUtil.e("voice", e.toString());
                    }
                }
            }).start();

            LogUtil.d("startAudioRecord", "开始录音...");
        } catch (IllegalStateException e) {
            LogUtil.e("startAudioRecord", "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
            LogUtil.e("startAudioRecord", "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    public void stopRecord() {
        try {
            isRecording = false;
            valueAnimator.cancel();
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;

        } catch (RuntimeException e) {
            if (e != null)
                LogUtil.d("startAudioRecord", JsonUtil.moderToString(e));
        }
        LogUtil.d("startAudioRecord", "结束录音...");
    }

    public void deleteAudio() {
        deleteAudio("");
    }


    public void deleteAudio(String path) {
        if (TextUtils.isEmpty(path)) {
            if (audioFile != null && audioFile.exists()) {
                audioFile.delete();
            }
        } else {
            File file = new File(path);
            if (file != null && file.exists()) file.delete();
        }

    }

    public String getAudioPath() {
        if (audioFile != null && audioFile.exists()) {
            LogUtil.d("startAudioRecord", "路径：" + audioFile.getPath());
            return audioFile.getPath();
        }

        LogUtil.d("startAudioRecord", "没有文件...");
        return "";
    }

    private String getRootPath() {
        File directory_doc = ContextManager.getContext().getCacheDir();
        File dir = new File(directory_doc, "yx_audio");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getPath();
    }

    public interface RecordListener {

        /**
         * 播放进度回调
         *
         * @param second 当前播放时间进度
         */
        void recordTime(int second);

        /**
         * 振幅回调
         *
         * @param maxAmplitude 振幅数值
         */
        void maxAmplitude(int maxAmplitude);
    }


}
