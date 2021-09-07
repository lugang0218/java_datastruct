package com.hubu.tree;
public class BPlusTreeTest {
    public static void main(String[] args) {
        BPlusTree<Integer,Integer> tree=new BPlusTree<>(null,100);
        for(int i=1;i<=1000000;i++){
            tree.put(i,i);
        }
        for(int i=1;i<=1000000;i++){
            if(tree.remove(i)!=i){
                System.out.println(i);
            }
        }
        for(int i=1;i<=1000000;i++){
            System.out.println(tree.get(1));
        }
    }
}