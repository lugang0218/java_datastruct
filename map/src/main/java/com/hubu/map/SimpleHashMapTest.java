package com.hubu.map;
import java.util.ArrayList;
import java.util.List;
public class SimpleHashMapTest {
    public static void main(String[] args) {
        SimpleHashMap<Integer,Integer> simpleHashMap=new SimpleHashMap<>();
        for(int i=0;i<1000000;i++){
            simpleHashMap.put(i+1,i);
        }
        Integer integer = simpleHashMap.get(1000000);
        System.out.println(integer);
    }

    public void testTreeMap(){
        TreeMap<Integer,Integer> treeMap=new TreeMap<>();
        for(int i=0;i<1000000;i++){
            treeMap.put(i+1,i);
        }
        Integer integer = treeMap.get(1000000);
        System.out.println(integer);
    }

    public void testArrayList(){
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<1000000;i++){
            list.add((i+1));
        }
        System.out.println(list.get(1000000-1));
    }
    /**
     * 测试右移 右移n位，就出一2的n次方
     */
    public void test(){
        byte a=12;
        System.out.println(a>>1);
    }
}
