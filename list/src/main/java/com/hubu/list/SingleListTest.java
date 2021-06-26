package com.hubu.list;
import com.hubu.list.util.InsertPolicy;
import com.hubu.list.util.Printer;
import com.hubu.list.util.SingleListPrinter;
public class SingleListTest {
    public static void main(String[] args) {
        Printer<Integer> printer=new SingleListPrinter<>();
        SingleList<Integer> list=new SingleList<>(printer, InsertPolicy.Tail);
    }
}
