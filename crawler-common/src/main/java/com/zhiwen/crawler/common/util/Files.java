package com.zhiwen.crawler.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhengwenzhu on 2017/1/17.
 */
public class Files {
    public static String read(String path) {
        try {
            InputStream inputStream = Files.class.getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder(1024);
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
