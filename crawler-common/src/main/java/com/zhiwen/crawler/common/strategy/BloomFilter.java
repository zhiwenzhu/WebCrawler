package com.zhiwen.crawler.common.strategy;

import org.apache.commons.lang3.StringUtils;

import java.util.AbstractCollection;
import java.util.BitSet;

/**
 * Created by zhiwenzhu on 17/1/9.
 */
public class BloomFilter {
    private static final int BIT_SIZE = 2 << 29;

    private static final int[] SEEDS = new int[] {3, 5, 7, 11, 13, 31, 37, 61};

    private BitSet bitSet = new BitSet(BIT_SIZE);

    private Hash[] func = new Hash[SEEDS.length];//用于存储8个随机哈希值对象

    public BloomFilter(){
        for(int i = 0; i < SEEDS.length; i++){
            func[i] = new Hash(BIT_SIZE, SEEDS[i]);
        }
    }

    public void addUrl(String url) {
        if (StringUtils.isNotBlank(url)) {
            for (Hash f : func) {
                bitSet.set(f.hash(url), true);
            }
        }
    }

    //如果参数是空字符串，返回true（即判断为已经爬取了该url），跳过该条url
    public boolean contains(String url) {
        boolean result = true;
        if (StringUtils.isNotBlank(url)) {
            for (Hash f : func) {
                result = result && bitSet.get(f.hash(url));
            }
        }

        return result;
    }


    public static class Hash{
        private int size;//二进制向量数组大小
        private int seed;//随机数种子

        public Hash(int cap, int seed){
            this.size = cap;
            this.seed = seed;
        }

        /**
         * 计算哈希值(也可以选用别的恰当的哈希函数)
         */
        public int hash(String value){
            int result = 0;
            int len = value.length();
            for(int i = 0; i < len; i++){
                result = seed * result + value.charAt(i);
            }

            return (size - 1) & result;
        }
    }


    public static void main(String[] args) {
        BloomFilter bloomFilter = new BloomFilter();

        bloomFilter.addUrl("www.baidu.com");
        bloomFilter.addUrl("wwww.163.com");

        System.out.println(bloomFilter.contains("www.baidu.com"));
        System.out.println(bloomFilter.contains("wwww.163.com"));
        System.out.println(bloomFilter.contains("www.zhiwenzhu.com"));
    }





}
