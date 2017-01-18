package com.zhiwen.crawler.url.store.spi.impl;

import com.zhiwen.crawler.common.strategy.BloomFilter;
import com.zhiwen.crawler.url.store.spi.UrlMarket;

import java.util.*;

/**
 * Created by zhiwenzhu on 17/1/12.
 */
public class UrlMarketImpl implements UrlMarket {
//    private Set<String> urlSet;

    private Queue<String> urlQueue;

    private BloomFilter bloomFilter;

    public void deposit(Collection<String> urls) {
        for (String url : urls) {
            synchronized (urlQueue) {
                if (!hasVisited(url)) {
                    urlQueue.add(url);
                    bloomFilter.addUrl(url);
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

        bloomFilter = new BloomFilter();
    }

}
