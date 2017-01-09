package com.zhiwen.crawler.common.strategy;

import com.zhiwen.crawler.common.config.DirectoryPath;

/**
 * Created by zhiwenzhu on 17/1/9.
 */
public class GetSeedUrlsStrategy implements CrawlerSrategy {
    //存储url的文件所在的根文件夹
    private static final String BASE_DIR = DirectoryPath.URL_STORE_PATH;

    //本次爬取完成的存储url的文件，所在的date层文件夹
    private String dateDir;

    //本次爬取完成的存储url的文件,的文件名
    private String urlFileName;



    public String getToCrawlerMessage() {
        return null;
    }
}
