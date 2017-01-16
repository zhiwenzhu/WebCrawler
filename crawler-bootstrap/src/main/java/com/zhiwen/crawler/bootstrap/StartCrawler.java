package com.zhiwen.crawler.bootstrap;


import com.zhiwen.crawler.fetcher.Fetcher;
import com.zhiwen.crawler.fetcher.SimpleFetcher;
import com.zhiwen.crawler.file.parser.Parser;
import com.zhiwen.crawler.file.parser.SimpleParser;
import com.zhiwen.crawler.file.store.spi.FileStore;
import com.zhiwen.crawler.file.store.spi.impl.FileStoreImpl;
import com.zhiwen.crawler.url.store.spi.UrlMarket;
import com.zhiwen.crawler.url.store.spi.impl.UrlMarketImpl;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhiwenzhu on 17/1/6.
 */
public class StartCrawler {


    public static final int THREAD_POOL_SIZE = 10;

    private static final ExecutorService es = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private static Fetcher fetcher;

    private static UrlMarket urlMarket;

    private static FileStore fileStore;

    private static Parser parser;

    private static final class CrawlerTask implements Callable {

        public Object call() throws Exception {
            Crawler crawler = new Crawler(fetcher, urlMarket, parser, fileStore);
            crawler.crawl();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        fetcher = new SimpleFetcher();
        urlMarket = new UrlMarketImpl();
        parser = new SimpleParser();
        fileStore = new FileStoreImpl();

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            es.submit(new CrawlerTask());
        }

    }
}
