package com.zhiwen.crawler.file.parser;

import java.util.List;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public class RegexParserEngine {

    private String content;


    public RegexParserEngine(String html) {
        this.content = html;
    }


    //TODO  PARSING
    public void parse() {

    }

    //TODO extract URLs from HTML page
    public List<String> getUrls() {
        return null;
    }

    //TODO  extract text from HTML page
    public String getText() {
        return null;
    }
}
