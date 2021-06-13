package com.hubu.queue;

public abstract class AbstractQueue<T> implements Queue<T> {
    protected  int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
}
