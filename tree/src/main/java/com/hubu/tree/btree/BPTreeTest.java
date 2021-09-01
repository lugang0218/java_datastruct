package com.hubu.tree.btree;
public class BPTreeTest {
    public static void main(String[] args) {
        BPlusTree<Integer,Integer> tree=new BPlusTree<>(null,100);

        for(int i=1;i<=10000000;i++){
            tree.put(i,i);
        }

        System.out.println("开始校验");
        System.out.println(tree.isValid());
    }
}
