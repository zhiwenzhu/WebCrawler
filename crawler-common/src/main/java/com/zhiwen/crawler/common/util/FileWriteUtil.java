package com.zhiwen.crawler.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by zhiwenzhu on 17/1/8.
 *
 * 将字节流写进文件内
 */
public class FileWriteUtil {
    public static void writeToFile(File file, byte[] bytes, boolean append) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream ops = new FileOutputStream(file, append);
            ops.write(bytes);
            ops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
