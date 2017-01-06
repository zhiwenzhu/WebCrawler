package com.zhiwen.crawler.file.store.dao;

import com.zhiwen.crawler.file.store.model.FileMessage;

/**
 * Created by zhiwenzhu on 17/1/5.
 */
public interface FileMessageDao {
    int addFileMessage(FileMessage fm);

    FileMessage getFileMessageById(int id);

    FileMessage getFileMessageByUrl(String url);

    int getMaxId();
}
