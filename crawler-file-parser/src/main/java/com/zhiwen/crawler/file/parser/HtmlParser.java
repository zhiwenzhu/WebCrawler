package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawler.common.model.Page;
import org.htmlparser.util.ParserException;

import java.util.Set;

/**
 * Created by zhiwenzhu on 17/1/17.
 */
public class HtmlParser implements Parser {


    HtmlParserEngine engine = new HtmlParserEngine();


    public Page parse(String url, String content) {
        Page page = new Page();

        try {
            Set<String> urls = engine.extractLinks(url, content);
            page.setUrls(urls);
        } catch (ParserException e) {
            e.printStackTrace();
            //TODO remove catch block here
        }
        page.setUrl(url);
        page.setContent(content);
        return page;
    }

    public Page parse(Page page) {
        return parse(page.getUrl(), page.getContent());
    }

}
