package com.zhiwen.crawler.url.store.dao;

import com.zhiwen.crawler.url.store.TestBase;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by zhiwenzhu on 17/1/7.
 */
public class CrawlerIndexDaoTest extends TestBase {
    @Resource
    private CrawlerIndexDao crawlerIndexDao;

    @Test
    public void updateIndex() throws Exception {
//        Assert.assertNotNull(crawlerIndexDao);
//
//        int result = crawlerIndexDao.updateIndex(2);
//
//        Assert.assertTrue(result > 0);
    }

    @Test
    public void getCrawlerIndex() throws Exception {
        Assert.assertNotNull(crawlerIndexDao);

        int result = crawlerIndexDao.getCrawlerIndex();

        Assert.assertTrue(result > 0);
    }


}