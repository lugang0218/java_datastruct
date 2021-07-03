package com.hubu.map;
public class TreeMapTest {
    public static void main(String[] args) {
        TreeMap<Integer,String> map=new TreeMap<>();
        map.put(12,"hello world");
        map.put(12,"world hello");
        map.put(13,"java");
        map.remove(13);
        map.remove(12);
        System.out.println(map.get(12));
    }
}
