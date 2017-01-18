package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawler.common.model.Page;
import com.zhiwen.crawler.fetcher.Fetcher;
import com.zhiwen.crawler.fetcher.SimpleFetcher;
import com.zhiwen.crawler.file.parser.match.HtmlParserUtil;

import java.io.IOException;
import java.util.Set;

/**
 * Created by zhiwenzhu on 17/1/17.
 */
public class HtmlParser implements com.zhiwen.crawler.file.parser.Parser{


    public Page parse(String url, String content) {
        Page page = new Page();

        Set<String> urls = HtmlParserUtil.extractLinks(url, content);

        page.setUrl(url);
        page.setContent(content);
        page.setUrls(urls);

        return page;
    }

    public Page parse(Page page) {
        return parse(page.getUrl(), page.getContent());
    }

    public static void main(String[] args) throws IOException {
        String url = "http://www.163.com";
        Fetcher fetcher = new SimpleFetcher();

        String content = fetcher.fetch(url);

        HtmlParser parser = new HtmlParser();

        Set<String> urls = parser.parse(url, content).getUrls();

        int i = 0;
        for (String u : urls) {
            System.out.println(i++ + ":" + u);
        }
    }
}
