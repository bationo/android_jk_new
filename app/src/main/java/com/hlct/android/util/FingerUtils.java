package com.hlct.android.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;

import com.hlct.android.R;
import com.hlct.android.bean.MessageEvent;
import com.za.finger.FingerHelper;
import com.za.finger.IUsbConnState;

import org.greenrobot.eventbus.EventBus;

import cn.pda.serialport.Tools;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/6/19
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description :指纹识别工具类，二次封装
 */

public class FingerUtils {

    private Activity mContext;
    private static final String TAG = "FingerUtils";
    /**
     * 实例化.
     */
    private static FingerHelper mFingerHelper;
    private Handler mHandler = new Handler();      //handle thread message
    private AlertDialog mDialog;
    private int statues = 0;
    private String connectState = "";
    //IUsbConnState is to receive usb finger connect state
    private IUsbConnState usbConnState = new IUsbConnState() {
        @Override
        public void onUsbConnected() {
            Log.e(TAG, "onUsbConnected()");
            //connect finger device
            statues = mFingerHelper.connectFingerDev();
            if (statues == mFingerHelper.CONNECT_OK) {
                connectState = "连接成功";
            } else {
                connectState = "连接失败";
            }
        }

        @Override
        public void onUsbPermissionDenied() {
            Log.e(TAG, "onUsbPermissionDenied()");
            connectState = "请求权限失败";
        }

        @Override
        public void onDeviceNotFound() {
            Log.e(TAG, "onDeviceNotFound()");
            connectState = "设备未找到";
        }
    };


    /**
     * 构造函数
     *
     * @param mContext 上下文
     */
    public FingerUtils(Activity mContext) {
        this.mContext = mContext;
        mFingerHelper = new FingerHelper(mContext, usbConnState);
    }

    /**
     * 初始化指纹工具.
     */
    public void initUtils() {
        mFingerHelper.init();
    }

    /**
     * 获取连接状态
     * 必须在<code>initUtils()</code>方法调用后才能获取到状态,否则返回 空字符串"".
     *
     * @return "连接成功","连接失败","请求权限失败","设备未找到" 四种状态
     */
    public String getConnState() {
        return connectState;
    }

    private String tempImgPath = "/mnt/sdcard/temp.bmp";
    private final int IMAGE_SIZE = 256 * 288;//image size
    private long getImageStartTime = 0L;

    /**
     * 获取指纹图像.
     * 使用eventbus 将结果<code>Bitmap</code>发送出去
     */
    public void getFingerPrintImage() {
        getImageStartTime = System.currentTimeMillis();
        mHandler.postDelayed(getFPImageTask, 0);
    }

    private long getCharStartTime = 0L;

    /**
     * 得到指纹识别码.
     * 通过EventBus传递String;
     */
    public void getFingerChar() {
        getCharStartTime = System.currentTimeMillis();
        mHandler.postDelayed(getCharTask, 0);
    }

    private long enrollStartTime = 0L;
    private int fpCharBuffer = 0;

