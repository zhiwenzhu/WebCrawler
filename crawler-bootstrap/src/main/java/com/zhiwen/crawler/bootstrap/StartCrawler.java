package com.zhiwen.crawler.bootstrap;


import com.zhiwen.crawler.fetcher.Fetcher;
import com.zhiwen.crawler.fetcher.SimpleFetcher;
import com.zhiwen.crawler.file.parser.HtmlParser;
import com.zhiwen.crawler.file.parser.Parser;
import com.zhiwen.crawler.file.store.spi.FileStore;
import com.zhiwen.crawler.file.store.spi.impl.FileStoreImpl;
import com.zhiwen.crawler.url.store.spi.UrlMarket;
import com.zhiwen.crawler.url.store.spi.impl.UrlMarketImpl;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by zhiwenzhu on 17/1/6.
 */
public class StartCrawler {
    private ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    private Crawler crawler;

    public StartCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    public void startCrawl() {

        while (tpe.getQueue().size() < 1) {
            tpe.submit(new Runnable() {
                public void run() {
                    crawler.crawl();
                }
            });
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        crawler.crawl();
    }

    public static void main(String[] args) throws IOException {
        Fetcher fetcher = new SimpleFetcher();
        UrlMarket urlMarket = new UrlMarketImpl();
        urlMarket.deposit("http://www.163.com");        //seed url
//        urlMarket.deposit("http://bbs.nju.edu.cn/");
//        urlMarket.deposit("https://www.douban.com/");
//        Parser parser = new SimpleParser();

        Parser parser = new HtmlParser();
        FileStore fileStore = new FileStoreImpl();

        Crawler crawler = new Crawler(fetcher, urlMarket, parser, fileStore);
//        crawler.crawl();

        StartCrawler startCrawler = new StartCrawler(crawler);

        startCrawler.startCrawl();


    }
}
