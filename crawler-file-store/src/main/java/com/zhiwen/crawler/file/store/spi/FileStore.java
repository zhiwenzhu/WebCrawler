package com.zhiwen.crawler.file.store.spi;

import com.zhiwen.crawler.common.model.Page;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public interface FileStore {

    String save(String url, Page page);

}
