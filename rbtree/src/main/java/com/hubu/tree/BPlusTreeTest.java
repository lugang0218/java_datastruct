package com.hubu.tree;
public class BPlusTreeTest {
    public static void main(String[] args) {
        BPlusTree<Integer,Integer> tree=new BPlusTree<>(null,4);
        for(int i=1;i<=6;i++){
            tree.put(i,i);
        }
        System.out.println(tree.isValid());
        tree.remove(6);
        tree.remove(5);
        tree.remove(4);
        System.out.println(tree.get(1));
        System.out.println(tree.get(2));
        System.out.println(tree.get(3));

        System.out.println(tree.isValid());
    }
}