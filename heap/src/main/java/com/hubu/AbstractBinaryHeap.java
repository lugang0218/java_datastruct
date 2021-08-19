package com.hubu;

import java.util.Comparator;

public abstract class AbstractBinaryHeap<T> implements Heap<T>{
    protected int size=0;
    protected Comparator<T> comparator;
    public AbstractBinaryHeap(Comparator<T> comparator){
        this.comparator=comparator;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        return size==0;
    }

    protected int compare(T value1,T value2){
        return comparator!=null?comparator.compare(value1,value2):((Comparable<T>) value1).compareTo(value2);
    }

    public abstract void show();
}
