package com.hlct.android.DataCache;

import android.content.Context;

import com.hlct.android.bean.User;
import com.hlct.android.util.ACache;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/7/27
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description : 二次封装缓存工具类
 */

public class DataCache {
    private static ACache aCache;

    /**
     * 储存文件解析结果.
     *
     * @param context getApplicationCo.
     * @param key     存储结果的key.
     * @param result  结果.
     */
    public static void saveParseResult(Context context, String key, boolean result) {
        aCache = ACache.get(context);
        aCache.put(key, result);
    }

    public static boolean getParseResult(Context context, String key) {
        aCache = ACache.get(context);
        boolean b = (boolean) aCache.getAsObject(key);
        return b;
    }

    /**
     * 存储一个User对象到缓存中,一周后自动失效.
     *
     * @param context getApplicationContext().
     * @param key     储存User的key.
     * @param user    User对象.
     */
    public static void saveUser(Context context, String key, User user) {
        aCache = ACache.get(context);
        aCache.put(key, user, 7 * ACache.TIME_DAY);
    }

    /**
     * 读取一个User对象,如果不存在则抛出空指针异常.
     *
     * @param context getApplicationContext().
     * @param key     储存User的key.
     * @return User对象.
     */
    public static User readUser(Context context, String key) throws NullPointerException {
        aCache = ACache.get(context);
        User user = (User) aCache.getAsObject(key);
        if (user == null) {
            throw new NullPointerException();
        } else {
            return user;
        }
    }

    /**
     * 清空所有的aCache缓存
     * @param context
     */
    public static void cleanAll(Context context) {
        aCache = ACache.get(context);
        aCache.clear();
    }

}
