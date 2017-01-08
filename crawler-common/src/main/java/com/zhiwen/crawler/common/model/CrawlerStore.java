package com.zhiwen.crawler.common.model;

/**
 * Created by zhiwenzhu on 17/1/8.
 */
public interface CrawlerStore {
    void storeToFile(String filename, byte[] bytes);
}
