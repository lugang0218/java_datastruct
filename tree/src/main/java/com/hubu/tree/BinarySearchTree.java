package com.hubu.tree;
import java.util.Comparator;
public class  BinarySearchTree<T> extends AbstractTree<T> implements Tree<T>{
    private Node<T> root;
    private int size=0;
    private Comparator comparator;
    public BinarySearchTree(Printer<T> printer,Comparator<T> comparator){
        super(printer);
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

    @Override
    public void clear() {

    }

    @Override
    public void remove(T value) {

    }

    @Override
    public void preOrder() {

    }

    @Override
    public void midOrder() {

    }

    @Override
    public void postOrder() {

    }

    public void doPreOrder(Node<T> node){
        System.out.println(node.value);
        if(node.left!=null){
            doPreOrder(node.left);
        }
        if(node.right!=null){
            doPreOrder(node.right);
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


    public int getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size==0;
    }

}
