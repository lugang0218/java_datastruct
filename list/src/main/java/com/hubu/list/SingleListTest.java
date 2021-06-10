package com.hubu.list;
public class SingleListTest {
    public static void main(String[] args) {
        SingleList<Integer> list=new SingleList<>();
        list.add(12);
        list.add(13);
        list.add(14);
        list.add(15);
        list.add(16);
        list.reverse();
    }
}
