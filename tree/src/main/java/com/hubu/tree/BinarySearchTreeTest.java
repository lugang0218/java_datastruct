package com.hubu.tree;
import com.hubu.tree.printer.BinaryTrees;
import com.hubu.util.ArrayUtils;

import java.util.Comparator;
public class BinarySearchTreeTest {
    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree=new BinarySearchTree<>((value)->{
            System.out.println("result="+value);
        }, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        Integer array[]=ArrayUtils.randomInteger(1000000,1,1000000000);
        for(int i=0;i<1000000;i++){
            binarySearchTree.add(array[i]);
        }
        System.out.println(binarySearchTree.height());


        RedBlackTree<Integer,Integer> redBlackTree=new RedBlackTree<>(null);

        for(int i=0;i<1000000;i++){
            redBlackTree.put(array[i],array[i]);
        }
        System.out.println(redBlackTree.height());
//
    }
}
