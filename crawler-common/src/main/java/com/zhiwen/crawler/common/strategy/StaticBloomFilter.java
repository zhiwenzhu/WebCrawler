package com.zhiwen.crawler.common.strategy;

import com.zhiwen.crawler.common.config.DirectoryPath;
import org.junit.Assert;

import java.io.*;

/**
 * Created by zhiwenzhu on 17/1/10.
 */
public class StaticBloomFilter {
    private static final String FILE_PATH = DirectoryPath.BLOOM_FILTER_FILE;

    public static BloomFilter bloomFilter;

    public static void writeToFile() {
        try {
            File file = new File(FILE_PATH);
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
            File file = new File(FILE_PATH);
            InputStream ips = new FileInputStream(file);

            ObjectInputStream oips = new ObjectInputStream(ips);

            bloomFilter = (BloomFilter) oips.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bloomFilter;
    }

    public static void main(String[] args) {
        Assert.assertTrue(bloomFilter == null);

        getFromFile();

        Assert.assertNotNull(bloomFilter);
    }

}
