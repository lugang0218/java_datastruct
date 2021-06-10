package com.hubu.list;

import com.hubu.list.util.LinkedListPrinter;
import com.hubu.list.util.Printer;

public class LinkedTest {
    public static void main(String[] args) {
        Printer<Integer> printer=new LinkedListPrinter<>();
        LinkedList<Integer> list=new LinkedList<>(printer);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.show();
    }
}
