package com.hubu.tree;
public class BPlusTreeTest {
    public static void main(String[] args) {
        BPlusTree<Integer,Integer> tree=new BPlusTree<>();
        tree.insert(12,12);
        tree.insert(13,13);
        tree.insert(14,14);
        tree.insert(15,15);
        tree.insert(16,16);
        tree.insert(17,17);
        tree.insert(18,18);
        tree.insert(19,19);
        tree.insert(20,20);
    }
}
