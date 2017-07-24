package com.hlct.android.util;

import android.app.Activity;

import com.za.finger.FingerHelper;
import com.za.finger.IUsbConnState;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/6/19
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description :指纹识别工具类，二次封装
 */

public class FingerUtils {
    /**
     * 实例化.
     */
    private static FingerHelper mFingerHelper;
    /**
     * 返回值
     */
    private static int returnCode;
    /**
     * 返回信息
     */
    private static String msg;


    /**
     * 初始化方法.
     *
     * @param activity      activity是因为需要接收获取USB授权操作后的返回
     * @param iUsbConnState iUsbConnState实现IUsbConnState接口，
     *                      用于接收USB指纹模组的授权状态和连接状态
     * @return String
     */
    private static String init(final Activity activity,
                               final IUsbConnState iUsbConnState) {
        mFingerHelper = new FingerHelper(activity, iUsbConnState);
        returnCode = mFingerHelper.init();
        msg = disposeReturnCode(returnCode);
        return msg;
    }

    /**
     * 连接USB指纹模组.
     *
     * @return String
     */
    private static String connectFingerDev() {
        // FIXME: 2017/6/19 阅读示例代码，根据授权的不同给出不同的提示.
        returnCode = mFingerHelper.connectFingerDev();
        msg = disposeReturnCode(returnCode);
        return msg;
    }

    // TODO: 2017/6/19 方法：获取指纹特征；将指纹特征保存到数据库（登陆人员ID）；搜索指纹特征；关闭设备.


    /**
     * 根据不同的返回码返回不同的提示.
     *
     * @param returnCode int类型的返回码
     * @return Sring类型的提示
     */
    private static String disposeReturnCode(final int returnCode) {
        String error_msg;
        switch (returnCode) {
            case (0x00):
                error_msg = "操作成功";
                break;
            case (0x01):
                error_msg = "连接成功" + "指令出错";
                break;
            case (0x02):
                error_msg = "未发现手指";
                break;
            case (0x03):
                error_msg = "获取指纹图像出错";
                break;
            case (0x04):
                error_msg = "手指太干躁";
                break;
            case (0x05):
                error_msg = "手指太湿";
                break;
            case (0x06):
                error_msg = "无序指纹";
                break;
            case (0x07):
                error_msg = "生成特征数据过小";
                break;
            case (0x08):
                error_msg = "无法匹配";
                break;
            case (0x09):
                error_msg = "无法搜索";
                break;
            case (0x0a):
                error_msg = "指纹合并出错 ";
                break;
            case (0x0b):
                error_msg = "设备地址覆盖";
                break;
            case (0x0c):
                error_msg = "读取出错";
                break;
            case (0x0d):
                error_msg = "上传模板出错";
                break;
            case (0x0e):
                error_msg = "数据接收出错";
                break;
            case (0x0f):
                error_msg = "上传指纹图像出错";
                break;
            case (0x10):
                error_msg = "删除模板出错";
                break;
            case (0x11):
                error_msg = "清空模板出错";
                break;
            case (0x12):
                error_msg = "休眠出错";
                break;
            case (0x13):
                error_msg = "无效的密钥";
                break;
            case (0x14):
                error_msg = "复位出错";
                break;
            case (0x15):
                error_msg = "无效的指纹图像";
                break;
            case (0x17):
                error_msg = "残留数据不可删除";
                break;
            default:
                error_msg = "未知错误";
                break;
        }
        return error_msg;
    }

}
