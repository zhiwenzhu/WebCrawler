package com.zhiwen.crawler.url.store.spi.impl;

import com.zhiwen.crawler.url.store.dao.UrlsDao;
import com.zhiwen.crawler.url.store.model.Urls;
import com.zhiwen.crawler.url.store.spi.UrlsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by zhiwenzhu on 17/1/5.
 */
@Deprecated
@Service("UrlsService")
public class UrlsServiceImpl implements UrlsService {

    @Autowired
    private UrlsDao urlsDao;

    public int addUrlss(List<Urls> urlss) {
        return urlsDao.addUrlss(urlss);
    }

    public Urls getUrlsById(int id) {
        return urlsDao.getUrlsById(id);
    }

    public List<Urls> getUrlsByParentUrl(String parentUrl) {
        return urlsDao.getUrlsByParentUrl(parentUrl);
    }

    public int addUrls(Urls urls) {
        return urlsDao.addUrls(urls);
    }

    public Urls getUrlsByUrl(String url) {
        return urlsDao.getUrlsByUrl(url);
    }
}
