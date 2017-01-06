package com.zhiwen.crawler.file.parser;

import com.zhiwen.crawler.fetcher.FetcherPageContent;
import com.zhiwen.crawler.file.match.BodyMatchStrategy;
import com.zhiwen.crawler.file.match.HeadMatchStrategy;
import com.zhiwen.crawler.file.store.model.FileMessage;
import com.zhiwen.crawler.file.store.spi.FileMessageService;
import com.zhiwen.crawler.file.util.SpringBeanUtil;
import com.zhiwen.crawler.url.store.model.Urls;
import com.zhiwen.crawler.url.store.spi.UrlsService;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by zhiwenzhu on 17/1/4.
 * 通过给该类的成员变量（url）赋值，执行run方法后，程序先获取该url对应的页面html内容，
 * 然后分别解析head标签和body标签内的内容；
 * 得到一个记录页面基本信息的FileMessage类实例，和一个该页面包含的有用url的集合；
 * 最后调用持久层，将数据存入数据库
 */

public class HtmlContentParser extends Thread{
    //持久层对象
    private FileMessageService fileMessageService;

    //持久层对象
    private UrlsService urlsService;

    //带抓取的url，可通过构造函数和set方法注入；
    private String url;

    //调用FetcherPageContent的静态方法通过给定的url得到网页内容，以字符串形式返回；
    private String fetcherPageContent() {
        return FetcherPageContent.fetcherPage(url);
    }

    public HtmlContentParser(String url) {
        this.url = url;
    }

    public HtmlContentParser() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 该类对外开放的接口方法；
     * 外部通过注入了url成员变量的实例调用该方法；
     * 完成页面获取、信息解析、数据存储等过程；
     */
    public void run() {
        String page = fetcherPageContent();
        Set<String> urlSet = BodyMatchStrategy.getUrlFromPageContent(page);

        FileMessage fm = HeadMatchStrategy.getMessageFromPageContent(page);
        fm.setUrl(url);

        //方便测试
        System.out.println("1:" + fm.getTitle());
        System.out.println("2:" + fm.getUrl());
        System.out.println("3:" + fm.getKeywords());
        System.out.println("4:" + fm.getDescription());

        //把页面提取出来的信息存入持久层；先判断数据库中是否有该页面信息
        //多线程时，此处应该加锁
        fileMessageService = SpringBeanUtil.getFileMessageService();
        urlsService = SpringBeanUtil.getUrlsService();

        if (fileMessageService.getFileMessageByUrl(url) == null) {
            fileMessageService.addFileMessage(fm);

            for (String item : urlSet) {
                if (fileMessageService.getFileMessageByUrl(item) == null && urlsService.getUrlsByUrl(item) == null
                        && urlsService.getUrlsByParentUrl(item).size() == 0) {
                    Urls urls = new Urls();
                    urls.setUrl(item);
                    urls.setParentUrl(url);
                    urlsService.addUrls(urls);
                }
            }

        }

    }

    private List<Urls> genUrls(Set<String> urls, String parentUrl) {
        List<Urls> urlss = new LinkedList<Urls>();
        for (String url : urls) {
            if (fileMessageService.getFileMessageByUrl(url) == null && urlsService.getUrlsByUrl(url) == null
                    && urlsService.getUrlsByParentUrl(url).size() == 0) {
                Urls temp = new Urls();
                temp.setUrl(url);
                temp.setParentUrl(parentUrl);
                urlss.add(temp);
            }
        }

        return urlss;
    }




    public static void main(String[] args) {
        HtmlContentParser hp = new HtmlContentParser();
        hp.setUrl("http://caipiao.163.com/award/qlc/#from=wzy");
        hp.run();
    }

}
