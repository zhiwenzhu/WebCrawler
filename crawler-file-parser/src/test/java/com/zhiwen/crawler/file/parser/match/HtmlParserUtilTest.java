package com.zhiwen.crawler.file.parser.match;

import com.zhiwen.crawler.common.util.Files;
import com.zhiwen.crawler.file.parser.HtmlParserEngine;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Set;

/**
 * Created by zhengwenzhu on 2017/1/17.
 */
public class HtmlParserUtilTest extends TestCase {

    public void testExtractLinks() throws Exception {
        String content = Files.read("/test.html");
        String url = "https://github.com/zhiwenzhu/WebCrawler/commit/ee4551d1a7c6cc05af143e928f6419fae4e2e681";
        Set<String> urls = new HtmlParserEngine().extractLinks(url, content);

        Assert.assertTrue(urls.contains("https://gist.github.com/"));
        int i = 1;
        for (String u : urls) {
            System.out.println(i++ + ":" + u);
        }

        Assert.assertTrue(urls.contains("https://github.com/fluidicon.png"));
        Assert.assertTrue(urls.contains("https://github.com/apple-touch-icon-180x180.png"));
    }
}