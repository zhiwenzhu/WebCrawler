package com.zhiwen.crawler.url.store.spi;

import com.zhiwen.crawler.url.store.model.UrlsFileName;

import java.util.LinkedList;

/**
 * Created by chu on 17-1-19.
 */
public interface UrlsFileNameService {
    String getFirstFileName();

    int addFileName(UrlsFileName ufn);

    int deleteFirstFileName(String name);

    LinkedList<String> getFilesNameForTest();
}