    /**
     * 将指纹特征储存在数据空中
     */
    public void saveFingerChar() {
        //TODO 将指纹储存在数据库中,返回所在的位置 数据库最大只能储存512个指纹.
        //        initUtils();
        enrollStartTime = System.currentTimeMillis();
        fpCharBuffer = mFingerHelper.CHAR_BUFFER_A;
        //run match finger char task
        mHandler.postDelayed(enrollTask, 0);
        mDialog = new AlertDialog.Builder(mContext)
                .setTitle("指纹录制")
                .setMessage("请将手指放置在指纹传感器上!")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        close();
                        mDialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        close();
                        mDialog.dismiss();
                    }
                })
                .show();
        enableButton(mDialog.getButton(-1), false);
    }

    private long searchStartTime = 0L;

    /**
     * 从指纹库中搜索指纹
     */
    public void searchFingerChar() {
        searchStartTime = System.currentTimeMillis();
        mHandler.postDelayed(searchTask, 0);
    }

    /**
     * 清空数据空中的所有指纹
     *
     * @return true表示清空成功, false表示清空失败
     */
    public boolean emptyFingerChar() {
        int statue = mFingerHelper.emptyChar();
        if (statue == mFingerHelper.PS_OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 关闭指纹识别
     */
    public void close() {
        mFingerHelper.close();
    }

    /*********************************************************************************/
    private long getImageEndTime = 0L;
    /**
     * get finger image task
     */
    private Runnable getFPImageTask = new Runnable() {
        @Override
        public void run() {
            long timeCount = 0L;
            getImageEndTime = System.currentTimeMillis();
            timeCount = getImageEndTime - getImageStartTime;
            //search finger time 10s
            if (timeCount > 10000) {
                MessageEvent messageEvent = new MessageEvent(TAG, "FingerLoginActivity",
                        "指纹识别超时");
                EventBus.getDefault().post(messageEvent);
                return;
            }
            statues = mFingerHelper.getImage();
            //find finger
            if (statues == mFingerHelper.PS_OK) {
                int[] recvLen = {0, 0};
                byte[] imageByte = new byte[IMAGE_SIZE];//256*288
                mFingerHelper.uploadImage(imageByte, recvLen);
                //switch to bmp
                mFingerHelper.imageData2BMP(imageByte, tempImgPath);
                Bitmap bitmap = BitmapFactory.decodeFile(tempImgPath, null);
                EventBus.getDefault().post(bitmap);
                Log.e(TAG, bitmap.toString());
            } else if (statues == mFingerHelper.PS_NO_FINGER) {      //未识别到指纹
                mHandler.postDelayed(getFPImageTask, 100);
            } else if (statues == mFingerHelper.PS_GET_IMG_ERR) {    //图像获取错误
                mHandler.postDelayed(getFPImageTask, 100);
                return;
            } else {
                return;
            }
        }
    };

    private long getCharEndTime = 0L;
    /**
     * get finger char
     */
    private Runnable getCharTask = new Runnable() {
        @Override
        public void run() {
            long timeCount = 0L;
            getCharEndTime = System.currentTimeMillis();
            timeCount = getCharEndTime - getCharStartTime;
            //search finger time 10s
            if (timeCount > 10000) {
                MessageEvent messageEvent = new MessageEvent(TAG, "FingerLoginActivity",
                        "指纹识别超时");
                EventBus.getDefault().post(messageEvent);
                return;
            }
            statues = mFingerHelper.getImage();
            //find finger
            if (statues == mFingerHelper.PS_OK) {
                //gen char to bufferA
                statues = mFingerHelper.genChar(mFingerHelper.CHAR_BUFFER_A);
                if (statues == mFingerHelper.PS_OK) {
                    int[] iCharLen = {0, 0};
                    byte[] charBytes = new byte[512];
                    //upload char
                    statues = mFingerHelper.upCharFromBufferID(mFingerHelper.CHAR_BUFFER_A, charBytes, iCharLen);
                    if (statues == mFingerHelper.PS_OK) {
                        //upload success
                        String fingerChar = Tools.Bytes2HexString(charBytes, 512);
                        MessageEvent messageEvent = new MessageEvent(TAG,
                                "FingerLoginActivity", fingerChar);
                        EventBus.getDefault().post(messageEvent);
                    }
                } else {
                    //char is bad quickly
                    return;
                }
            } else if (statues == mFingerHelper.PS_NO_FINGER) {
                mHandler.postDelayed(getCharTask, 100);
            } else if (statues == mFingerHelper.PS_GET_IMG_ERR) {
                mHandler.postDelayed(getCharTask, 100);
                return;
            } else {
                return;
            }
        }
    };

    private long enrollEndTime = 0L;
    /**
     * enroll finger char to flash database
     */
    private Runnable enrollTask = new Runnable() {
        @Override
        public void run() {
            long timeCount = 0L;
            enrollEndTime = System.currentTimeMillis();
            timeCount = enrollEndTime - enrollStartTime;
            //search finger time 10s
            if (timeCount > 10000) {
                //TODO 将"someActivity" 替换成所用的Activity;
                MessageEvent messageEvent = new MessageEvent(TAG, "SomeActivity", "指纹识别超时");
                EventBus.getDefault().post(messageEvent);
                return;
            }
            statues = mFingerHelper.getImage();
            //find finger
            if (statues == mFingerHelper.PS_OK) {
                //first finger
                if (fpCharBuffer == mFingerHelper.CHAR_BUFFER_A) {
                    //gen char to bufferA
                    statues = mFingerHelper.genChar(fpCharBuffer);
                    if (statues == mFingerHelper.PS_OK) {
                        int[] iMaddr = {0, 0};
                        //is exist flash database,database size = 512
                        statues = mFingerHelper.search(mFingerHelper.CHAR_BUFFER_A, 0, 512, iMaddr);
                        // 表示指纹库中已经存在改指纹.
                        if (statues == mFingerHelper.PS_OK) {
                            mDialog.setMessage("该指纹已经在数据库中存在,数据库索引:" + iMaddr[0]);
                            enableButton(mDialog.getButton(-1), true);
                            return;
                        }
                        mDialog.setMessage("识别成功,请将手指重新放置");
                        fpCharBuffer = mFingerHelper.CHAR_BUFFER_B;
                        mHandler.postDelayed(enrollTask, 2000);
                    }
                } else if (fpCharBuffer == mFingerHelper.CHAR_BUFFER_B) { //second finger
                    //gen char to bufferB
                    statues = mFingerHelper.genChar(fpCharBuffer);
                    if (statues == mFingerHelper.PS_OK) {
                        //merge BUFFER_A with BUFFER_B , gen template to MODULE_BUFFER
                        mFingerHelper.regTemplate();                        //生成模板
                        int[] iMbNum = {0, 0};
                        mFingerHelper.getTemplateNum(iMbNum);               //获取数据库中的模板数
                        int templateNum = iMbNum[0];
                        if (templateNum >= 512) {
                            mDialog.setMessage("指纹数据库已经满,请清空后重新录制");
                            enableButton(mDialog.getButton(-1), true);
                            return;
                        }
                        //store template to flash database
                        statues = mFingerHelper.storeTemplate(mFingerHelper.MODEL_BUFFER, templateNum);
                        if (statues == mFingerHelper.PS_OK) {
                            mDialog.setMessage("指纹录制成功!");
                            enableButton(mDialog.getButton(-1), true);
                            //TODO 将someActivity替换
                            MessageEvent message = new MessageEvent(TAG, "someActivity", templateNum);
                            EventBus.getDefault().post(message);
                        } else {
                            mDialog.setMessage("指纹录制失败");
                            enableButton(mDialog.getButton(-1), true);
                        }
                    }
                }

            } else if (statues == mFingerHelper.PS_NO_FINGER) {
                //mDialog.setMessage("未识别到指纹 ,time:"+((10000 - (enrollEndTime-enrollStartTime))));
                mHandler.postDelayed(enrollTask, 100);
            } else if (statues == mFingerHelper.PS_GET_IMG_ERR) {
                mDialog.setMessage("指纹识别错误!");
                enableButton(mDialog.getButton(-1), true);
                return;
            } else {
                mDialog.setMessage("指纹模块初始化失败");
                enableButton(mDialog.getButton(-1), true);
                return;
            }
        }
    };

    private long searchEndTime = 0L;
    /**
     * search finger in flash database
     */
    private Runnable searchTask = new Runnable() {
        @Override
        public void run() {
            long timeCount = 0L;
            searchEndTime = System.currentTimeMillis();
            timeCount = searchEndTime - searchStartTime;
            //search finger time 10s
            if (timeCount > 10000) {
                MessageEvent messageEvent = new MessageEvent(TAG, "FingerLoginActivity",
                        "指纹识别超时");
                EventBus.getDefault().post(messageEvent);
                return;
            }
            statues = mFingerHelper.getImage();
            //find finger
            if (statues == mFingerHelper.PS_OK) {
                //gen char to bufferA
                statues = mFingerHelper.genChar(mFingerHelper.CHAR_BUFFER_A);
                if (statues == mFingerHelper.PS_OK) {
                    int[] iMaddr = {0, 0};
                    //is exist flash database,database size = 512
                    statues = mFingerHelper.search(mFingerHelper.CHAR_BUFFER_A, 0, 512, iMaddr);
                    if (statues == mFingerHelper.PS_OK) {
                        //TODO 找到并返回数据库所在位置
                        MessageEvent messageEvent = new MessageEvent(TAG, "FingerLoginActivity",
                                iMaddr[0]);
                        EventBus.getDefault().post(messageEvent);
                    } else {
                        MessageEvent messageEvent = new MessageEvent(TAG, "FingerLoginActivity",
                                "not found");
                        EventBus.getDefault().post(messageEvent);
                    }
                }
            } else if (statues == mFingerHelper.PS_NO_FINGER) {
                mHandler.postDelayed(searchTask, 100);
            } else if (statues == mFingerHelper.PS_GET_IMG_ERR) {
                return;
            } else {
                return;
            }
        }
    };


    /**
     * 将button设置为可用和不可用的状态
     *
     * @param btn button
     * @param b   true为可用,false为不可用
     */
    private void enableButton(Button btn, boolean b) {
        if (b) {
            btn.setClickable(true);
            btn.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            btn.setClickable(false);
            btn.setTextColor(Color.GRAY);
        }
    }


    //    /**
    //     * 初始化方法.
    //     *
    //     * @param activity      activity是因为需要接收获取USB授权操作后的返回
    //     * @param iUsbConnState iUsbConnState实现IUsbConnState接口，
    //     *                      用于接收USB指纹模组的授权状态和连接状态
    //     * @return String
    //     */
    //    private static String init(final Activity activity,
    //                               final IUsbConnState iUsbConnState) {
    //        mFingerHelper = new FingerHelper(activity, iUsbConnState);
    //        returnCode = mFingerHelper.init();
    //        msg = disposeReturnCode(returnCode);
    //        return msg;
    //    }
    //
    //    /**
    //     * 连接USB指纹模组.
    //     *
    //     * @return String
    //     */
    //    private static String connectFingerDev() {
    //        // FIXME: 2017/6/19 阅读示例代码，根据授权的不同给出不同的提示.
    //        returnCode = mFingerHelper.connectFingerDev();
    //        msg = disposeReturnCode(returnCode);
    //        return msg;
    //    }
    //
    //    // TODO: 2017/6/19 方法：获取指纹特征；将指纹特征保存到数据库（登陆人员ID）；搜索指纹特征；关闭设备.
    //    /**
    //     * 根据不同的返回码返回不同的提示.
    //     *
    //     * @param returnCode int类型的返回码
    //     * @return Sring类型的提示
    //     */
    //    private static String disposeReturnCode(final int returnCode) {
    //        String error_msg;
    //        switch (returnCode) {
    //            case (0x00):
    //                error_msg = "操作成功";
    //                break;
    //            case (0x01):
    //                error_msg = "连接成功" + "指令出错";
    //                break;
    //            case (0x02):
    //                error_msg = "未发现手指";
    //                break;
    //            case (0x03):
    //                error_msg = "获取指纹图像出错";
    //                break;
    //            case (0x04):
    //                error_msg = "手指太干躁";
    //                break;
    //            case (0x05):
    //                error_msg = "手指太湿";
    //                break;
    //            case (0x06):
    //                error_msg = "无序指纹";
    //                break;
    //            case (0x07):
    //                error_msg = "生成特征数据过小";
    //                break;
    //            case (0x08):
    //                error_msg = "无法匹配";
    //                break;
    //            case (0x09):
    //                error_msg = "无法搜索";
    //                break;
    //            case (0x0a):
    //                error_msg = "指纹合并出错 ";
    //                break;
    //            case (0x0b):
    //                error_msg = "设备地址覆盖";
    //                break;
    //            case (0x0c):
    //                error_msg = "读取出错";
    //                break;
    //            case (0x0d):
    //                error_msg = "上传模板出错";
    //                break;
    //            case (0x0e):
    //                error_msg = "数据接收出错";
    //                break;
    //            case (0x0f):
    //                error_msg = "上传指纹图像出错";
    //                break;
    //            case (0x10):
    //                error_msg = "删除模板出错";
    //                break;
    //            case (0x11):
    //                error_msg = "清空模板出错";
    //                break;
    //            case (0x12):
    //                error_msg = "休眠出错";
    //                break;
    //            case (0x13):
    //                error_msg = "无效的密钥";
    //                break;
    //            case (0x14):
    //                error_msg = "复位出错";
    //                break;
    //            case (0x15):
    //                error_msg = "无效的指纹图像";
    //                break;
    //            case (0x17):
    //                error_msg = "残留数据不可删除";
    //                break;
    //            default:
    //                error_msg = "未知错误";
    //                break;
    //        }
    //        return error_msg;
    //    }

}
