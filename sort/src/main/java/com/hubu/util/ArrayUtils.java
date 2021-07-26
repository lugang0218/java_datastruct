package com.hubu.util;


import java.util.Random;

/**
 *
 */
public class ArrayUtils {




    private static Random random=new Random();


    /**
     *
     *
     *
     * @param numbers 个数
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static Integer [] randomInteger(int numbers, int min, int max){
        Integer []array=new Integer[numbers];
        for(int i=0;i<numbers;i++){
            int value=random.nextInt(max-min+1)+min;
            array[i]=value;
        }
        return array;
    }
    public static void main(String[] args) {
    }

}
