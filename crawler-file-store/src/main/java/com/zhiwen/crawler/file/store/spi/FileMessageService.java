package com.zhiwen.crawler.file.store.spi;

import com.zhiwen.crawler.file.store.model.FileMessage;

/**
 * Created by zhiwenzhu on 17/1/5.
 */

public interface FileMessageService {
    int addFileMessage(FileMessage fm);

    FileMessage getFileMessageById(int id);

    FileMessage getFileMessageByUrl(String url);
}
