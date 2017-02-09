package com.zhiwen.crawler.monitor;

import junit.framework.TestCase;

/**
 * Created by zhengwenzhu on 2017/2/9.
 */
public class MonitorServerTest extends TestCase {

    public void testStart() throws Exception {
        Model model = new Model();
        model.acc(2, 234222);
        MonitorServer server = new MonitorServer(model);

        server.start();

        System.in.read();
    }
}