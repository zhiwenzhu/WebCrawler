package com.zhiwen.crawler.common.strategy;

import java.io.*;

/**
 * Created by zhiwenzhu on 17/1/10.
 */
public class BloomUtil {

    public static void writeToFile(BloomFilter bloomFilter, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream fops = new FileOutputStream(file);
            ObjectOutputStream oops = new ObjectOutputStream(fops);
            oops.writeObject(bloomFilter);

            oops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BloomFilter getFromFile(String filePath) {
        BloomFilter bloomFilter = null;
        try {
            File file = new File(filePath);
            InputStream ips = new FileInputStream(file);

            ObjectInputStream oips = new ObjectInputStream(ips);

            bloomFilter = (BloomFilter) oips.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bloomFilter;
    }
}
