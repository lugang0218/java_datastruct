package com.hubu.tree;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
public class BinarySearchTreeTest {
    static class MyComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1-o2;
        }
    }
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree=new BinarySearchTree<>(new MyComparator());
        List<Integer> list = Arrays.asList(4, 2, 7, 1, 3,6,5);
        tree.build(list);
        tree.levelOrder();
        System.out.println(tree.height());
    }
}
