package com.zhiwen.crawler.file.store.spi.impl;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.model.Page;
import com.zhiwen.crawler.common.util.FileNameGenerator;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import com.zhiwen.crawler.file.store.spi.FileStore;
import com.zhiwen.crawler.monitor.Monitor;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhiwenzhu on 17/1/16.
 */
public class FileStoreImpl implements FileStore {

    private static final String FILE_STORE_DIR = DirectoryPath.FILE_STORE_PATH;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmSS");

    private String secondDir;

    private List<Page> pages = new LinkedList<Page>();

    public static int saveCount;

    private Map<String, Integer> pageNumOfThread = new HashMap<String, Integer>();

    private long date = System.currentTimeMillis();

    private Map<String, Integer> prePageNumOfThread = new HashMap<String, Integer>();

    public void save(Page page) {
        synchronized (pages) {
            long current = System.currentTimeMillis();
            if ((current - date) > 1000 * 60 * 1) {
                Set<Map.Entry<String, Integer>> entries = pageNumOfThread.entrySet();

                for (Map.Entry<String, Integer> entry : entries) {
                    System.out.println(entry.getKey() + ":" + entry.getValue() + " " +
                            (prePageNumOfThread.entrySet().size() != 0 ? prePageNumOfThread.get(entry.getKey()) : ""));
                }
                System.out.println("没有进展的线程数量是:" + getNumOfNoProgressThreads());
                date = current;
                prePageNumOfThread.clear();
                prePageNumOfThread.putAll(pageNumOfThread);

            }
            if (pages.size() < 100) {
                pages.add(page);
                String currentThread = Thread.currentThread().getName();
                if (pageNumOfThread.containsKey(currentThread)) {
                    int num = pageNumOfThread.get(currentThread);
                    pageNumOfThread.put(currentThread, ++ num);
                } else {
                    pageNumOfThread.put(currentThread, 1);
                }
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

            long bytes = FileWriteUtil.writeToFile(path + fileName, content, false);

            Monitor.acc(bytes);
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

    private int getNumOfNoProgressThreads() {
        int result = 0;
        for (Map.Entry<String, Integer> entry : pageNumOfThread.entrySet()) {
            if (entry.getValue() == prePageNumOfThread.get(entry.getKey())) {
                result ++;
            }
        }

        return result;
    }
}
