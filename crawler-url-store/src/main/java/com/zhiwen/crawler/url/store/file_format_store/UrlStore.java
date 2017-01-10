package com.zhiwen.crawler.url.store.file_format_store;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.model.CrawlerStore;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Date;

/**
 * Created by zhiwenzhu on 17/1/8.
 */
public class UrlStore implements CrawlerStore {
    private static String dateString = DirectoryPath.DATE_FORMAT.format(new Date());

    private static String today_dir = DirectoryPath.URL_STORE_PATH + dateString;

    static {
        File file = new File(today_dir);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public void storeToFile(String filename, byte[] bytes) {
        Date currentDate = new Date();
        String currentDateString = DirectoryPath.DATE_FORMAT.format(currentDate);
        if (!StringUtils.equals(dateString, currentDateString)) {
            dateString = currentDateString;
            today_dir = DirectoryPath.URL_STORE_PATH + dateString;

            File file = new File(today_dir);
            if (!file.exists()) {
                file.mkdir();
            }

            UrlDateDirStore udds = new UrlDateDirStore();
            byte[] urlDateDirBytes = (dateString + "\n").getBytes();
            udds.storeToFile(DirectoryPath.URL_DATE_DIR_STORE_FILE, urlDateDirBytes);
        }

        File file = new File(today_dir + "/" + filename);

        FileWriteUtil.writeToFile(file, bytes, false);

        UrlFileNameStore ufns = new UrlFileNameStore();
        byte[] urlFileNameBytes = (filename + "\n").getBytes();
        ufns.storeToFile(dateString, urlFileNameBytes);

    }
}
