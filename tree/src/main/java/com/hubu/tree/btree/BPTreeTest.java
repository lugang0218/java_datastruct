package com.hubu.tree.btree;
public class BPTreeTest {
    public static void main(String[] args) {
        BPTree<Integer,Integer> tree=new BPTree<>(null,100);
        for(int i=1;i<=1000000;i++){
            tree.put(i,i);
        }
        System.out.println();
        for(int i=1;i<=100;i++){
            System.out.println(tree.get(i));
        }
    }
}
