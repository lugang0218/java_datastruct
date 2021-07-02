package com.hubu.tree;
public class RedBlackTreeTest {
    public static void main(String[] args) {
        int []array={4,3,6,1,3,5,8,0,7,10,11};
        RedBlackTree<Integer,Integer> redBlackTree=new RedBlackTree<>(null);
        for(int i=0;i<array.length;i++){
            redBlackTree.put(array[i],array[i]);
        }
        TreeOperation.show(redBlackTree.getRoot());
    }
}
