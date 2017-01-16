package com.zhiwen.crawler.common.util;

/**
 * Created by zhiwenzhu on 17/1/9.
 */
public class ResolveFilePathUtil {
    public static String[] splitFilePath(String filePath) {
        String[] result = filePath.split("/");
        return result;
    }

    public static void main(String[] args) {
        String[] result = ResolveFilePathUtil.splitFilePath("/zhuzhiwen/123/test");

        for (String s : result) {
            System.out.println(s);
        
    }
 }
