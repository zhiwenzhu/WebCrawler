package com.zhiwen.crawler.common.util;

import com.zhiwen.crawler.common.config.DirectoryPath;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by zhiwenzhu on 17/1/8.
 *
 * 将字节流写进文件内
 */
public class FileWriteUtil {
    private static final String BLANK_STRING = "";
    public static void writeToFile(String filePath, String content, boolean append) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] bytes = content.getBytes();
            OutputStream ops = new FileOutputStream(file, append);
            ops.write(bytes);
            ops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToFileAvoidDuplicat(File file, String content, boolean append) {
        try {
            if (!file.exists()) {
                file.createNewFile();
                byte[] bytes = content.getBytes();
                OutputStream ops = new FileOutputStream(file, append);
                ops.write(bytes);
                ops.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Queue<String> getAndRmUrlsFromFile(String filePath) {
        Queue<String> urlQueue = new LinkedList<String>();
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String s = "";

            while ((s = reader.readLine()) != null) {
                urlQueue.add(s);
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return urlQueue;
    }

    public static void main(String[] args) {

    }
}
