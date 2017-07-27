package com.hlct.android.util;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：     cmy
 * @version :     2017/7/16.
 * @e-mil ：      mengyuan.cheng.mier@gmail.com
 * @Description : 将所有的activity添加到List中,方便退出时统一管理.
 *
 *                在Acitvity的onCreate方法中初始化。
 *                ActivityUtils.getInstance().addActivity(this);
 *                在退出App的代码中调用destory()方法.
 */
public class ActivityUtils extends Application {
    private static List<Object> activitys = new ArrayList<>();
    private static ActivityUtils instance;

    /**
     * 获取单例模式中唯一的MyApplication实例.
     * @return instance
     */
    public static ActivityUtils getInstance() {
        if (instance == null) {
            instance = new ActivityUtils();
        }
        return instance;
    }

    /**
     * 添加activity到容器中.
     * @param activity
     */
    public void addActivity(final Activity activity) {
        if (!activitys.contains(activity)) {
            activitys.contains(activity);
        }
    }

    /**
     * 退出App时调用该方法.
     * 遍历所有activity并且finish.
     */
    public void destory() {
        for (Object activity : activitys) {
            ((Activity) activity).finish();
        }
        System.exit(0);
    }
}
