package com.zhiwen.crawler.bootstrap;

import com.zhiwen.crawler.TestSite;
import com.zhiwen.crawler.fetcher.Fetcher;
import com.zhiwen.crawler.fetcher.SimpleFetcher;
import com.zhiwen.crawler.file.parser.HtmlParser;
import com.zhiwen.crawler.file.parser.Parser;
import com.zhiwen.crawler.file.store.spi.FileStore;
import com.zhiwen.crawler.file.store.spi.impl.FileStoreImpl;
import com.zhiwen.crawler.url.store.spi.UrlMarket;
import com.zhiwen.crawler.url.store.spi.impl.UrlMarketImpl;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

/**
 * Created by zhengwenzhu on 2017/2/9.
 */
public class CrawlerTest extends TestCase {


    @Before
    public void setUp() throws Exception {
        TestSite.start();
    }

    @After
    public void shutdown() throws Exception {
        TestSite.shutdown();
    }

    public void testCrawl() throws Exception {
        Fetcher fetcher = new SimpleFetcher();
        UrlMarket urlMarket = new UrlMarketImpl();
        urlMarket.deposit("http://localhost:4096");        //seed url
        Parser parser = new HtmlParser();
        FileStore fileStore = new FileStoreImpl();

        Crawler crawler = new Crawler(fetcher, urlMarket, parser, fileStore);
        crawler.crawl();
    }
}