package com.hubu.list;
import com.hubu.list.util.Asserts;
public class SkipListTest {
    public static void main(String[] args) {
        SkipList<Integer,Integer> list=new SkipList<>();
        for(int i=1;i<=1000000;i++){
            list.put(i,i+100);
        }
        System.out.println(list.level());
        for(int i=1;i<=1000000;i++){
            if(list.get(i)!=i+100){
                System.out.println(false);
            }
        }
        System.out.println(list.size());;
        Asserts.test(list.size()==1000000);
    }
}
