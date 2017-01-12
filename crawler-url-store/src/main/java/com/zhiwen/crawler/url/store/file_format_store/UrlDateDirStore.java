package com.zhiwen.crawler.url.store.file_format_store;

import com.zhiwen.crawler.common.model.CrawlerStore;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import java.io.File;

/**
 * Created by zhiwenzhu on 17/1/9.
 */
public class UrlDateDirStore implements CrawlerStore {
//    private static final String BASE_DIR = DirectoryPath.CONFIG_STORE_PATH;

    public void storeToFile(String filename, String content) {
        File file = new File(filename);

        FileWriteUtil.writeToFile(file, content, true);

    }
}
