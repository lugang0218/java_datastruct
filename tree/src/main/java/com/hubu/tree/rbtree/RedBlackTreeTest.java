package com.hubu.tree.rbtree;

public class RedBlackTreeTest {
    public static void main(String[] args) {
        RedBlackTree<Integer,String> tree=new RedBlackTree<>();
        tree.put(12,"hello");
        tree.put(13,"world");
        tree.put(14,"gender");
        tree.midOrder();
    }
}
