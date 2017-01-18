package com.zhiwen.crawler.file.store.spi.impl;

import com.zhiwen.crawler.common.model.Page;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import com.zhiwen.crawler.file.store.spi.FileStore;
import com.zhiwen.crawler.file.store.util.FileNameGenerator;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhiwenzhu on 17/1/16.
 */
public class FileStoreImpl implements FileStore {
    private static final String FILE_STORE_DIR = "/crawler_file_store/";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddhhmmSS");

    private String secondDir;

    private List<Page> pages = new LinkedList<Page>();

    public synchronized void save(Page page) {
        if (pages.size() < 100) {
            pages.add(page);
//            System.out.println(Thread.currentThread().getName() + "下载页面数量：" + pages.size());
        } else {
            save(pages);

            System.out.println(Thread.currentThread().getName() + "储存100个页面完成");
            pages.clear();
        }
    }

    public void save(List<Page> pages) {
        Date date = new Date();

        secondDir = DATE_FORMAT.format(date) + "/";
        String path = FILE_STORE_DIR + secondDir;
        mkdir(path);

        for (Page page : pages) {
            String content = genContent(page);
            String fileName = FileNameGenerator.nextId();

            FileWriteUtil.writeToFile(path + fileName, content, false);
        }
    }

    private void mkdir(String path) {
        File file = new File(path);

        if (!file.exists()) {
            file.mkdir();
        }
    }

    private String genContent(Page page) {
        String content = page.getUrl() + "\n" + page.getContent();

        return content;
    }
}
