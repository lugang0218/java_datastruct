package com.hubu.list;

public class SingleHeadListTest {
    public static void main(String[] args) {
        SingleHeadList<Integer> list=new SingleHeadList<>(null);
        list.add(12);
        list.add(13);
        list.add(14);
        list.add(15);
        System.out.println(list.remove(0));
        System.out.println(list.remove(2));
    }
}
