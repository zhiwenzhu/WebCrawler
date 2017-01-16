package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawler.file.parser.match.BodyMatchStrategy;

import java.util.Set;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public class RegexParserEngine {

    private String content;


    public RegexParserEngine(String html) {
        this.content = html;
    }

    public Set<String> getUrls() {
        return BodyMatchStrategy.getUrlFromPageContent(content);
    }

}
