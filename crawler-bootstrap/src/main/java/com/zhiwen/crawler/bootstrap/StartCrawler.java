package com.zhiwen.crawler.bootstrap;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhiwenzhu on 17/1/6.
 */
public class StartCrawler {


    public static void main(String[] args) throws IOException {

        ExecutorService es = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            es.submit(new CrawleTask());
        }

        System.in.read();
    }
}
