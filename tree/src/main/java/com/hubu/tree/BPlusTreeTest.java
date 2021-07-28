package com.hubu.tree;
public class BPlusTreeTest {
    public static void main(String[] args) {
        BplusTree<Integer,Integer> tree=new BplusTree<>(4);
        tree.add(1,1);
        tree.add(2,2);
        tree.add(3,3);
        tree.add(4,4);
        tree.add(5,5);
    }
}
