package com.zhiwen.crawler.file.store.file_format_store;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.model.CrawlerStore;
import com.zhiwen.crawler.common.util.FileWriteUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Date;

/**
 * Created by zhiwenzhu on 17/1/8.
 */
public class FileStore implements CrawlerStore {
    private static Date date = new Date();
    private static String dateString = DirectoryPath.DATE_FORMAT.format(date);

    private static String today_dir = DirectoryPath.FILE_STORE_PATH + dateString;

    static {
        File file = new File(today_dir);
        if (!file.exists()) {
            file.mkdir();
        }
    }


    public void storeToFile(String fileName, byte[] bytes) {

        Date currentDate = new Date();
        String currentDateString = DirectoryPath.DATE_FORMAT.format(currentDate);
        if (!StringUtils.equals(dateString, currentDateString)) {
            dateString = currentDateString;
            today_dir = DirectoryPath.FILE_STORE_PATH + dateString;

            File file = new File(today_dir);
            if (!file.exists()) {
                file.mkdir();
            }
        }

        File file = new File(today_dir + "/" + fileName);
        FileWriteUtil.writeToFile(file, bytes, false);

    }

    //测试静态变量的加载
    public void test() {
        System.out.println(dateString);
        System.out.println(today_dir);

        dateString = "更改后";

        System.out.println(dateString);
        System.out.println(today_dir);


    }


    public static void main(String[] args) {
        FileStore fileStore = new FileStore();

        byte[] bytes = new byte[] {'z', 'h', 'u'};

        String fileName = "test.txt";

        fileStore.storeToFile(fileName, bytes);

    }
}
