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
//        System.out.println("1");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        System.out.println("2");

//        conn.setRequestMethod("GET");        默认是get请求，可以不写改行代码
        conn.setConnectTimeout(3 * 1000);

//        System.out.println("3");

        if (conn.getResponseCode() == 200) {   //该行代码时间开销较大
//            System.out.println("4");
            InputStream is = conn.getInputStream();
//            System.out.println("5");
            String charset = "gb2312";
            Pattern pattern = Pattern.compile("charset=\\S*");
            Matcher matcher = pattern.matcher(conn.getContentType());
//            System.out.println("6");
            String s = conn.getContentType();
            if (matcher.find() && matcher.group().startsWith("charset")) {
//                System.out.println("7");
                charset = matcher.group().replace("charset=", "");
//                System.out.println("8");
            } else {
//                System.out.println("9");
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));

//            StringBuilder sb = new StringBuilder();
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
//            System.out.println("10");
            return sb.toString();
        } else {
//            throw new IOException(conn.getResponseCode() + " " + conn.getResponseMessage());
//            System.out.println("11");
            return null;
        }
    }
}
