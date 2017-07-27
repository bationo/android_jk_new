package com.hlct.android.constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hlct.android.greendao.DaoMaster;
import com.hlct.android.greendao.DaoSession;

/**
 * Created by mengyuan.cheng on 2017/6/15.
 */

public class DatabaseConstant {
    /**
     * 防止子类调用.
     */
    protected DatabaseConstant() {
        throw new UnsupportedOperationException();
    }

    /**
     * 文件存放地址.
     */
    public static final String FILE_PATH = "/sdcard/Download/";
    /**
     * 数据库名称.
     */
    public static final String DATABASE_NAME = "hlct.db";
    /**
     * User对象的缓存名称.
     */
    public static final String DATACHCHE_USER = "LoginUser";
    /**
     * 文件解析结果的缓存名称.
     */
    public static final String DATACHCHE_FILE_RESULT = "FileResult";

    public static DaoSession setupDatabase(Context context, DaoSession daoSession) {
        //创建数据库
        DaoMaster.DevOpenHelper helper =
                new DaoMaster.DevOpenHelper(context, DatabaseConstant.DATABASE_NAME, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster dm = new DaoMaster(db);
        //获取Dao对象的管理者
        daoSession = dm.newSession();

        return daoSession;
    }

}
