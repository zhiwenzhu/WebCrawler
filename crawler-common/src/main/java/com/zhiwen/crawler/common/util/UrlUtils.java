package com.zhiwen.crawler.common.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public class UrlUtils {

    public static final String HTTP_SCHEMA = "http:";
    public static final String HTTPS_SCHEMA = "https://";

    public static String combine(String baseUrl, String suffix) {

        try {
            if (suffix == null) {
                return baseUrl;
            }

            if (suffix.startsWith(HTTP_SCHEMA) || suffix.startsWith(HTTPS_SCHEMA)) {
//            if (suffix.contains("://")) {
                return suffix;
            }

            URL url = new URL(baseUrl);
            URL whole = new URL(url, suffix);

            return whole.toString();

        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String baseUrl = "http://www.baidu.com";

        String suffix = "www.baidu.com/zhuzhu";

        String result = UrlUtils.combine(baseUrl, suffix);

        System.out.println(result);
    }
}
