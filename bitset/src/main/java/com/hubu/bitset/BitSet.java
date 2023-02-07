package com.hubu.bitset;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
public class BitSet {
    private byte[] bits;
    //byte数组中每一个数字都要代表八位
    public static final int DEFAULT_CAPACITY = 1<<10;//最大容量是1左移10位
    public BitSet(){
        this.bits = new byte[DEFAULT_CAPACITY];
        //System.out.println(bits.length);//输出一下1左移10位的数字是多少
    }
    public static void main(String[] args) {
        BitSet map = new BitSet();
        Set<Integer> set = new HashSet<Integer>();
        Random random = new Random();
        for (int i = 0 ;i<800;i++){
            int i1 = random.nextInt(DEFAULT_CAPACITY);
            map.add(i1);
            set.add(i1);
        }
        for (int i = 100; i < 300; i++) {
            boolean contains = map.contains(i);
            if (contains){
                //用已知的set 来确定这个Map写的没问题
                if (set.contains(i)){
                    System.out.print(i + " is contains by set!!! ");
                }
                System.out.println(i + " is contains!!! ");
            }
            else {
                if (!set.contains(i)){
                    System.out.print((i + " is not contains by set !!!"));
                }
                System.out.println(i + " is not contains!");
            }
        }
    }
    public void add(int value){
        //找到value值在byte数组中的位置是在哪里
        int index = getIndex(value);
        //求余
        int position = getPosition(value);
        bits[index] |=1<<position;
    }
    public boolean contains(int value){
        int index = getIndex(value);
        int position = getPosition(value);
        return (bits[index] & 1<<position) != 0;
    }
    private int getPosition(int value){
        // return value%8;
        return value & 0x07;
    }
    //找到value值当前所在数组中的位置
    private int getIndex(int value){
        // return value/8;
        return value>>3;
        //byte数组每个数字代表八位，除以8就可以得到数组里边的位置
    }
}
