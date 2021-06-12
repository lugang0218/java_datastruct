package com.hubu.tree;


import java.util.Comparator;

public class BinarySearchTree <T>{


    private Node<T> root;
    private int size=0;


    private Comparator comparator;


    public BinarySearchTree(Comparator<T> comparator){
        this.comparator=comparator;
    }
    public void add(T value){
        Node<T> newNode=new Node<>(value);
        if(root==null){
            root=newNode;
            return;
        }
        int compare=0;
        Node<T> current=root;
        Node<T> prev=null;
        while(current!=null){
            prev=current;
            compare=compare(value,current.value);
            if(compare>0){
                current=current.right;
            }
            else{
                current=current.left;
            }
        }
        if(compare>0){
            prev.right=newNode;
        }
        else{
            prev.left=newNode;
        }
    }
    public int compare(T value1,T value2){
        return comparator.compare(value1,value2);
    }


    //添加节点
    static class Node<T>{
        T value;
        Node<T> left;
        Node<T> right;
        public Node(T value,Node<T> left,Node<T> right){
            this.value=value;
            this.left=left;
            this.right=right;
        }
        public Node(T value){
            this(value,null,null);
        }
    }


    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree=new BinarySearchTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        binarySearchTree.add(100);
        binarySearchTree.add(80);
        binarySearchTree.add(90);
        binarySearchTree.add(200);
        binarySearchTree.add(150);
    }
}
