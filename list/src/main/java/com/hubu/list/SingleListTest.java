package com.hubu.list;

import com.hubu.list.util.Printer;
import com.hubu.list.util.SingleListPrinter;

public class SingleListTest {
    public static void main(String[] args) {
        Printer<Integer> printer=new SingleListPrinter<>();
        SingleList<Integer> list=new SingleList<>(printer);
        list.add(12);
        list.add(13);
        list.add(14);
        list.add(15);
        list.add(16);
        list.show();
    }
}
