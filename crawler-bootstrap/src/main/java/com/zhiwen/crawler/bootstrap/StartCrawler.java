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

    public static void main(String[] args) throws IOException {
        Fetcher fetcher = new SimpleFetcher();
        UrlMarket urlMarket = new UrlMarketImpl();
        urlMarket.deposit("http://www.163.com");        //seed url
        Parser parser = new HtmlParser();
        FileStore fileStore = new FileStoreImpl();

        Thread.currentThread().setPriority(Thread.NORM_PRIORITY);

        Crawler crawler = new Crawler(fetcher, urlMarket, parser, fileStore);
        crawler.crawl();

    }
}
