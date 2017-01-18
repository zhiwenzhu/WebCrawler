package com.zhiwen.crawler.url.store.spi.impl;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.strategy.BloomFilter;
import com.zhiwen.crawler.common.strategy.BloomUtil;
import com.zhiwen.crawler.url.store.spi.UrlMarket;

import java.util.*;

/**
 * Created by zhiwenzhu on 17/1/12.
 */
public class UrlMarketImpl implements UrlMarket {
//    private Set<String> urlSet;
    private static final String BLOOM_OBJECT_PATH = DirectoryPath.BLOOM_OBJECT_PATH;

    private Queue<String> urlQueue;

    private BloomFilter bloomFilter = BloomUtil.getFromFile(BLOOM_OBJECT_PATH);

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
                }
            }
        }
    }

    public void deposit(String url) {
        urlQueue.add(url);
    }

    public Collection<String> withdraw(int batchSize) {
        Set<String> urls = new HashSet<String>(batchSize);
        for (int i = 0; i < batchSize && i < urlQueue.size(); i ++) {
            String url = urlQueue.poll();

            if (url != null) {
                urls.add(url);
            }
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
}
