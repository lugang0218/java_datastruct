package com.hubu.list;
/**
 * 单项环链表
 */
public class SingleCircleList<E>{
    private Node<E> head;
    static class Node<T>{
        private T value;
        private Node<T> next;
        public Node(T value,Node<T> next){
            this.value=value;
            this.next=next;
        }
        public Node(T value){
            this(value,null);
        }
    }

    public void add(E value){
        if(head==null){
            head=new Node<>(value,null);
            head.next=head;
        }
        else{
            Node<E> newNode=new Node<>(value,null);
            Node<E> tempHead = head;
            while(tempHead.next!=head){
                tempHead=tempHead.next;
            }
            tempHead.next=newNode;
            newNode.next=head;
        }
    }
    public void show(){
        if(head==null){
            return;
        }
        Node<E> current=head;
        while(current.next!=head){
            System.out.println(current.value);
            current=current.next;
        }
        System.out.println(current.value);
    }
}
