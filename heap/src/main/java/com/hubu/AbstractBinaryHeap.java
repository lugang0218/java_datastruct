package com.hubu;

public abstract class AbstractBinaryHeap<T> implements Heap<T>{
    protected int size=0;

    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        return size==0;
    }
}
