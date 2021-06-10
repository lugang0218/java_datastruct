package com.hubu.list;

import com.hubu.list.util.Printer;

public abstract class AbstractList<T> implements List<T> {

    protected Printer<T> printer;

    public AbstractList(Printer printer){
        this.printer=printer;
    }
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
