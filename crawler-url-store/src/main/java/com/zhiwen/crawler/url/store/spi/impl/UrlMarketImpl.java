package com.zhiwen.crawler.url.store.spi.impl;

import com.zhiwen.crawler.url.store.spi.UrlMarket;

import java.util.Collection;

/**
 * Created by zhiwenzhu on 17/1/12.
 */
public class UrlMarketImpl implements UrlMarket {

    public void deposit(Collection<String> urls) {

    }

    public void deposit(String url) {

    }

    public Collection<String> withdraw() {
        return null;
    }

    public boolean hasVisited(String url) {
        return false;
    }

    private String collectionsToDepositContent(Collection<String> urls) {
        String content = "";

        for (String url : urls) {
            content += url + "\n";
        }

        return content;
    }
}
