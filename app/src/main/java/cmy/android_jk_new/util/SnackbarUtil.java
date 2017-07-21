package cmy.android_jk_new.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/7/21
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description :
 */

public class SnackbarUtil {

    /**
     * 显示一个长时间的Snackbar
     * @param view view
     * @param msg 想显示的消息
     */
    public static void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

}
