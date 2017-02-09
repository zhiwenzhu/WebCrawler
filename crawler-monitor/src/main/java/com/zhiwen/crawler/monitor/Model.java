package com.zhiwen.crawler.monitor;

import com.zhiwen.crawler.monitor.utils.CircleQueue;

/**
 * Created by zhengwenzhu on 2017/2/9.
 */
public class Model {
    private CircleQueue<MinuteStatistics> hourDetail = new CircleQueue<MinuteStatistics>(60);

    private long startedTime;

    private long totalBytes;
    private long totalCount;

    public void acc(long count, long bytes) {
        totalBytes += bytes;
        totalCount += count;
    }

    public static class MinuteStatistics {
        private long timestamp;
        private long count;
        private long bytes;

        public MinuteStatistics() {
            long ts = System.currentTimeMillis();
            long mod = ts % 1000 * 60;
            timestamp = ts - mod;

            count = 0;
            bytes = 0;
        }

        public void inc() {
            count++;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public long getCount() {
            return count;
        }
    }


    public CircleQueue<MinuteStatistics> getHourDetail() {
        return hourDetail;
    }

    public long getStartedTime() {
        return startedTime;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public long getTotalCount() {
        return totalCount;
    }
}

