package com.hubu.list;
public interface Queue <T>{
    void offer(T value);
    T poll();
    boolean isEmpty();
}
