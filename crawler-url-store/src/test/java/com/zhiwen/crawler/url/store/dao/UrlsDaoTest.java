package com.zhiwen.crawler.url.store.dao;

import com.zhiwen.crawler.url.store.TestBase;
import com.zhiwen.crawler.url.store.model.Urls;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhiwenzhu on 17/1/5.
 */
public class UrlsDaoTest extends TestBase {
    @Autowired
    private UrlsDao urlsDao;

    @Test
    public void addUrls() throws Exception {
//        Assert.assertNotNull(urlsDao);
//
//        Urls urls = new Urls();
//        urls.setParentUrl("www.zhiwenzhu.com");
//        urls.setUrl("www.baidu.com");
//
//        int result = urlsDao.addUrls(urls);
//        Assert.assertTrue(result > 0);
    }

    @Test
    public void addUrlss() throws Exception {

    }

    @Test
    public void getUrlById() throws Exception {
        Assert.assertNotNull(urlsDao);

        Urls urls = urlsDao.getUrlsById(1);

        Assert.assertNotNull(urls);
    }

    @Test
    public void getUrlsByParentUrl() throws Exception {
        List<Urls> urlss = urlsDao.getUrlsByParentUrl("www.zhiwenzhu.com");

        Assert.assertNotNull(urlss);

        Assert.assertTrue(urlss.size() == 1);


    }

}