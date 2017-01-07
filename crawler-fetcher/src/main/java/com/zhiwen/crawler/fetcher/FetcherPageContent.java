package com.zhiwen.crawler.fetcher;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhiwenzhu on 17/1/4.
 * 该类中方法主要用来获取指定url对应的html页面内容
 */
public class FetcherPageContent {

    /**
     * 通过指定url获取html页面内容；
     * @param seedUrl
     * @return
     */
    public static String fetcherPage(String seedUrl) {
        seedUrl = preHandleUrl(seedUrl);
        String result = "";

        try {
            URL url = new URL(seedUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10 * 1000);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                String charset = "gb2312";
                Pattern pattern = Pattern.compile("charset=\\S*");
                Matcher matcher = pattern.matcher(conn.getContentType());
                String s = conn.getContentType();
                System.out.println("编码方式：" + s);
                if (matcher.find() && matcher.group().startsWith("charset") ) {
                    charset = matcher.group().replace("charset=", "");
                } else {
                    return null;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                is.close();
                result = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //预处理url，处理掉部分不合法的url；
    private static String preHandleUrl(String url) {
        url = url.trim();
        if (url.contains(" ")) {
            throw new IllegalArgumentException("不合法的url:" + url);
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;

        }
        return url;
    }



    //本方法测试；
    public static void main(String[] args) {

        String result = FetcherPageContent.fetcherPage("http://www.baidu.com");
        System.out.println(result);

    }
}
