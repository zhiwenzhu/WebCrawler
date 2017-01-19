package com.zhiwen.crawler.url.store.util;

import com.zhiwen.crawler.url.store.spi.UrlsFileNameService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chu on 17-1-19.
 */
public class SpringUtil {
    private static final String[] args = new String[] {
            "classpath*:/config/spring/local/appContext-beans.xml",
            "classpath*:/config/spring/local/appContext-core.xml",
            "classpath*:/config/spring/local/appContext-mybatis.xml",
            "classpath*:/config/spring/local/appContext-dao.xml"
    };

    public static ApplicationContext applicationContext;

    public static UrlsFileNameService getFileNameService() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(args);
        }
        return applicationContext.getBean(UrlsFileNameService.class);
    }
}
