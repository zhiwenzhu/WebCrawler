package com.zhiwen.crawler.common.model;

import java.util.Set;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public class Page {


    /**
     * The Url reference to this page
     */
    private String url;

    /**
     * The original content of this page
     */
    private String content;

    /**
     * The processed urls in this page,
     * for example, the url of this page is http://www.abc.com/123,
     * contains reference to abc.html  /abc/ddd/123.html and http://www.bacd.com/
     * will be extract as http://www.abc.com/123/abc.html http://www.abc.com/abc/ddd/123.html  http://www.bacd.com/
     * respectively.
     *
     */
    private Set<String> urls;

    private String text;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
