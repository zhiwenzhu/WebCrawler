package com.zhiwen.crawler.url.store.dao;

import com.zhiwen.crawler.url.store.model.UrlsFileName;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chu on 17-1-19.
 */
public interface UrlsFileNameDao {
    String getFirstFileName();

    int addFileName(UrlsFileName ufn);

    int deleteFileName(String name);

    //for test:由于之前大量的测试发现，线程单线程或者多线程执行过程中，均会出现线程阻塞，测试发现，线程阻塞应该是出现在程序与数据库建立连接的过程中，
    //该次测试在程序开始执行时，直接从数据库中获取一万条待爬取文件名，（对应的一万个存储url的文件中储存超过千万的url），所有的带爬取url的数据源信息一次获取完，
    //后续不再与数据库交互，url文件名在本次测试中也暂时不写入数据库，数据不做保存。
    LinkedList<String> getFilesNameForTest();
}
