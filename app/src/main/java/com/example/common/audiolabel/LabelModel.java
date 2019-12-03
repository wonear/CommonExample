package com.example.common.audiolabel;

import android.graphics.Rect;

import com.wonear.common.utils.DimensionUtil;

/**
 * @author wonear
 * @date : 2019/12/3 16:38
 */
public class LabelModel {

    private int x;//左上角横坐标
    private int y;//左上角纵坐标


    private Rect rect;//当前控件的矩形范围

    private Rect rectRate;//当前控件的矩形范围

    private String audioPath;//对应的音频路径
    private String audioTime;//音频时间



    public LabelModel(int x, int y, Rect rect, String audioPath, String audioTime) {
        this.x = x;
        this.y = y;
        this.rect = rect;
        this.audioPath = audioPath;
        this.audioTime = audioTime;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(String audioTime) {
        this.audioTime = audioTime;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

}
