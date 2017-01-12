package com.zhiwen.crawler.url.store.model;

/**
 * Created by zhiwenzhu on 17/1/5.
 */
@Deprecated
public class Urls {
    private String url;

    private String parentUrl;

    private int id;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
