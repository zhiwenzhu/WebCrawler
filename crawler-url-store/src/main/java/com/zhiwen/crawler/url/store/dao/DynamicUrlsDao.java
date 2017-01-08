package com.zhiwen.crawler.url.store.dao;

import com.zhiwen.crawler.url.store.model.DynamicUrls;

/**
 * Created by zhiwenzhu on 17/1/7.
 *
 * 测试$符号在mybatis 的sql语句中应用，无实际用处
 */
public interface DynamicUrlsDao {
    int addUrls(DynamicUrls dynamicUrls);
}
