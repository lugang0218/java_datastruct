package com.hubu.list.util;
public class SingleListPrinter<T> extends AbstractPrinter<T> implements Printer<T> {
    @Override
    public void print(T value) {
        System.out.println("begin time:"+System.currentTimeMillis());
        super.print(value);
        System.out.println("end time:"+System.currentTimeMillis());
    }
}
