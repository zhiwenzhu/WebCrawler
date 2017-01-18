package com.zhiwen.crawler.file.parser.match;

import com.zhiwen.crawler.common.util.Files;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by zhengwenzhu on 2017/1/17.
 */
public class HtmlParserUtilTest extends TestCase {

    @Test
    public void testExtracLinks() throws Exception {
        String path = HtmlParserUtilTest.class.getResource("/test.html").getFile();

        String content = Files.read("/test.html");
        String url = "https://github.com/zhiwenzhu/WebCrawler/commit/ee4551d1a7c6cc05af143e928f6419fae4e2e681";
        Set<String> urls = HtmlParserUtil.extractLinks(url, content);

        int i = 1;
        for (String u : urls) {
            System.out.println(i++ + ":" + u);
        }


        Assert.assertTrue(urls.contains("https://github.com/fluidicon.png"));
        Assert.assertTrue(urls.contains("https://github.com/apple-touch-icon-180x180.png"));
        Assert.assertTrue(urls.contains("wss://live.github.com/_sockets/VjI6MTQzMDQ0MzU2OmExYzk5MGY4M2RiY2NhOGRkNDIzY2U2OTUwOTRkMmZiNDI4OWRjZGNkY2YzNjE3N2Y3YjJkMzljOWJlYTQzMjY=--327553545d81a4334c296a0017af805b88ecca13"));
    }
}