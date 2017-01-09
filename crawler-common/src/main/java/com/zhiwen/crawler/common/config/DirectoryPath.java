package com.zhiwen.crawler.common.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by zhiwenzhu on 17/1/8.
 */
public class DirectoryPath {
    //html文件存储的根目录
    public static final String FILE_STORE_PATH = "/crawler_file_store/";

    //url文件存储的根目录
    public static final String URL_STORE_PATH = "/crawler_url_store/";

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    //存储所有目录和文件名信息的根目录
    public static final String CONFIG_STORE_PATH = "/crawler_config/";

    //存储url文件夹根目录下，所有日期层文件夹的name的文件名
    public static final String URL_DATE_DIR_STORE_FILE = CONFIG_STORE_PATH + "url_date_dir_store_file.txt";

    //存储文件，每个文建中存储某一天的所有url文件的文件名，（该文件的文件名用yyyy-MM-dd的形式，和URL_STORE_PATH
    // 下的date层文件夹名对应）
    public static final String DATE_URL_FILE_PATH = "per_day_url_store_file_name/";

    //记录正在爬的url所在的上一级目录名的文件，  该文件只存储一条数据，
    //每次当前程序里list中的url都爬完后，去读取该文件中的这条记录，然后解析该信息，再去找到接下来爬的url文件
    public static final String Next_CRAWLER_FILE_NAME = CONFIG_STORE_PATH + "to_crawler_file";

}
