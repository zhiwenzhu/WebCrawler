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


    //该方法内的代码会造成线程阻塞
    public String fetch(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3 * 1000);

//        if (conn.getResponseCode() == 200) {   //该行代码时间开销较大
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
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            return sb.toString();
//        }
//        else {
//            throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
//            System.out.println("11");
//            return null;
//        }
    }
}
