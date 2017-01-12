package com.zhiwen.crawler.url.store.model;

/**
 * Created by zhiwenzhu on 17/1/7.
 */
@Deprecated
public class DynamicUrls {
    private String dbName;

    private String url;

    private String parentUrl;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

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
}
