package com.zhiwen.crawler.url.store.model;

import java.util.Date;

/**
 * Created by chu on 17-1-19.
 */
public class UrlsFileName {
    private String id;

    private String name;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
