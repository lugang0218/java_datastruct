package com.hubu.list.util;
public class LinkedListPrinter<T> extends AbstractPrinter<T> implements Printer<T> {
    public void print(T value,boolean isLast){
        if(!isLast){
            System.out.print(value+"->");
        }
        else{
            System.out.print(value);
        }
    }
}
