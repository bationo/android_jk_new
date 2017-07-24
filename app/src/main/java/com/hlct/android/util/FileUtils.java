package com.hlct.android.util;

import android.util.Log;

import com.hlct.framework.pda.common.entity.ResultInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/7/24
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description :文件读写工具类
 */

public class FileUtils {
    /**
     * 判断文件是否存在
     *
     * @param path：文件路径
     * @return boolean
     */
    public static boolean isExist(String path) {
        File file = new File(path);
        boolean status = file.exists();
        Log.d("isExist", "" + status);
        return status;
    }

    /**
     * 如果文件不存在，就创建文件
     *
     * @param path 文件路径
     * @return 文件路径
     */
    public static String createIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    /**
     * 向文件中写入数据
     *
     * @param filePath 目标文件全路径
     * @param data     要写入的数据
     * @return true表示写入成功  false表示写入失败
     */
    public static boolean writeBytes(String filePath, byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(data);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 向文件中写入字符串String类型的内容
     *
     * @param file    文件路径
     * @param content 文件内容
     * @param charset 写入时候所使用的字符集
     */
    public static void writeString(String file, String content, String charset) {
        try {
            byte[] data = content.getBytes(charset);
            writeBytes(file, data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 从文件中读取数据，返回类型是ResultInfo类型
     *
     * @param file 文件路径
     * @return
     */
    public static Object readString(String file) {
        ResultInfo ret = null;
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream obj = new ObjectInputStream(in);
            ret = (ResultInfo) obj.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取时间戳
     *
     * @return yyyyMMdd
     */
    public static String getDate() {
        String str = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date curDate = new Date(System.currentTimeMillis());
            str = format.format(curDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
