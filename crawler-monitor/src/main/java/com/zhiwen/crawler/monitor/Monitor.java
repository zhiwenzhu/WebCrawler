package com.zhiwen.crawler.monitor;

/**
 * Created by zhengwenzhu on 2017/2/8.
 */
public class Monitor {

    private static Monitor instance;

    private static void init() {
        if (instance == null) {
            synchronized (Monitor.class) {
                if (instance == null) {
                    instance = new Monitor();
                    instance.internalInit();
                }
            }
        }
    }


    private Monitor() {

    }

    public static void acc(long size) {
        acc(1, size);
    }

    public static void acc(long count, long size) {
        checkAndInit();
        instance.internalAcc(count, size);
    }

    private void internalAcc(long count, long size) {
        model.acc(count, size);
    }

    private Model model;

    private void internalInit() {
        model = new Model();
        MonitorServer monitorServer = new MonitorServer(model);
        monitorServer.start();

    }

    private static void checkAndInit() {
        if (instance == null) {
            init();
        }
    }
}
