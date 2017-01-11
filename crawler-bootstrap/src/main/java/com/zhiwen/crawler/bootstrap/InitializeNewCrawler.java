package com.zhiwen.crawler.bootstrap;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.strategy.BloomFilter;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import com.zhiwen.crawler.file.parser.HtmlContentParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhiwenzhu on 17/1/11.
 */
public class InitializeNewCrawler {
    private static final String FIRST_URL = "www.163.com";
    private static final String START_FILE = "startup.txt\nwww.163.com";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final String DATE_STRING = DATE_FORMAT.format(new Date());

    private static final String BLOOM_OBJECT_PATH = DirectoryPath.BLOOM_FILTER_FILE;

    private void writeToCrawlerFile() {
        String filePath = DirectoryPath.Next_CRAWLER_FILE_NAME;
        String fileContent = DirectoryPath.URL_STORE_PATH + DATE_STRING + "/" + FIRST_URL;

        File file = new File(filePath);
        FileWriteUtil.writeToFile(file, fileContent.getBytes(), false);
    }

    private void writeToUrlDateDirFile() {
        String filePath = DirectoryPath.URL_DATE_DIR_STORE_FILE;
        String fileContent = DATE_STRING;

        File file = new File(filePath);
        FileWriteUtil.writeToFile(file, fileContent.getBytes(), true);
    }

    private void writeToPerDayUrlDtoreFile() {
        String filePath = DirectoryPath.CONFIG_STORE_PATH + DirectoryPath.DATE_URL_FILE_PATH + DATE_STRING;
        String fileContent = START_FILE;

        File file = new File(filePath);
        FileWriteUtil.writeToFile(file, fileContent.getBytes(), false);
    }

    private void writeToBloomFilter() {

        try {
            BloomFilter bloomFilter = new BloomFilter();

            bloomFilter.addUrl(FIRST_URL);
            File file = new File(BLOOM_OBJECT_PATH);

            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream ops = new FileOutputStream(file);

            ObjectOutputStream oops = new ObjectOutputStream(ops);

            oops.writeObject(bloomFilter);

            oops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initial() {
        writeToCrawlerFile();
        writeToPerDayUrlDtoreFile();
        writeToUrlDateDirFile();
        HtmlContentParser hp = new HtmlContentParser();
        hp.setUrl(FIRST_URL);
        hp.start();
        writeToBloomFilter();
    }

    public static void main(String[] args) {
        InitializeNewCrawler initializeNewCrawler = new InitializeNewCrawler();
        initializeNewCrawler.initial();
    }
}
