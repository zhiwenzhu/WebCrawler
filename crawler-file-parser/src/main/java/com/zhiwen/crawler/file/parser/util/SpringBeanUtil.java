package com.zhiwen.crawler.file.parser.util;

import com.zhiwen.crawler.file.parser.HtmlContentParser;
import com.zhiwen.crawler.file.store.spi.FileMessageService;
import com.zhiwen.crawler.url.store.dao.CrawlerIndexDao;
import com.zhiwen.crawler.url.store.spi.UrlsService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhiwenzhu on 17/1/6.
 */
public class SpringBeanUtil implements ApplicationContextAware {
    private static final String[] args = new String[] {
            "classpath*:/config/spring/local/appContext-beans.xml",
            "classpath*:/config/spring/local/appContext-core.xml",
            "classpath*:/config/spring/local/appContext-mybatis.xml",
            "classpath*:/config/spring/local/appContext-dao.xml"
    };

    public static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    public static FileMessageService getFileMessageService() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(args);
        }
        return applicationContext.getBean(FileMessageService.class);
    }

    public static UrlsService getUrlsService() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(args);
        }
        return applicationContext.getBean(UrlsService.class);
    }

    public static HtmlContentParser getHp() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(args);
        }
        return applicationContext.getBean(HtmlContentParser.class);
    }

    public static CrawlerIndexDao getCrawlerIndexDao() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(args);
        }

        return applicationContext.getBean(CrawlerIndexDao.class);
    }


}
