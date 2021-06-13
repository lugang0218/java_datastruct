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
        binarySearchTree.add(80);
        binarySearchTree.add(50);
//        binarySearchTree.add(30);
//        binarySearchTree.add(60);
//        binarySearchTree.add(15);
//        binarySearchTree.add(40);
//        binarySearchTree.add(14);
//        binarySearchTree.add(55);
//        binarySearchTree.add(70);
//        binarySearchTree.add(100);
        binarySearchTree.remove(80);
        binarySearchTree.midOrder();

    }
}
