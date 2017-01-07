package com.zhiwen.crawler.url.store.dao;

import com.zhiwen.crawler.url.store.TestBase;
import com.zhiwen.crawler.url.store.model.DynamicUrls;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by zhiwenzhu on 17/1/7.
 */
public class DynamicUrlsDaoTest extends TestBase {
    @Resource
    private DynamicUrlsDao dynamicUrlsDao;
    @Test
    public void addUrls() throws Exception {
        Assert.assertNotNull(dynamicUrlsDao == null);
        DynamicUrls dynamicUrls = new DynamicUrls();

        dynamicUrls.setDbName("Urls");
        dynamicUrls.setUrl("测试动态插入数据");
        dynamicUrls.setParentUrl("www.zhiwenzhu.com");

        int result = dynamicUrlsDao.addUrls(dynamicUrls);

        Assert.assertTrue(result > 0);

    }

}