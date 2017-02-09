package com.zhiwen.crawler.monitor.utils;

/**
 * Created by zhengwenzhu on 2017/2/8.
 */
public class CircleQueue<E> {

    private int capacity;

    private E[] nodes;

    private int index = 0;

    private int size = 0;

    @SuppressWarnings("unchecked")
    public CircleQueue(int capacity) {
        this.capacity = capacity;
        this.nodes = (E[]) new Object[capacity];
    }

    public void add(E e) {
        nodes[index] = e;
        if (size < capacity) {
            size++;
        }
        next();
    }

    public E top() {
        if (size > 0) {
            return nodes[preview()];
        } else {
            return null;
        }
    }


    public E poll() {
        if (size > 0) {
            index = preview();
            size --;
            return nodes[index];
        }

        return null;
    }

    private void next() {
        index = (++index) % capacity;
    }

    private int preview() {
        return (index + capacity - 1) % capacity;
    }

    public int size() {
        return size;
    }
}
