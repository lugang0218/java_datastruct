package com.hubu.tree;

public abstract class AbstractPrinter<T> implements Printer<T>{
    @Override
    public void print(T value) {
        System.out.println("value="+value);
    }
}
