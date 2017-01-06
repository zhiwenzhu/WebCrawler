package com.zhiwen.crawler.file.store.dao;

import com.zhiwen.crawler.file.store.TestBase;
import com.zhiwen.crawler.file.store.model.FileMessage;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by zhiwenzhu on 17/1/5.
 */
public class FileMessageDaoTest extends TestBase{
    @Autowired
    private FileMessageDao fileMessageDao;

    @Test
    public void addFileMessage() throws Exception {
//        Assert.assertNotNull(fileMessageDao);
//        FileMessage fm = new FileMessage();
//        fm.setUrl("www.zhiwenzhu.com");
//        fm.setDescription("朱志文的网站");
//        fm.setTitle("zhuzhu");
//        fm.setKeywords("个人网站");
//
//        int result = fileMessageDao.addFileMessage(fm);
//
//        Assert.assertTrue(result > 0);

        //已测试成功，insert操作，测试一次就好

    }

    @Test
    public void getFileMessageById() throws Exception {
        Assert.assertNotNull(fileMessageDao);
        FileMessage fm = fileMessageDao.getFileMessageById(1);

        Assert.assertNotNull(fm);

    }

    @Test
    public void getFileMessageByUrl() throws Exception {
        Assert.assertNotNull(fileMessageDao);
        FileMessage fm = fileMessageDao.getFileMessageByUrl("www.zhiwenzhu.com");

        Assert.assertNotNull(fm);

        FileMessage fm1 = fileMessageDao.getFileMessageByUrl("123");
        Assert.assertNull(fm1);

    }

    @Test
    public void getMaxId() throws Exception {
        int result = 0;
        result = fileMessageDao.getMaxId();

        Assert.assertTrue(result > 0);
        System.out.println(result);
    }

}