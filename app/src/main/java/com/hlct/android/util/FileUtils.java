package com.hlct.android.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : mengyuan.cheng
 * @Version : 2017/7/24
 * @E-mail : mengyuan.cheng.mier@gmail.com
 * @Description :文件读写工具类
 */

public class FileUtils {

    private static final String LINE_SEP = System.getProperty("line.separator");

    /**
     * @param file
     * @return
     */
    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 根据文件的路径判断文件是否存在
     *
     * @param filePath 文件路径
     * @return 返回true表示文件存在，false表示文件不存在
     */
    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param s
     * @return
     */
    private static boolean isSpace(final String s) {
        if (s == null)
            return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
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
            fos.flush();
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
     * 将字符串写入文件中。
     *
     * @param filePath 文件路径名
     * @param content  要写入的内容
     * @param append   true 表示追加内容，false 表示替换内容
     */
    public static boolean writeStringToTxt(String filePath, String content, boolean append) {
        boolean res = true;
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file, append);
            if (append) {
                content = System.getProperty("line.separator") + content;
            }
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            res = false;
            ex.printStackTrace();
        }
        return res;
    }



    /**
     * 将文本文件中的内容读入到buffer中
     *
     * @param buffer   buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    /**
     * 读取文本文件内容
     *
     * @param filePath 文件所在路径
     * @return 文本内容
     * @throws IOException 异常
     */
    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        FileUtils.readToBuffer(sb, filePath);
        return sb.toString();
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

    /**
     * 关闭IO
     *
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null)
            return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 使文件生成以后立即在电脑中可见
     *
     * @param context  传入activity
     * @param filePath 传入file对象的文件路径
     */
    public static void makeFileAvailable(Context context, String filePath) {
        MediaScannerConnection.scanFile(context, new String[]{filePath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d("context", "File scanned" + path);
                    }
                });

    }
}
