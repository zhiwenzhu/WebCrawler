package com.zhiwen.crawler.url.store.dao;

/**
 * Created by zhiwenzhu on 17/1/7.
 */
public interface CrawlerIndexDao {
    int updateIndex(int urlId);

    int getCrawlerIndex();


}
