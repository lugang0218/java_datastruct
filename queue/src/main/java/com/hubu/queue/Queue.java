package com.hubu.queue;
/**
 * 队列接口
 */
public interface Queue<T> {
    int size();
    boolean contains(T value);
    void clear();
    void offer(T value);
    T pool();
    T peek();
    boolean isEmpty();
}
