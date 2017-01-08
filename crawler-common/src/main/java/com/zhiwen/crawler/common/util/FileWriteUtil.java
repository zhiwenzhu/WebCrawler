package com.zhiwen.crawler.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by zhiwenzhu on 17/1/8.
 */
public class FileWriteUtil {
    public static void writeToFile(File file, byte[] bytes) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream ops = new FileOutputStream(file);
            ops.write(bytes);
            ops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
