package com.wonear.common.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


    /**
     * 对文字进行md5加密
     *
     * @param string 需要md加密的文字
     * @return 加密后的内容
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 设置文字高亮
     *
     * @param text  文字内容
     * @param key   要高亮的文字
     * @param color 高亮颜色
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder setTextHightLines(String text, String key, int color) {
        int start = 0, end = 0;
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        if (text.toLowerCase().contains(key.toLowerCase())) {
            start = text.toLowerCase().indexOf(key.toLowerCase());
            end = start + key.length();
            style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return style;
    }


    /**
     * 文字高亮
     *
     * @param text  文字内容
     * @param start 要高亮的文字起始位置
     * @param end   要高亮的文字终止位置
     * @param color 颜色
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder setTextHightLines(String text, int start, int end, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }


    /**
     * 是否是数字
     *
     * @param text
     * @return 是否数字
     */
    public static boolean isNum(String text) {
        Pattern pattern = Pattern.compile("^[-+]?[0-9]");
        return pattern.matcher(text).matches();
    }

    /**
     * 隐藏手机号中间部分
     *
     * @param phoneNum 电话号码
     * @return 转格式后的手机文字
     */
    public static String phoneText(String phoneNum) {
        //...
        if (phoneNum == null) return "";
        if (phoneNum.length() <= 7) return phoneNum;
        else if (phoneNum.length() <= 10)
            return phoneNum.substring(0, 3).concat("***") + phoneNum.substring(phoneNum.length() - 3);
        else
            return phoneNum.substring(0, 3).concat("****") + phoneNum.substring(phoneNum.length() - 4);
    }


    /**
     * string 转成float
     *
     * @return 浮点型数字
     */
    public static float convertToFloat(String number, float defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(number);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str 字符串
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 复制到粘贴板
     *
     * @param text 要复制的文字
     */
    @SuppressLint("NewApi")
    public static void copyClip(String text) {
        if (TextUtils.isEmpty(text)) return;
        ClipData clipData = ClipData.newPlainText("copy", text);
        ClipboardManager cmb = (ClipboardManager) ContextManager.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(clipData);
    }


    /**
     * 根据日期获取星期几
     *
     * @param date 日期
     * @return 星期
     */
    public static String getWeekByDate(String date) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "星期日";
        }
        if (wek == 2) {
            Week += "星期一";
        }
        if (wek == 3) {
            Week += "星期二";
        }
        if (wek == 4) {
            Week += "星期三";
        }
        if (wek == 5) {
            Week += "星期四";
        }
        if (wek == 6) {
            Week += "星期五";
        }
        if (wek == 7) {
            Week += "星期六";
        }
        return Week;
    }

    /**
     * 秒数转化成00:00格式
     *
     * @param second
     * @return
     */
    public static String parseTime(int second) {
        StringBuilder builder = new StringBuilder();
        if (second >= 60 * 60 * 24) {
            int day = second / (60 * 60 * 24);
            builder.append(day).append("天");

        } else if (second >= 60 * 60) {

            int hour = second / 3600;
            if (hour < 10) {
                builder.append("0").append(hour).append(":");
            } else {
                builder.append(hour).append(":");
            }

            int minute = second % 3600 / 60;
            if (minute < 10) {
                builder.append("0").append(minute).append(":");
            } else {
                builder.append(minute).append(":");
            }

            int se = second % 3600 % 60;
            if (se < 10) {
                builder.append("0").append(se);
            } else {
                builder.append(se);
            }

        } else if (second >= 60) {
            int minute = second / 60;
            if (minute < 10) {
                builder.append("0").append(minute).append(":");
            } else {
                builder.append(minute).append(":");
            }

            int se = second % 60;
            if (se < 10) {
                builder.append("0").append(se);
            } else {
                builder.append(se);
            }
        } else {
            builder.append("00:");
            if (second < 10) {
                builder.append("0").append(second);
            } else {
                builder.append(second);
            }
        }
        return builder.toString();
    }

    public static String noBlank(String description) {
        if (TextUtils.isEmpty(description)) return description;
        return description.replace("\n", "").replace(" ", "");
    }
}
