package com.hubu.tree.bplustree;
public class BPlusTreeTest {
    public static void main(String[] args) {
        BPlusTree<Integer, Integer> tree = new BPlusTree<>(100, null);
        for(int i=1;i<=1000000;i++){
            tree.put(i,i);
        }
        for(int i=1;i<=1000000;i++){
            tree.remove(i);
        }
        for(int i=1;i<=6;i++){
            if(tree.get(i)!=null){
                System.out.println(i);
            }
        }
    }
}
