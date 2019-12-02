package com.wonear.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;

public class IntentUtil {


    /**
     * 调整到拨号界面
     *
     * @param phone 要拨打的电话号码
     */
    public static void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ContextManager.getContext().startActivity(intent);
    }


    /**
     * 发送短信，跳转到发送短信界面
     *
     * @param phone   发送短信的电话号码
     * @param content 发送的短信内容
     */
    public static void sendSmsMessage(String phone, String content) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
        intent.putExtra("sms_body", content);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ContextManager.getContext().startActivity(intent);
    }


    /**
     * 调用系统方式选择联系人，不需要手动去请求权限（暂时没发现需要需请求权限的机型）
     *
     * @param activity 当前上下文传入
     */
    public static void pickContact(Activity activity) {
        Intent jumpIntent = new Intent(Intent.ACTION_PICK);
        //从有电话号码的联系人中选取
        jumpIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        activity.startActivityForResult(jumpIntent, 100);
    }


    public static void playAudio(Activity activity, String path) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String type = "audio/*";
        Uri uri = Uri.parse(path);
        intent.setDataAndType(uri, type);
        activity.startActivity(intent);
    }

    /**
     * 回到手机桌面,用在首页返回可不杀死app退出
     */
    public static void intentHome() {
        Intent intent = new Intent();
        // 为Intent设置Action、Category属性
        intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
        intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ContextManager.getContext().startActivity(intent);
    }
}
