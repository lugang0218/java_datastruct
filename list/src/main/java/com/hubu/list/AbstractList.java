package com.hubu.list;
public abstract class AbstractList<T> implements List<T> {

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
