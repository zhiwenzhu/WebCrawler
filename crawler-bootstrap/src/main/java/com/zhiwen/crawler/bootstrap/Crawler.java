package com.zhiwen.crawler.bootstrap;

import com.zhiwen.crawler.common.model.Page;
import com.zhiwen.crawler.fetcher.Fetcher;
import com.zhiwen.crawler.file.parser.Parser;
import com.zhiwen.crawler.file.store.spi.FileStore;
import com.zhiwen.crawler.url.store.spi.UrlMarket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public class Crawler {

    private Fetcher fetcher;

    private UrlMarket urlMarket;

    private FileStore fileStore;

    private Parser parser;

    private ExecutorService es = Executors.newFixedThreadPool(20);

    public Crawler(Fetcher fetcher, UrlMarket urlMarket, Parser parser, FileStore fileStore) {
        this.fetcher = fetcher;
        this.urlMarket = urlMarket;
        this.parser = parser;
        this.fileStore = fileStore;
    }

    public void crawl() {
        crawl(new String[]{});
    }

    public void crawl(String... seedUrls) {
        saveSeed(seedUrls);
        Collection<String> urls = urlMarket.withdraw();
        List<Future> results = new ArrayList<Future>(urls.size());
        for (String url : urls) {
            try {
                process(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        waitAllDone(results);
    }

    private void waitAllDone(List<Future> results) {
        for (Future result : results) {
            try {
                result.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Future process(final String url) throws IOException {
        return es.submit(new Callable<Object>() {
            public Object call() throws Exception {
                String content = fetcher.fetch(url);
                Page page = parser.parse(url, content);
                urlMarket.deposit(page.getUrls());
                fileStore.save(url, page);
                return null;
            }
        });
    }

    private void saveSeed(String... seedUrls) {
        for (String url : seedUrls) {
            urlMarket.deposit(url);
        }
    }

}
