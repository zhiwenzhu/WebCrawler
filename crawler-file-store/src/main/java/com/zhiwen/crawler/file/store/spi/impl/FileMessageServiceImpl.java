package com.zhiwen.crawler.file.store.spi.impl;

import com.zhiwen.crawler.file.store.dao.FileMessageDao;
import com.zhiwen.crawler.file.store.model.FileMessage;
import com.zhiwen.crawler.file.store.spi.FileMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhiwenzhu on 17/1/5.
 */
@Deprecated
@Service("FileMessageService")
public class FileMessageServiceImpl implements FileMessageService{
    @Autowired
    private FileMessageDao fileMessageDao;

    public int addFileMessage(FileMessage fm) {
        return fileMessageDao.addFileMessage(fm);
    }

    public FileMessage getFileMessageById(int id) {
        return fileMessageDao.getFileMessageById(id);
    }

    public FileMessage getFileMessageByUrl(String url) {
        return fileMessageDao.getFileMessageByUrl(url);
    }

    public int getMaxId() {
        return fileMessageDao.getMaxId();
    }
}
