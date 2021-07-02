package com.hubu.tree;
public class RedBlackTreeTest {
    public static void main(String[] args) {
        float []array={4,3,6,1,3,5,8,0,7,10,6.5f,7.5f,9,11};
        RedBlackTree<Float,Float> redBlackTree=new RedBlackTree<>(null);
        for(int i=0;i<array.length;i++){
            redBlackTree.put(array[i],array[i]);
        }
        redBlackTree.remove(5.0f);
    }
}
