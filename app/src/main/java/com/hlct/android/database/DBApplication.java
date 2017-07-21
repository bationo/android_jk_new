package com.hlct.android.database;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.hlct.android.constant.DatabaseConstant;
import com.hlct.android.greendao.DaoMaster;
import com.hlct.android.greendao.DaoSession;

/**
 * Created by lazylee on 2017/7/6.
 * 在全局变量中将GreenDao相关类进行初始化
 */

public class DBApplication extends Application {

    private static DaoSession mDaoSession;

    /**
     * 创建数据数据库操作
     * 初始化daoSession
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //创建SQLite数据库的SQLiteOpenHelper的具体实现
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, DatabaseConstant.DATABASE_NAME,null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //获取数据库对象 DaoMaster是GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //管理所有的Dao对象，Dao对象中存在着增删改查等API
        mDaoSession = daoMaster.newSession();
    }

    /**
     * 获取daoSession对象
     * @return  mDaoSession
     */
    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
