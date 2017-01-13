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
}