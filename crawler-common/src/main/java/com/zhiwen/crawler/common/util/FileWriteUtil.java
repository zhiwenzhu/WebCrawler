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

    public static Queue<String> getAndRmUrlsFromFile(String filePath, int batchSize) {
        Queue<String> urlQueue = new LinkedList<String>();

        File file = new File(filePath);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String s = "";
            int i = 0;
            while (i < batchSize && (s = reader.readLine()) != null) {
                urlQueue.add(s);
                i ++;
            }

            String tempFileContent = "";
            String tempFilePath = "/crawler_url_store/temp";
            while ((s = reader.readLine()) != null) {
                tempFileContent += s + "\n";
                i ++;   //for test
            }

            if (StringUtils.isNotBlank(tempFileContent)) {
                writeToFile(tempFilePath, tempFileContent, false);

                file.renameTo(new File("/crawler_url_store/dump_urls"));

                File tempFile = new File(tempFilePath);
                tempFile.renameTo(new File(filePath));
            } else {
                writeToFile(filePath, BLANK_STRING, false);
                System.out.println("清空urls文件：" + i + "条url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return urlQueue;
    }

    public static void main(String[] args) {

    }
}
