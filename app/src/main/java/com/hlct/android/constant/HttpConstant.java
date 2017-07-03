package com.hlct.android.constant;

/**
 * @author ：     cmy
 * @version :     2017/5/27.
 * @e-mil ：      mengyuan.cheng.mier@gmail.com
 * @Description : 网络连接相关常量
 */

public class HttpConstant {

    /**
     * 防止子类调用.
     */
    protected HttpConstant() {
        throw new UnsupportedOperationException();
    }
    /**
     * 服务器地址.
     */
    public static final String BASE_SERVER_URL =
            "http://192.168.1.109:8080/android_jkweb/";

    /**
     * PDA登录地址.
     */
    public static final String URL_PDA_LOGIN =
            BASE_SERVER_URL + "/mobile-message!pdaLogin.do";
}
