package com.zhiwen.crawler.common.util;

import org.junit.Assert;

import junit.framework.TestCase;

/**
 * Created by zhengwenzhu on 2017/1/13.
 */
public class UrlUtilsTest extends TestCase {

    public void testCombine() throws Exception {

        Assert.assertEquals("http://www.abc.com/123/456.html", UrlUtils.combine("http://www.abc.com/123/", "456.html"));
        Assert.assertEquals("http://www.abc.com/123/456/456.html", UrlUtils.combine("http://www.abc.com/123/456/789.html", "456.html"));
        Assert.assertEquals("http://www.abc.com/456.html", UrlUtils.combine("http://www.abc.com/123", "456.html"));
        Assert.assertEquals("http://www.abc.com/456.html", UrlUtils.combine("http://www.abc.com/123", "/456.html"));
        Assert.assertEquals("http://www.abc.com/456.html", UrlUtils.combine("http://www.test.com/123", "http://www.abc.com/456.html"));

    }

    public void combine() throws Exception {
        String baseUrl = "https://github.com/zhiwenzhu/WebCrawler/commit/ee4551d1a7c6cc05af143e928f6419fae4e2e681";
        Assert.assertEquals("https://github.com/apple-touch-icon-180x180.png", UrlUtils.combine(baseUrl, "/apple-touch-icon-180x180.png"));
        Assert.assertEquals("https://github.com/apple-touch-icon-180x180.png", UrlUtils.combine(baseUrl, "apple-touch-icon-180x180.png"));
    }
}