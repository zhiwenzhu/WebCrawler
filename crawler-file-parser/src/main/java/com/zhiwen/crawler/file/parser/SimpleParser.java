package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawler.common.model.Page;
import com.zhiwen.crawler.common.util.UrlUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public class SimpleParser implements Parser {
    public Page parse(String url, String content) {
        RegexParserEngine engine = new RegexParserEngine(content);
        engine.parse();
        String text = engine.getText();
        List<String> urls = engine.getUrls();
        Page page = new Page();
        page.setContent(content);
        page.setText(text);
        page.setUrl(url);
        Set<String> processedUrls = new HashSet<String>();
        for (String u : urls) {
            String combine = UrlUtils.combine(url, u);
            if (combine != null) {
                processedUrls.add(combine);
            }
        }
        page.setUrls(processedUrls);
        return page;
    }

    public Page parse(Page page) {
        return parse(page.getUrl(), page.getContent());
    }


}
