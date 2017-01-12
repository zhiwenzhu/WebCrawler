package com.zhiwen.crawler.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhengwenzhu on 2017/1/12.
 */
public class SimpleFetcher implements Fetcher {

    public String fetch(String url) throws IOException {
        URL _url = new URL(url);
        return fetch(_url);
    }


    public String fetch(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            String charset = "gb2312";
            Pattern pattern = Pattern.compile("charset=\\S*");
            Matcher matcher = pattern.matcher(conn.getContentType());
            String s = conn.getContentType();
            if (matcher.find() && matcher.group().startsWith("charset")) {
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
            return sb.toString();
        } else {
            throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
        }
    }
}
