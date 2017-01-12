package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawler.common.model.Page;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public interface Parser {

    Page parse(String url, String content);

    Page parse(Page page);
}
