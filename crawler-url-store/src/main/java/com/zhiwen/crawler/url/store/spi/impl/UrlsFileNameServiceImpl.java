package com.zhiwen.crawler.url.store.spi.impl;

import com.zhiwen.crawler.url.store.dao.UrlsFileNameDao;
import com.zhiwen.crawler.url.store.model.UrlsFileName;
import com.zhiwen.crawler.url.store.spi.UrlsFileNameService;
import com.zhiwen.crawler.url.store.util.SpringUtil;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chu on 17-1-19.
 */

@Service
public class UrlsFileNameServiceImpl implements UrlsFileNameService {
    @Autowired
    private UrlsFileNameDao urlsFileNameDao;

    public String getLatestFileName() {
        return urlsFileNameDao.getLatestFileName();
    }

    public int addFileName(UrlsFileName ufn) {
        return urlsFileNameDao.addFileName(ufn);
    }

    public static void main(String[] args) {
        UrlsFileNameService urlsFileNameService = SpringUtil.getFileNameService();

        String name = urlsFileNameService.getLatestFileName();

        Assert.assertNotNull(name);

        System.out.println(name);
    }
}
