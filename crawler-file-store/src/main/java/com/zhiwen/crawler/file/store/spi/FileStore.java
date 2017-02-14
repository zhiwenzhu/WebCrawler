package com.zhiwen.crawler.file.store.spi;

import com.zhiwen.crawler.common.model.Page;

import java.util.List;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public interface FileStore {

    void save(Page page);

    void save(List<Page> pages);

}
