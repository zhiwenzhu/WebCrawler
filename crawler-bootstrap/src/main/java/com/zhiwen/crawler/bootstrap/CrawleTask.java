package com.zhiwen.crawler.bootstrap;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.strategy.BloomFilter;
import com.zhiwen.crawler.common.strategy.GetSeedUrlsStrategy;
import com.zhiwen.crawler.common.strategy.StaticBloomFilter;
import com.zhiwen.crawler.file.parser.HtmlContentParser;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public class CrawleTask implements Runnable {

    private int count = 0;

    private int newPage = 0;

    private static long beginTime = System.currentTimeMillis();

    private static final int INTERVAL_OF_WRITE_BF = 1000 * 60 * 5;



    private BloomFilter getBloomFilter() {
        BloomFilter bloomFilter = StaticBloomFilter.bloomFilter;

        if (bloomFilter == null) {
            bloomFilter = StaticBloomFilter.getFromFile();
        }
        return bloomFilter;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程开始执行");


        BloomFilter bloomFilter = getBloomFilter();

        String toCrawlerUrlFile = "";
        GetSeedUrlsStrategy gsus = new GetSeedUrlsStrategy();
        synchronized (this) {
            try {
                File file = new File(DirectoryPath.Next_CRAWLER_FILE_NAME);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                toCrawlerUrlFile = reader.readLine();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (StringUtils.isNotBlank(toCrawlerUrlFile)) {
                String newPath = gsus.getToCrawlerMessage(toCrawlerUrlFile);
                if (StringUtils.isNotBlank(newPath)) {
                    File file = new File(DirectoryPath.Next_CRAWLER_FILE_NAME);
                    try {
                        OutputStream ops = new FileOutputStream(file);
                        ops.write(newPath.getBytes());
                        ops.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
//                    return;
                }
            }
        }


        if (StringUtils.isNotBlank(toCrawlerUrlFile)) {
            List<String> toCrawlerUrls = gsus.getToCrawlerUrl(toCrawlerUrlFile);
            System.out.println(toCrawlerUrls.size() + "条url开始被爬取");
            HtmlContentParser hp = new HtmlContentParser();
            for (String url : toCrawlerUrls) {
                count++;
//                if (!urlSet.contains(url)) {
//                    hp.setUrl(url);
//                    hp.run();
//                    newPage ++;
//                    System.out.println(Thread.currentThread().getName() + ":" + count + ":" + url + "是新爬取的"
//                                       + "（" + newPage +"）");
//
//                    urlSet.add(url);
//                }


                if (!bloomFilter.contains(url)) {
                    hp.setUrl(url);
                    hp.run();
                    newPage++;
                    System.out.println(Thread.currentThread().getName() + ":" + count + ":" + url + "是新爬取的"
                            + "（" + newPage + "）");
                    bloomFilter.addUrl(url);

                } else {
                    System.out.println(Thread.currentThread().getName() + ":" + count + url + "已经爬取过");
                }

                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long currentTime = System.currentTimeMillis();
                if ((currentTime - beginTime) >= INTERVAL_OF_WRITE_BF) {
                    StaticBloomFilter.writeToFile();
                    beginTime = currentTime;
                }
            }

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date date = new Date();
            String dateString = format.format(date);

            System.out.println("截止" + dateString + ": 已爬取" + count + "个页面");

            System.out.println(Thread.currentThread().getName() + "线程执行完毕");
            run();
        }

    }
}
