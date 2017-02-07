package com.zhiwen.crawler.file.store.spi.impl;

import com.zhiwen.crawler.common.model.Page;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import com.zhiwen.crawler.file.store.spi.FileStore;
import com.zhiwen.crawler.common.util.FileNameGenerator;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhiwenzhu on 17/1/16.
 */
public class FileStoreImpl implements FileStore {
//    private static final String FILE_STORE_DIR = "/crawler_file_store/";

    private static final String FILE_STORE_DIR = "/media/chu/My Passport/zhiwen/crawler5/";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmSS");

    private String secondDir;

    private List<Page> pages = new LinkedList<Page>();

    public static int saveCount;

    private Map<String, Integer> pageNumOfThread = new HashMap<String, Integer>();

    public void save(Page page) {
        synchronized (pages) {
            if (pages.size() < 100) {
                pages.add(page);
                String currentThread = Thread.currentThread().getName();
                if (pageNumOfThread.containsKey(currentThread)) {
                    int num = pageNumOfThread.get(currentThread);
                    pageNumOfThread.put(currentThread, ++ num);
                } else {
                    pageNumOfThread.put(currentThread, 1);
                }

//                System.out.println(Thread.currentThread().getName() + "下载页面数量：" + pages.size());
                System.out.println(pageNumOfThread.get(currentThread) + " " + currentThread + "下载页面数量：" + pages.size());
            } else {
                save(pages);
                System.out.println("第" + saveCount++ + "次：" + Thread.currentThread().getName() + "储存100个页面完成");
                pages.clear();
            }
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
