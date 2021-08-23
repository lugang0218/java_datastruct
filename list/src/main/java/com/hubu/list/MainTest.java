package com.hubu.list;

import java.util.HashSet;
import java.util.Iterator;

public class MainTest {
    public static int singleNumber(int[] nums) {


        HashSet<Integer> set=new HashSet<>();
        for(int i=0;i<nums.length;i++){
            boolean result=set.add(nums[i]);
            if(!result){
                set.remove(nums[i]);
            }
        }
        Iterator<Integer> iterator = set.iterator();
        return iterator.next();
    }

    public static void main(String[] args) {
        int array[]=new int[]{4,1,2,1,2};
        System.out.println(singleNumber(array));
    }
}
