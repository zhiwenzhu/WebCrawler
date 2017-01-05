package com.zhiwen.crawler.url.store.model;

import java.util.List;
import java.util.Set;

/**
 * Created by zhiwenzhu on 17/1/5.
 */
public class Urls {
    private String urls;

    private String parentUrl;

    private int id;

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
