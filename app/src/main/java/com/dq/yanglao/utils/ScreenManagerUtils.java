package com.dq.yanglao.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * @describe： 屏幕管理器 管理所有Activity
 * @author：jingang
 * @createdate：2018/03/22
 */

public class ScreenManagerUtils {

    private static Stack<Activity> activityStack;
    private static ScreenManagerUtils instance;

    public static Activity getmCurrentActivity() {
        return mCurrentActivity;
    }

    public static void setmCurrentActivity(Activity mCurrentActivity) {
        ScreenManagerUtils.mCurrentActivity = mCurrentActivity;
    }

    private static Activity mCurrentActivity;

    private ScreenManagerUtils() {
    }

    /**
     * 单例模式 获取屏幕管理对象
     *
     * @return
     */
    public static ScreenManagerUtils getInstance() {
        if (instance == null) {
            instance = new ScreenManagerUtils();
        }
        return instance;
    }

    /**
     * 退出当前栈顶Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获得当前栈顶Activity
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 将Activity加入栈中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 退出栈中所有Activity 并退出程序
     */
    public void finishAllActivityAndClose() {
        if (activityStack != null && activityStack.size() > 0) {
            for (Activity activity : activityStack) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
            activityStack.clear();
        }
        System.exit(0);
    }

    /**
     * 退出登录 清空activit栈 不退出程序
     */
    public void clearActivityStack() {
        if (activityStack != null && activityStack.size() > 0) {
            for (Activity activity : activityStack) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 保留ActivityStack中的某个activity
     *//*
    public void saveOnlyActivity(Class clazz) {
        if (activityStack != null && activityStack.size() > 0) {
            for (Activity activity : activityStack) {
                String itemActivityPackageName = activity.getComponentName().getClassName();
                String saveActivityPackageName = clazz.getName();
                if (!activity.isFinishing() && !itemActivityPackageName.equals(saveActivityPackageName)) {
                    activity.finish();
                }
            }
        }
    }*/

}