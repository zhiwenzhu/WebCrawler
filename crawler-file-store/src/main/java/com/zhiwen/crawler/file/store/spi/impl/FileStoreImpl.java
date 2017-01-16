package com.zhiwen.crawler.file.store.spi.impl;

import com.zhiwen.crawler.common.model.Page;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import com.zhiwen.crawler.file.store.spi.FileStore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhiwenzhu on 17/1/16.
 */
public class FileStoreImpl implements FileStore {
    private static final String FILE_STORE_DIR = "crawler_file_store";

    public String save(String url, Page page) {
        String content = url + "\n" + page.getContent();
        String name = genFileName();

        return name;
    }

    private synchronized String genFileName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmSS");
        Date date = new Date();

        String dateString = dateFormat.format(date);

        String name = dateString + ".txt";

        return name;
    }
}
