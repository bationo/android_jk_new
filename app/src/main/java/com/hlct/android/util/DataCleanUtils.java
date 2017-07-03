package com.hlct.android.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/6/16
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description :
 *          主要功能有清除内/外缓存，
 *          清除数据库，
 *          清除sharedPreference，
 *          清除files和清除自定义目录
 */
public class DataCleanUtils {

    /**
     * 防止子类调用.
     */
    protected DataCleanUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache).
     * @param context
     */
    public static void cleanInternalCache(final Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases).
     * @param context
     */
    public static void cleanDatabases(final Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs).
     * @param context
     */
    public static void cleanSharedPreference(final Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库.
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(final Context context,
                                           final String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容.
     * @param context
     */
    public static void cleanFiles(final Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache).
     * @param context
     */
    public static void cleanExternalCache(final Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除.
     * @param filePath
     */
    public static void cleanCustomCache(final String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 清除本应用所有的数据.
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(final Context context,
                                            final String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理.
     * @param directory
     */
    private static void deleteFilesByDirectory(final File directory) {
        if (directory != null && directory.exists()
                && directory.isDirectory()) {

            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

}
