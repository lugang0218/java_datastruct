package com.hubu.queue;


/**
 *
 * 单向循环链表
 *
 */

public class SingleCircleQueue<T>extends AbstractQueue<T> implements Queue<T>{
    private Node<T> head=null;
    @Override
    public boolean contains(T value) {
        return false;
    }


    @Override
    public void clear() {

    }

    @Override
    public void offer(T value) {
        /**
         * 组成环状
         */
        size++;
        if(head==null){
            head=new Node(value,null);
            head.next=head;
        }
        else{
            Node<T> newNode=new Node<>(value,null);
            Node<T> current=head;
            while(current.next!=head){
                current=current.next;
            }
            Node next=current.next;
            current.next=newNode;
            newNode.next=next;
        }
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }


    class Node<E>{
        public Node<E> next;
        public E data;

        public Node(E data,Node<E> next){
            this.data=data;
            this.next=next;
        }
    }
}
