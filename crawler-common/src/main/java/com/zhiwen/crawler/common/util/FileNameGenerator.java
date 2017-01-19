package com.zhiwen.crawler.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhiwenzhu on 17/1/16.
 */
public class FileNameGenerator {
    private static final Map<String, AtomicInteger> indexMap = new HashMap<String, AtomicInteger>();

    public static String nextId() {

        String dateHour = currentDateSecond();
        String index = nextIndex(dateHour);
        String ip = ipHex();
        return dateHour + ip + index;
    }

    private static String ipHex() {
        return "";
    }

    private static String nextIndex(String dateSecond) {
        AtomicInteger atomicInteger = indexMap.get(dateSecond);
        if (atomicInteger == null) {
            synchronized (indexMap) {
                atomicInteger = indexMap.get(dateSecond);
                if (atomicInteger == null) {
                    atomicInteger = new AtomicInteger();
                    indexMap.clear();
                    indexMap.put(dateSecond, atomicInteger);
                }
            }
        }

        String format = "%04d";
        return String.format(format, atomicInteger.addAndGet(1));
    }

    private static String currentDateSecond() {
        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(new Date());
    }
}
