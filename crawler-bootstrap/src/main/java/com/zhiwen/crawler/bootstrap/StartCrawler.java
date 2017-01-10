package com.zhiwen.crawler.bootstrap;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.strategy.BloomFilter;
import com.zhiwen.crawler.common.strategy.GetSeedUrlsStrategy;
import com.zhiwen.crawler.common.strategy.StaticBloomFilter;
import com.zhiwen.crawler.file.parser.HtmlContentParser;
import com.zhiwen.crawler.file.parser.util.SpringBeanUtil;
import com.zhiwen.crawler.file.store.spi.FileMessageService;
import com.zhiwen.crawler.url.store.dao.CrawlerIndexDao;
import com.zhiwen.crawler.url.store.spi.UrlsService;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhiwenzhu on 17/1/6.
 */
public class StartCrawler extends Thread {
    private int count = 0;

    public static int crawlerIndex = 0;

    private UrlsService urlsService = SpringBeanUtil.getUrlsService();

    private FileMessageService fileMessageService = SpringBeanUtil.getFileMessageService();

    private CrawlerIndexDao crawlerIndexDao = SpringBeanUtil.getCrawlerIndexDao();

    private static long beginTime = System.currentTimeMillis();

    private static final int INTERVAL_OF_WRITE_BF = 1000 * 60 * 5;

//    public void getLastCrawlerIndex() {
//        int maxFileMessageId = fileMessageService.getMaxId();
//        String lastCrawlerUrl = "";
//
//        if (maxFileMessageId > 0) {
//            lastCrawlerUrl = fileMessageService.getFileMessageById(maxFileMessageId).getUrl();
//            if (StringUtils.isNotBlank(lastCrawlerUrl)) {
//                lastCrawlerIndex = urlsService.getUrlsByUrl(lastCrawlerUrl).getId();
//            }
//        }
//    }

    private void getCrawlerIndex() {
        crawlerIndex = crawlerIndexDao.getCrawlerIndex();
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程开始执行");
//        if (crawlerIndex == 0) {
//            getCrawlerIndex();
//        }
//        String toCrawlerUrl = "";
//        for ( ; ; ) {
//            Urls urls = urlsService.getUrlsById(crawlerIndex);
//            if (urls != null && StringUtils.isNotBlank(urls.getUrl())) {
//                toCrawlerUrl = urls.getUrl();
//                break;
//            }
//        }
//        HtmlContentParser hp = new HtmlContentParser();
//        hp.setUrl(toCrawlerUrl);
//        hp.run();
//                crawlerIndex++;
//                crawlerIndexDao.updateIndex(crawlerIndex);
//        run();

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
                    return;
                }
            }
        }


        if (StringUtils.isNotBlank(toCrawlerUrlFile)) {
            List<String> toCrawlerUrls = gsus.getToCrawlerUrl(toCrawlerUrlFile);
            System.out.println(toCrawlerUrls.size() + "条url开始被爬取");
            HtmlContentParser hp = new HtmlContentParser();
            for (String url : toCrawlerUrls) {
                count++;
                if (!bloomFilter.contains(url)) {
                    hp.setUrl(url);
                    hp.run();
                    System.out.println(count + ":" + url + "是新爬取的");
                    bloomFilter.addUrl(url);

                }
                else {
                    System.out.println(url + "已经爬取过");
//                    System.out.println(count);
//                    String s = url.replace("http://", "").replace("/", "_");
//                    System.out.println(s);
                }

                System.out.println(Thread.currentThread().getName() + ":" + count);
                try {
                    sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long currentTime = System.currentTimeMillis();
                if ((currentTime -beginTime) >= 1000 * 60 * 5) {
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

    private BloomFilter getBloomFilter() {
        BloomFilter bloomFilter = StaticBloomFilter.bloomFilter;

        if (bloomFilter == null) {
            bloomFilter = StaticBloomFilter.getFromFile();
        }

        return bloomFilter;
    }

    public static void main(String[] args) {


        StartCrawler sc = new StartCrawler();

        Thread threadOne = new Thread(sc, "one");
        Thread threadTwo = new Thread(sc, "two");
        Thread threadThree = new Thread(sc, "three");
        Thread threadFour = new Thread(sc, "four");
        Thread threadFive = new Thread(sc, "five");
        Thread threadSix = new Thread(sc, "six");
        Thread threadSeven = new Thread(sc, "seven");
        Thread threadEight = new Thread(sc, "eight");
        Thread threadNine = new Thread(sc, "nine");

//        sc.run();

        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadFour.start();
        threadFive.start();
        threadSix.start();
        threadSeven.start();
        threadEight.start();
        threadNine.start();

    }
}
