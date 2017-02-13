package com.zhiwen.crawler.url.store.spi.impl;

import com.zhiwen.crawler.url.store.dao.UrlsFileNameDao;
import com.zhiwen.crawler.url.store.model.UrlsFileName;
import com.zhiwen.crawler.url.store.spi.UrlsFileNameService;
import com.zhiwen.crawler.url.store.util.SpringUtil;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chu on 17-1-19.
 */

@Service
public class UrlsFileNameServiceImpl implements UrlsFileNameService {
    @Autowired
    private UrlsFileNameDao urlsFileNameDao;

    public String getFirstFileName() {
        return urlsFileNameDao.getFirstFileName();
    }

    public int addFileName(UrlsFileName ufn) {
        return urlsFileNameDao.addFileName(ufn);
    }

    public int deleteFirstFileName(String name) {
        return urlsFileNameDao.deleteFileName(name);
    }

    public static void main(String[] args) {
        UrlsFileNameService urlsFileNameService = SpringUtil.getFileNameService();

        String name = urlsFileNameService.getFirstFileName();

        Assert.assertNotNull(name);

        System.out.println(name);
    }

    public LinkedList<String> getFilesNameForTest() {
        return urlsFileNameDao.getFilesNameForTest();
    }
}
