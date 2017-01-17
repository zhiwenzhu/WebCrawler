package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawler.common.model.Page;

import java.util.Set;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public class SimpleParser implements Parser {
    public Page parse(String url, String content) {
        RegexParserEngine engine = new RegexParserEngine(content);

        Page page = new Page();
        page.setContent(content);
        page.setUrl(url);
        Set<String> urls = engine.getUrls();

        page.setUrls(urls);
        return page;
    }

    public Page parse(Page page) {
        return parse(page.getUrl(), page.getContent());
    }


}
