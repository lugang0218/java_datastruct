package com.hubu.tree;
import com.hubu.tree.printer.BinaryTrees;
import java.util.Comparator;

public class RedBlackTreeTest {
    public static void main(String[] args) {
        RedBlackTree<Integer,Integer> redBlackTree=new RedBlackTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        for(int i=1;i<=8;i++){
            redBlackTree.put(i,i);
        }


        BinaryTrees.print(redBlackTree, BinaryTrees.PrintStyle.LEVEL_ORDER);
    }
}
