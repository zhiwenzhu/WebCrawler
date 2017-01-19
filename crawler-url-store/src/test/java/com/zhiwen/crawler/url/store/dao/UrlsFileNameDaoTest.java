package com.zhiwen.crawler.url.store.dao;

import com.zhiwen.crawler.url.store.TestBase;
import com.zhiwen.crawler.url.store.model.UrlsFileName;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by chu on 17-1-19.
 */
public class UrlsFileNameDaoTest extends TestBase {
    @Resource
    private UrlsFileNameDao urlsFileNameDao;

    @Test
    public void getLatestFileName() throws Exception {
        Assert.assertNotNull(urlsFileNameDao);

        String name = urlsFileNameDao.getFirstFileName();

        Assert.assertNotNull(name);


    }

    @Test
    public void addFileName() throws Exception {
//        UrlsFileName ufn = new UrlsFileName();
//
//        ufn.setName("Test");
//
//        int result = urlsFileNameDao.addFileName(ufn);
//        Assert.assertNotNull(result);

        //insert operation test success
    }

}