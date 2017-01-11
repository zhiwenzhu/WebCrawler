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
    private static final String START_FILE = "startup.txt";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final String DATE_STRING = DATE_FORMAT.format(new Date());

    private void writeToCrawlerFile() {
        String filePath = DirectoryPath.Next_CRAWLER_FILE_NAME;
        String fileContent = DirectoryPath.URL_STORE_PATH + DATE_STRING + "/" + START_FILE;

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


    /**
     * 创建两个BloomFilter对象（写进两个文件），
     * pageBloomFilter用来判断是否重复爬取某个页面；
     * urlBloomFilter用来判断页面解析后url的集合是否有重复存储
     */
    private void writeNewBloomFilter() {
        BloomFilter pageBloomFilter = new BloomFilter();
        BloomFilter urlBloomFilter = new BloomFilter();

        pageBloomFilter.addUrl(FIRST_URL);
        urlBloomFilter.addUrl(START_FILE);
        urlBloomFilter.addUrl(FIRST_URL);

        writeNewObjectFile(DirectoryPath.PAGE_BLOOM_OBJECT_PATH, pageBloomFilter);

        writeNewObjectFile(DirectoryPath.URL_BLOOM_OBJECT_PATH, urlBloomFilter);

    }

    private void writeNewObjectFile(String filePath, Object o) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            OutputStream ops = new FileOutputStream(file);
            ObjectOutputStream oops = new ObjectOutputStream(ops);

            oops.writeObject(o);
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
        hp.run();
        writeNewBloomFilter();
    }

    public static void main(String[] args) {
        InitializeNewCrawler initializeNewCrawler = new InitializeNewCrawler();
        initializeNewCrawler.initial();
    }
}
