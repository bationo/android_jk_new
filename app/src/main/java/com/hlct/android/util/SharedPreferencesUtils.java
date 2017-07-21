package com.hlct.android.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/6/26
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description :SharedPreferences工具类.
 */

public class SharedPreferencesUtils {
    private String SP_INFO = "spInfo";
    private Context context;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
    }

    /**
     * 存放字符串类型的登陆信息,以键值对的形式保存.
     *
     * @param key
     * @param value
     */
    public void setInfo(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SP_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取String类型的数据
     *
     * @param key 键值对的键
     * @return String类型的字符串
     */
    public String getStringInfo(String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_INFO,
                Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * 存放布尔型参数.
     * @param key
     * @param value
     */
    public void setInfo(String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SP_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获得用户信息中某项布尔型参数的值.
     * @param key
     * @return
     */
    public boolean getBooleanInfo(String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_INFO,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 获取SharedPreferences数据里全部的key-value对.
     * @return Map类型的数据
     */
    public Map getAllInfo() {
        SharedPreferences sp = context.getSharedPreferences(SP_INFO,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 清空SharedPreferences.
     */
    public void clear() {
        SharedPreferences sp = context.getSharedPreferences(SP_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
