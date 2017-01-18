package com.zhiwen.crawler.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by chu on 17-1-18.
 */
public class FileWriteUtilTest {
    @Test
    public void writeToFile() throws Exception {

    }

    @Test
    public void getAndRmUrlsFromFile() throws Exception {
        String content = "11\n22\n33\n44\n55\n66\n77\n88\n99\n1111\n";
        String urlsFilePath = "/crawler_url_store/";

        FileWriteUtil.writeToFile(urlsFilePath + "urls", content, false);

        File file = new File(urlsFilePath + "urls");

        Assert.assertTrue(file.exists());

        Queue<String> urls = FileWriteUtil.getAndRmUrlsFromFile(urlsFilePath + "urls", 6);

        Assert.assertTrue(urls.size() == 6);

        File dump_urls = new File(urlsFilePath + "dump_urls");

        Assert.assertTrue(dump_urls.exists());

        Assert.assertTrue(file.exists());
    }

    @Test
    public void testGetAndRmUrlsFromFile() throws Exception {
        String content = "11\n22\n33\n44\n55\n66\n77\n88\n99\n1111\n";
        String urlsFilePath = "/crawler_url_store/";

        FileWriteUtil.writeToFile(urlsFilePath + "urls", content, false);

        File file = new File(urlsFilePath + "urls");

        Assert.assertTrue(file.exists());

        Queue<String> urls = FileWriteUtil.getAndRmUrlsFromFile(urlsFilePath + "urls", 11);

        Assert.assertTrue(urls.size() == 10);

        BufferedReader reader = new BufferedReader(new FileReader(file));

        Assert.assertNull(reader.readLine());

    }

}