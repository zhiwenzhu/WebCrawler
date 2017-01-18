package com.zhiwen.crawler.url.store.spi.impl;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.strategy.BloomFilter;
import com.zhiwen.crawler.common.strategy.BloomUtil;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import com.zhiwen.crawler.url.store.spi.UrlMarket;

import java.util.*;

/**
 * Created by zhiwenzhu on 17/1/12.
 */
public class UrlMarketImpl implements UrlMarket {
    private Set<String> urlSet = new HashSet<String>();

    private static final String BLOOM_OBJECT_PATH = DirectoryPath.BLOOM_OBJECT_PATH;

    private Queue<String> urlQueue;

    private BloomFilter bloomFilter = BloomUtil.getFromFile(BLOOM_OBJECT_PATH);

    private static final String URLS_STORE_PATH = DirectoryPath.URL_STORE_PATH;

    private int newUrlNum = 0;

    public void deposit(Collection<String> urls) {
        for (String url : urls) {
            synchronized (urlQueue) {
                if (!hasVisited(url)) {
                    deposit(url);
                    bloomFilter.addUrl(url);
                }
                newUrlNum++;
                if (newUrlNum >= 1000) {
                    BloomUtil.writeToFile(bloomFilter, BLOOM_OBJECT_PATH);
                    writeUrlsToFile(URLS_STORE_PATH, urlSet);
                    urlSet.clear();
                }
            }
        }
    }

    public void deposit(String url) {
        urlSet.add(url);
    }

    public Collection<String> withdraw(int batchSize) {
        Set<String> urls = new HashSet<String>(batchSize);
        for (int i = 0; i < batchSize && i < urlQueue.size(); i ++) {
            String url = urlQueue.poll();

            if (url != null) {
                urls.add(url);
            }
        }

        if (urlQueue.size() == 0) {
            urlQueue = fetchUrlsFromFileToQueue(1000);
        }

        return urls;
    }

//    public boolean hasVisited(String url) {
//        return !urlSet.add(url);
//    }

    public boolean hasVisited(String url) {
        return bloomFilter.contains(url);
    }

    public UrlMarketImpl() {
//        urlSet = new HashSet<String>();

        urlQueue = new LinkedList<String>();
    }

    private void writeUrlsToFile(String filePath, Collection<String> urls) {
        String content = "";
        for (String url : urls) {
            content += url + "\n";
        }

        FileWriteUtil.writeToFile(filePath, content, true);
    }
    private Queue<String> fetchUrlsFromFileToQueue(int batchSize) {
        return FileWriteUtil.getAndRmUrlsFromFile(URLS_STORE_PATH, batchSize);
    }
}
