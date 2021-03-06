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

import java.util.*;


/**
 * Created by zhiwenzhu on 17/1/12.
 */
public class UrlMarketImpl implements UrlMarket {
    private UrlsFileNameService ufs = SpringUtil.getFileNameService();

    private Set<String> urlSet;

    private static final String BLOOM_OBJECT_PATH = DirectoryPath.BLOOM_FILTER_STORE_PATH;

    private Queue<String> urlQueue;

    private BloomFilter bloomFilter = BloomUtil.getFromFile(BLOOM_OBJECT_PATH);

    private static final String URL_STORE_PATH = DirectoryPath.URL_STORE_PATH;

    private int writeNameToFileCount = 0;

    public synchronized void deposit(Collection<String> urls) {
        synchronized (urlSet) {
            for (String url : urls) {
                if (!hasVisited(url)) {
                    urlSet.add(url);
                    bloomFilter.addUrl(url);
                }

                if (urlSet.size() >= 1000) {
                    writeNameToFileCount ++;
                    String fileName = FileNameGenerator.nextId();
                    System.out.println("开始写url到文件" + writeNameToFileCount);
                    writeUrlsToFile(URL_STORE_PATH + fileName, urlSet);
                    System.out.println("写url到文件完成");
                    System.out.println("开始写文件名到database");
                    addFileNameToDataBase(fileName);                 //出现bug:写fileName到database会造成除主线程外的所有线程阻塞
                    System.out.println("写文件名到database完成");
                    urlSet.clear();

                }

                //存储十次url(即10万条url),写一次bloomfilter到文件中
                if (writeNameToFileCount >= 100) {
                    System.out.println("开始写到bloomFilter");
                    BloomUtil.writeToFile(bloomFilter, BLOOM_OBJECT_PATH);
                    System.out.println("写到bloomFilter完成");
                    writeNameToFileCount = 0;
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

    public  Collection<String> withdraw(int batchSize) {
        synchronized (urlQueue) {
            if (urlQueue.size() == 0) {
                urlQueue = fetchUrlsFromFileToQueue();
                if (urlQueue == null || urlQueue.size() == 0) {
                    urlQueue = new LinkedList<String>();
                    urlQueue.addAll(urlSet);
                    urlSet.clear();
                }
            }

            Set<String> urls = new HashSet<String>(batchSize);
            int size = urlQueue.size();
            for (int i = 0; i < batchSize && i < size; i ++) {
                String url = urlQueue.poll();

                if (url != null) {
                    urls.add(url);
                }
            }

            return urls;
        }
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
            urls = FileWriteUtil.getAndRmUrlsFromFile(URL_STORE_PATH + name);
        }
        return urls;
    }

    private String getLatestUrlsFileName() {

        String name = ufs.getFirstFileName();
        if (StringUtils.isNotBlank(name)) {
            ufs.deleteFirstFileName(name);
        }
        return name;
    }

    private void addFileNameToDataBase(String fileName) {
        UrlsFileName ufn = new UrlsFileName();

        ufn.setName(fileName);

        ufs.addFileName(ufn);
    }
}
