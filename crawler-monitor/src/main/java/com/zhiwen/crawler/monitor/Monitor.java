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

    public static void start() {
        checkAndInit();
        instance.internalStart();
    }

    public static void stop() {
        checkAndInit();
        instance.internalStop();
    }

    public static void count() {
        checkAndInit();
        instance.internalCount(1);
    }

    public static void count(int num) {
        checkAndInit();
        instance.internalCount(num);
    }

    private void internalCount(int num) {

    }

    private boolean started = false;


    private void internalInit() {

    }

    private void internalStop() {

    }

    private void internalStart() {

    }

    private static void checkAndInit() {
        if (instance == null) {
            init();
        }
    }
}
