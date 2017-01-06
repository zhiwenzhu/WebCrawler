package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawler.file.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by zhiwenzhu on 17/1/6.
 */
public class HtmlContentParserTest extends TestBase {
    @Autowired
    private HtmlContentParser htmlContentParser;
    @Test
    public void testrun() throws Exception {
        Assert.assertNotNull(htmlContentParser);

    }

}