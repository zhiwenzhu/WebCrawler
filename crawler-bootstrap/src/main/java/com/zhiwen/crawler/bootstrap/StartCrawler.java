package com.zhiwen.crawler.bootstrap;

import com.zhiwen.crawler.common.config.DirectoryPath;
import com.zhiwen.crawler.common.strategy.GetSeedUrlsStrategy;
import com.zhiwen.crawler.file.parser.HtmlContentParser;
import com.zhiwen.crawler.file.store.spi.FileMessageService;
import com.zhiwen.crawler.file.parser.util.SpringBeanUtil;
import com.zhiwen.crawler.url.store.dao.CrawlerIndexDao;
import com.zhiwen.crawler.url.store.model.Urls;
import com.zhiwen.crawler.url.store.spi.UrlsService;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.Buffer;
import java.util.List;

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
//        if (crawlerIndex == 0) {
//            getCrawlerIndex();
//        }
//        String toCrawlerUrl = "";
//        for ( ; ; ) {
//            Urls urls = urlsService.getUrlsById(crawlerIndex);
//            if (urls != null && StringUtils.isNotBlank(urls.getUrl())) {
//                toCrawlerUrl = urls.getUrl();
//                break;
//            }
//        }
//        HtmlContentParser hp = new HtmlContentParser();
//        hp.setUrl(toCrawlerUrl);
//        hp.run();
//                crawlerIndex++;
//                crawlerIndexDao.updateIndex(crawlerIndex);
//        run();

        String toCrawlerUrlFile = "";
        try {
            File file = new File(DirectoryPath.Next_CRAWLER_FILE_NAME);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            toCrawlerUrlFile = reader.readLine();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (StringUtils.isNotBlank(toCrawlerUrlFile)) {
            GetSeedUrlsStrategy gsus = new GetSeedUrlsStrategy();
            List<String> toCrawlerUrls = gsus.getToCrawlerUrl(toCrawlerUrlFile);
            HtmlContentParser hp = new HtmlContentParser();
            for (String url : toCrawlerUrls) {
                hp.setUrl(url);
                hp.run();
            }

            String newPath = gsus.getToCrawlerMessage(toCrawlerUrlFile);
            if (StringUtils.isNotBlank(newPath)) {
                File file = new File(DirectoryPath.Next_CRAWLER_FILE_NAME);
                try {
                    OutputStream ops = new FileOutputStream(file);
                    ops.write(newPath.getBytes());
                    ops.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
            System.out.println(Thread.currentThread().getName() + "线程执行完毕");
            run();
        }

    }

    public static void main(String[] args) {


        StartCrawler sc = new StartCrawler();

        sc.run();

    }
}
