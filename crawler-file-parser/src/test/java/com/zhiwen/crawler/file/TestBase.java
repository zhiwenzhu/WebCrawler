package com.zhiwen.crawler.file;

import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhengwenzhu on 16/10/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/config/spring/local/appContext-beans.xml",
        "/config/spring/local/appContext-core.xml"
//        "/config/spring/local/appContext-dao.xml",
//        "/config/spring/local/appContext-mybatis.xml"
})
public abstract class TestBase extends TestCase {

}
