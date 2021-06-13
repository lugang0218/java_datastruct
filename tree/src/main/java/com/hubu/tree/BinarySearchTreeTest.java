package com.hubu.tree;

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
        binarySearchTree.add(100);
        binarySearchTree.add(80);
        binarySearchTree.add(90);
        binarySearchTree.add(200);
        binarySearchTree.add(150);
    }
}
