package com.hubu.tree;
import java.util.ArrayList;
import java.util.List;


class Person{
    int age;
    String name;
}
public class TrieTest {
    public static void main(String[] args) {
        List<Person> list=new ArrayList<>();
        Person p=new Person();
        p.age=21;
        p.name="lugang";
        Person p2=new Person();
        p2.age=20;
        p2.name="zhangsan";
        list.add(p);
        list.add(p2);
        p.age=31;
        System.out.println(list.get(0).age);
        list.get(0).name="wangwu";
        System.out.println(p.name);
    }
}
