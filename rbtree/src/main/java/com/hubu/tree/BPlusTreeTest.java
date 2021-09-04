package com.hubu.tree;
public class BPlusTreeTest {
    public static void main(String[] args) {
        BPlusTree<Integer,Integer> tree=new BPlusTree<>(null,100);
        for(int i=1;i<=10000000;i++){
            tree.put(i,i);
        }
        for(int i=1;i<=10000000;i++){
            if(tree.get(i)!=i){
                System.out.println(i);
            }
        }
        System.out.println("hello");
    }
}