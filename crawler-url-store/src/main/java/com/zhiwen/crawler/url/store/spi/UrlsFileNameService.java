package com.zhiwen.crawler.url.store.spi;

import com.zhiwen.crawler.url.store.model.UrlsFileName;

/**
 * Created by chu on 17-1-19.
 */
public interface UrlsFileNameService {
    String getLatestFileName();

    int addFileName(UrlsFileName ufn);
}
