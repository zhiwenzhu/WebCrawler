package com.zhiwen.crawler.url.store.file_format_store;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.model.CrawlerStore;
import com.zhiwen.crawler.common.util.FileWriteUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by zhiwenzhu on 17/1/9.
 */
public class UrlFileNameStore implements CrawlerStore {
    private static final String BASE_DIR = DirectoryPath.CONFIG_STORE_PATH + DirectoryPath.DATE_URL_FILE_PATH;

    public void storeToFile(String filename, byte[] bytes) {
        File file = new File(BASE_DIR + filename);
        FileWriteUtil.writeToFile(file, bytes, true);
    }
}
