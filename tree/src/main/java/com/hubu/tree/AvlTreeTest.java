package com.hubu.tree;
import java.util.Comparator;
public class AvlTreeTest {
    public static void main(String[] args) {
        AvlTree<Integer> avlTree=new AvlTree<>((value)->{
            System.out.println("result="+value);
        }, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        avlTree.add(6);
        avlTree.add(4);
        avlTree.add(5);
        avlTree.preOrder();
    }
}
