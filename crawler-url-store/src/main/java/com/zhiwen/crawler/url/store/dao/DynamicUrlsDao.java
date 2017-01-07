package com.zhiwen.crawler.url.store.dao;

import com.zhiwen.crawler.url.store.model.DynamicUrls;

/**
 * Created by zhiwenzhu on 17/1/7.
 */
public interface DynamicUrlsDao {
    int addUrls(DynamicUrls dynamicUrls);
}
