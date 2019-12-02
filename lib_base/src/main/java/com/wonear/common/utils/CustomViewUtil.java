package com.wonear.common.utils;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author wonear
 * @date : 2019/10/8 10:11
 */
public class CustomViewUtil {

    public static int getTextWidth(String text, Paint paint) {
        Rect rect = new Rect(); // 文字所在区域的矩形
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }
}
