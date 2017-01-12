package com.zhiwen.crawler.fetcher;

import java.io.IOException;
import java.net.URL;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public interface Fetcher {

    String fetch(String url) throws IOException;

    String fetch(URL url) throws IOException;
}
