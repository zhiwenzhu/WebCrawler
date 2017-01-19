package com.zhiwen.crawler.url.store.dao;

import com.zhiwen.crawler.url.store.model.UrlsFileName;

/**
 * Created by chu on 17-1-19.
 */
public interface UrlsFileNameDao {
    String getLatestFileName();

    int addFileName(UrlsFileName ufn);
}
