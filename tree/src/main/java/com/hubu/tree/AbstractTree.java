package com.hubu.tree;

public abstract  class AbstractTree <T> implements Tree<T>{



    private Printer<T> printer;
    protected  int size;


    public AbstractTree(Printer<T> printer){
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


}
