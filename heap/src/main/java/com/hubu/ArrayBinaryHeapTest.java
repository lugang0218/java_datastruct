package com.hubu;

import java.util.HashMap;

public class ArrayBinaryHeapTest {
    public static void main(String[] args) {
        int a=16;
        a=a-1;
        a|=a>>1;
        System.out.println(a);
        a|=a>>2;
        System.out.println(a);
        a|=a>>4;
        System.out.println(a);
        a|=a>>8;
        System.out.println(a);
        a|=a>>16;
        System.out.println(a);
        a|=a>>32;
        System.out.println(a);

        System.out.println(a+1);
    }
}
