package com.hubu.tree2;
public class BinarySearchTreeTest {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(null);
        tree.add(12);
        tree.add(14);
        tree.add(15);
        tree.add(18);
        tree.testFindNode(18);
    }
}
