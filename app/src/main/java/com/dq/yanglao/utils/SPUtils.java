package com.dq.yanglao.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author jingang
 * @describtion 存取数据 公共类
 * @created at 2018/04/24
 */

public class SPUtils {

    public static final String SP_FILE = "dequan";//存数据名

    /**
     * 传入key，value，将数据存入Constant.SP_FILE(zzyj)中
     *
     * @param context 上下文对象
     * @param key     键值
     * @param value   数据
     */
    public static void savePreference(Context context, String key, String value) {
        SharedPreferences preference = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 根据key值 从名为Constant.SP_FILE(zzyj)的SharedPreferences中取出数据
     *
     * @param context 上下文对象
     * @param key     键值
     * @return
     */
    public static String getPreference(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        return preference.getString(key, "");
    }

    /**
     * @describtion 根据key值 从名为Constant.SP_FILE(zzyj)的SharedPreferences中移除数据
     * @author gengqiufang
     * @created at 2017/4/26 0026
     */
    public static void removePreference(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * @describtion 根据key值 清除Constant.SP_FILE(zzyj)的SharedPreferences
     * @author gengqiufang
     * @created at 2017/4/26 0026
     */
    public static void clearSP(Context context) {
        SharedPreferences preference = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.clear();
        editor.commit();
    }
}
