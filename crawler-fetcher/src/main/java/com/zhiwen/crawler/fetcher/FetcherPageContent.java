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
 */
public class FetcherPageContent {
    /**
     *
      */
    public static String fetcherPage(String seedUrl) {
        seedUrl = preHandleUrl(seedUrl);
        String result = "";

        try {
            URL url = new URL(seedUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                String charset = "UTF-8";
                Pattern pattern = Pattern.compile("charset=\\S*");
                Matcher matcher = pattern.matcher(conn.getContentType());
                if (matcher.find()) {
                    charset = matcher.group().replace("charset=", "");
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                is.close();
                result = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println(result);

        return result;
    }

    private static String preHandleUrl(String url) {
        if (url.contains(" ")) {
            throw new IllegalArgumentException("不合法的url:" + url);
        }
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }

        return url;
    }



    public static void main(String[] args) {

        String result = FetcherPageContent.fetcherPage("http://www.baidu.com");
        System.out.println(result);

    }




}
