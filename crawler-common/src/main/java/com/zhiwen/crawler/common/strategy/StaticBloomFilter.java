package com.zhiwen.crawler.common.strategy;

import com.zhiwen.crawler.common.config.DirectoryPath;

import java.io.*;

/**
 * Created by zhiwenzhu on 17/1/10.
 */
public class StaticBloomFilter {

    public static BloomFilter bloomFilter;

    private static final String BLOOM_OBJECT_PATH = DirectoryPath.BLOOM_OBJECT_PATH;

    public static void writeToFile() {
        try {
            File file = new File(BLOOM_OBJECT_PATH);
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

    public static BloomFilter getFromFile() {
        try {
            File file = new File(BLOOM_OBJECT_PATH);
            InputStream ips = new FileInputStream(file);

            ObjectInputStream oips = new ObjectInputStream(ips);

            bloomFilter = (BloomFilter) oips.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bloomFilter;
    }

    public static void main(String[] args) {
//        Assert.assertTrue(bloomFilter == null);

//        getFromFile();

//        Assert.assertNotNull(bloomFilter);
    }

}
