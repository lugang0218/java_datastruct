package com.hubu.tree;

import java.util.Comparator;

public abstract  class AbstractTree <T> implements Tree<T>{


    private Comparator<T> comparator;
    private Printer<T> printer;
    protected  int size;
    protected AbstractNode<T> root;
    public AbstractTree(Printer<T> printer){
        this.printer=printer;
    }



    public AbstractTree(Printer<T> printer,Comparator<T> comparator){
        this.comparator=comparator;
        this.printer=printer;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void print(T value) {

    }



    protected   int compare(T value1,T value2){
        return comparator!=null?comparator.compare(value1,value2)
                :((Comparable<T>)value1).compareTo(value2);
    }

    public abstract void afterAdd();
}
