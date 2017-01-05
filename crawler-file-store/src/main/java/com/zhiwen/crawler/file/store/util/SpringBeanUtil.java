package com.zhiwen.crawler.file.store.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhiwenzhu on 17/1/5.
 */
public class SpringBeanUtil {
    private static final String[] args = new String[] {
            "classpath:/config/spring/local/appContext-beans.xml",
            "classpath:/config/spring/local/appContext-core.xml",
            "classpath:/config/spring/local/appContext-mybatis.xml",
            "classpath:/config/spring/local/appContext-dao.xml"
    };

    private static ApplicationContext actx = new ClassPathXmlApplicationContext(args);

    public static Object getBean(String beanId) {
        return actx.getBean(beanId);
    }

}
