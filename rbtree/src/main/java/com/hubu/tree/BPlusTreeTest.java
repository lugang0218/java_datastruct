package com.hubu.tree;
public class BPlusTreeTest {
    public static void main(String[] args) {
        BPlusTree<Integer,Integer> tree=new BPlusTree<>(null,4);
        for(int i=1;i<=4;i++){
            tree.put(i,i);
        }
        System.out.println(tree.remove(3));
        System.out.println(tree.remove(4));
    }
}