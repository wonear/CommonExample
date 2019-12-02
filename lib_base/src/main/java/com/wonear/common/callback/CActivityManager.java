package com.wonear.common.callback;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

public class CActivityManager {

    private static Stack<Activity> activityStack;
    private static CActivityManager instance;

    private CActivityManager() {
    }

    /**
     * 单实例 , UI无需考虑多线程同步问题
     *
     * @return CActivityManager
     */
    public static CActivityManager getAppManager() {
        if (instance == null) {
            instance = new CActivityManager();
        }
        return instance;
    }

    /**
     * 添加
     *
     * @param activity 启动的界面
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     *
     * @param cls class
     * @return Activity
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * @param activity 结束的界面
     */
    public void finishActivity(Activity activity) {

        if (activity != null) {
            // 为与系统Activity栈保持一致，且考虑到手机设置项里的"不保留活动"选项引起的Activity生命周期调用onDestroy()方法所带来的问题,此处需要作出如下修正
            if (activity.isFinishing()) {
                activityStack.remove(activity);
                activity = null;
            } else {
                activity.finish();
            }
        }

    }

    /**
     * @param cls 结束的界面
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls 不关闭的那个ac名字
     */
    public void finishOthersActivity(Class<?> cls) {

        Iterator<Activity> it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (!(activity.getClass().equals(cls))) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity,在退出app时才调用，其他地方可能引起app闪退
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 判断某个acticity是否已经启动
     *
     * @param activity 要判断的activity
     * @return 是否启动
     */
    public boolean hasCreateActivity(Class activity) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (activity == activityStack.get(i).getClass()) {
                return true;
            }
        }
        return false;
    }
}
