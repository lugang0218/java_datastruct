package com.hubu.tree.bplustree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tree {


    static class Pair{
        int first;
        int second;

        public Pair(int first,int second){
            this.first = first;
            this.second = second;
        }
    }
    static class Node {
        int nodeId;
        //关键字
        ArrayList<Pair> keyList;

        ArrayList<Integer> valueList;

        //孩子节点
        ArrayList<Integer> childList;

        //前驱节点
        int prev;
        //后继节点
        int next;
        boolean isRoot;
        boolean isLeaf;
        //父节点
        int parent;
        Node(int nodeId,boolean isLeaf,boolean isRoot){
            this.nodeId = nodeId;
            this.isLeaf = isLeaf;
            this.isRoot = isRoot;
            this.keyList = new ArrayList<>();
            if(!isLeaf){
                childList = new ArrayList<>();
            }
        }

    }

    private static int counter = 0;
    private static int value = -1;

    Node root = null;

    private final int order;
    public Tree(int order){
        this.order = order;
    }
    public void put(int key,int value){
    }

    public boolean isFull(Node node,int order) {
        assert node != null;
        return order == node.keyList.size();
    }
    public void put(Node node,int key,int value){
        if(node == null){
            root = new Node(counter++, true, true);
            root.keyList.add(new Pair(key, value));
        }else if(node.isLeaf){
            node.keyList.add(new Pair(key,value));
            if(isFull(node,order)){
                //splitNode
            }
        }
    }
}
