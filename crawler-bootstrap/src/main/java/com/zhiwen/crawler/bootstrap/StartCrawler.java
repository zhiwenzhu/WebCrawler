package com.zhiwen.crawler.bootstrap;

import com.zhiwen.crawler.file.parser.HtmlContentParser;
import com.zhiwen.crawler.file.store.spi.FileMessageService;
import com.zhiwen.crawler.file.parser.util.SpringBeanUtil;
import com.zhiwen.crawler.url.store.dao.CrawlerIndexDao;
import com.zhiwen.crawler.url.store.model.Urls;
import com.zhiwen.crawler.url.store.spi.UrlsService;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhiwenzhu on 17/1/6.
 */
public class StartCrawler extends Thread {
    public static int crawlerIndex = 0;

    private UrlsService urlsService = SpringBeanUtil.getUrlsService();

    private FileMessageService fileMessageService = SpringBeanUtil.getFileMessageService();

    private CrawlerIndexDao crawlerIndexDao = SpringBeanUtil.getCrawlerIndexDao();

//    public void getLastCrawlerIndex() {
//        int maxFileMessageId = fileMessageService.getMaxId();
//        String lastCrawlerUrl = "";
//
//        if (maxFileMessageId > 0) {
//            lastCrawlerUrl = fileMessageService.getFileMessageById(maxFileMessageId).getUrl();
//            if (StringUtils.isNotBlank(lastCrawlerUrl)) {
//                lastCrawlerIndex = urlsService.getUrlsByUrl(lastCrawlerUrl).getId();
//            }
//        }
//    }

    private void getCrawlerIndex() {
        crawlerIndex = crawlerIndexDao.getCrawlerIndex();
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程开始执行");
        if (crawlerIndex == 0) {
            getCrawlerIndex();
        }
        String toCrawlerUrl = "";
        for ( ; ; ) {
            Urls urls = urlsService.getUrlsById(crawlerIndex);
            if (urls != null && StringUtils.isNotBlank(urls.getUrl())) {
                toCrawlerUrl = urls.getUrl();
                break;
            }
        }
        HtmlContentParser hp = new HtmlContentParser();
        hp.setUrl(toCrawlerUrl);
        hp.run();

        crawlerIndex++;

        System.out.println(Thread.currentThread().getName() + "线程执行完毕");

        crawlerIndexDao.updateIndex(crawlerIndex);
        run();

    }

    public static void main(String[] args) {


        StartCrawler sc = new StartCrawler();

        sc.run();

    }
}
