package com.zhiwen.crawler.url.store.dao;

import com.zhiwen.crawler.url.store.model.Urls;

import java.util.List;

/**
 * Created by zhiwenzhu on 17/1/5.
 */
public interface UrlsDao {
    int addUrls(Urls urls);

    int addUrlss(List<Urls> urlss);

    Urls getUrlsById(int id);

    List<Urls> getUrlsByParentUrl(String parentUrl);

    Urls getUrlsByUrl(String url);
}
