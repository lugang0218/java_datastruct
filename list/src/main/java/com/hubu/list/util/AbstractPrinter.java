package com.hubu.list.util;

public abstract class AbstractPrinter<T> implements Printer<T> {
    @Override
    public void print(T value) {
        System.out.println("value="+value);
    }
}
