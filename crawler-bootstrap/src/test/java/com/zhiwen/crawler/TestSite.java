package com.zhiwen.crawler;


/**
 * Created by zhengwenzhu on 2017/2/8.
 */

import com.zhiwen.crawler.site.SimpleHandler;
import org.eclipse.jetty.server.Server;

public class TestSite {

    public static final int SERVER_PORT = 4096;

    private static volatile Server server;

    public static void start() throws Exception {
        if (server == null) {
            server = new Server(SERVER_PORT);
            server.setHandler(new SimpleHandler());
        }
        if (!server.isStarted()) {
            server.start();
            System.err.println("Server started at " + server.getURI());
        }  else {
            System.err.println("Server already started at " + server.getURI());
        }
    }

    public static void shutdown() throws Exception {
        if (server != null && server.isStarted()) {
            server.stop();
        }
    }


    public static void main(String[] args) throws Exception {
        start();
    }
}
