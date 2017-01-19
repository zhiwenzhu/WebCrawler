package com.zhiwen.crawler.url.store.spi.impl;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.strategy.BloomFilter;
import com.zhiwen.crawler.common.strategy.BloomUtil;
import com.zhiwen.crawler.common.util.FileNameGenerator;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import com.zhiwen.crawler.url.store.model.UrlsFileName;
import com.zhiwen.crawler.url.store.spi.UrlMarket;
import com.zhiwen.crawler.url.store.spi.UrlsFileNameService;
import com.zhiwen.crawler.url.store.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by zhiwenzhu on 17/1/12.
 */
public class UrlMarketImpl implements UrlMarket {
    private UrlsFileNameService ufs = SpringUtil.getFileNameService();

    private Set<String> urlSet;

    private static final String BLOOM_OBJECT_PATH = DirectoryPath.BLOOM_OBJECT_PATH;

    private Queue<String> urlQueue;

    private BloomFilter bloomFilter = BloomUtil.getFromFile(BLOOM_OBJECT_PATH);

    private static final String URLS_STORE_PATH = DirectoryPath.URL_STORE_PATH;


    public synchronized void deposit(Collection<String> urls) {
        synchronized (urlSet) {
            for (String url : urls) {
                if (!hasVisited(url)) {
                    urlSet.add(url);
                    bloomFilter.addUrl(url);
                }

                if (urlSet.size() >= 1000) {
                    BloomUtil.writeToFile(bloomFilter, BLOOM_OBJECT_PATH);
                    String fileName = FileNameGenerator.nextId();
                    writeUrlsToFile(URLS_STORE_PATH + fileName, urlSet);
                    addFileNameToDataBase(fileName);
                    urlSet.clear();
                }
            }
        }
    }

    public void deposit(String url) {
        if (!hasVisited(url)) {
            urlSet.add(url);
            bloomFilter.addUrl(url);
        }
    }

    public Collection<String> withdraw(int batchSize) {
        if (urlQueue.size() == 0) {
            urlQueue = fetchUrlsFromFileToQueue();
            if (urlQueue == null || urlQueue.size() == 0) {
                urlQueue = new LinkedList<String>();
                urlQueue.addAll(urlSet);
                urlSet.clear();
            }
        }

        Set<String> urls = new HashSet<String>(batchSize);
        for (int i = 0; i < batchSize && i < urlQueue.size(); i ++) {
            String url = urlQueue.poll();

            if (url != null) {
                urls.add(url);
            }
        }

        return urls;
    }

    public boolean hasVisited(String url) {
        return bloomFilter.contains(url);
    }

    public UrlMarketImpl() {
        urlSet = new HashSet<String>();

        urlQueue = new LinkedList<String>();
    }

    private void writeUrlsToFile(String filePath, Collection<String> urls) {
        String content = "";
        for (String url : urls) {
            content += url + "\n";
        }

        FileWriteUtil.writeToFile(filePath, content, true);
    }
    private Queue<String> fetchUrlsFromFileToQueue() {
        String name = getLatestUrlsFileName();
        Queue<String> urls = null;
        if (StringUtils.isNotBlank(name)) {
            urls = FileWriteUtil.getAndRmUrlsFromFile(URLS_STORE_PATH + name);
        }
        return urls;
    }

    private String getLatestUrlsFileName() {

        String name = ufs.getLatestFileName();

        return name;
    }

    private void addFileNameToDataBase(String fileName) {
        UrlsFileName ufn = new UrlsFileName();

        ufn.setName(fileName);

        ufs.addFileName(ufn);
    }
}
