package com.zhiwen.crawler.monitor.utils;


import junit.framework.TestCase;

/**
 * Created by zhengwenzhu on 2017/2/9.
 */
public class CircleQueueTest extends TestCase {

    public void testAdd() throws Exception {
        CircleQueue<Integer> queue = new CircleQueue<Integer>(4);

        int e = 1;

        for (int i = 0; i < 10; i ++) {
            queue.add(i);
        }

        Integer ele = queue.poll();
        while (ele != null) {
            System.out.println(ele);
            ele = queue.poll();
        }

    }
}