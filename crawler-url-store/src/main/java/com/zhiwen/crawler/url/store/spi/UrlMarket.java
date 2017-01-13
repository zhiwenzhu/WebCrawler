package com.zhiwen.crawler.url.store.spi;

import java.util.Collection;

/**
 * The centralized URL manager center. The UrlStore manages of all the URLs be visited and to be visit. </br>
 * All crawler threads fetch URLs here, and send the new founded URLs here to store or to other crawlers.
 */
public interface UrlMarket {

    /**
     * Store URLs to the store. Crawler get many URLs by parse the downloaded pages. The save the URLs which to be visit by themselves in the local.
     * Those should not be download by themselves will be saved in the bank. Crawlers call this method to save the URL here.
     *
     * @param urls the URLs found by the crawlers.
     */
    void deposit(Collection<String> urls);

    void deposit(String url);

    /**
     * The crawlers fetch the unvisited URLs from the store. And the store distributes URLs by crawler group.
     * In the other word, crawler in the same crawler group share the same URLs queue.
     *
     * @return
     */
    Collection<String> withdraw(int batchSize);

    /**
     * Whether a URL has been visited
     *
     * @param url
     * @return
     */
    boolean hasVisited(String url);

}
