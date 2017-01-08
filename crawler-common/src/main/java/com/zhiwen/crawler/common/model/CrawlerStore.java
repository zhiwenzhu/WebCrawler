package com.zhiwen.crawler.common.model;

import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * Created by zhiwenzhu on 17/1/8.
 */
public interface CrawlerStore {
    void storeToFile(String filename, byte[] bytes);
}
