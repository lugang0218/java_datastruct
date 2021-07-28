package com.hubu.tree.btree;

public class BPlusTreeTest {
    public static void main(String[] args) {
        BPlusTree<Integer,Integer> tree=new BPlusTree<>(4);
        tree.add(12,12);
        tree.add(13,13);
        tree.add(14,14);
        tree.add(15,15);
    }
}
