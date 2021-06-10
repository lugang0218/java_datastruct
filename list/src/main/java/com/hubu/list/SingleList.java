package com.hubu.list;

import com.hubu.list.util.Printer;

public class SingleList <T> extends AbstractList<T> implements List<T>{
    private Node<T> head;

    public SingleList(Printer printer) {
        super(printer);
    }

    @Override
    public void clear() {

    }
    @Override
    public boolean add(T value) {
        return add(value,false);
    }

    /**
     * 外面传入是否添加完成，最后一部添加完成可以反转
     * @param value
     * @param isFinish
     * @return
     */
    public boolean add(T value,boolean isFinish) {
        //反向插入
        Node<T> newNode=new Node<>(value);
        if(head == null) {
            head=newNode;
        }
        else{
            newNode.next=head;
            head=newNode;
        }
        if(isFinish){
            reverse();
        }
        size++;

        return true;
    }
    public void reverse(){
        Node<T> prev = null;
        Node current = head;
        Node next=null;
        while (current != null) {
            next =current.next;
            current.next=prev;
            prev=current;
            current=next;
        }
        head.next=null;
        head=prev;
    }
    @Override
    public T get(int index) {
        Node<T> node=findNode(index);
        return node.value;
    }

    private Node<T> findNode(int index) {
        Node<T> current=head;
        for(int i=0;i<index;i++){
            current=current.next;
        }
        return current;
    }

    @Override
    public T set(int index, T newValue) {
        return null;
    }

    @Override
    public boolean add(int index, T value) {
        return false;
    }


    @Override
    public T remove(int index) {
        return null;
    }
    static class Node<E>{
        protected E value;
        protected Node<E> next;
        public Node(E value,Node<E> next){
            this.value=value;
            this.next=next;
        }
        public Node(E value){
            this(value,null);
        }
    }
    public void show(){
        if(printer==null){
            return;
        }
        Node<T> current=head;
        while(current!=null){
            printer.print(current.value);
            current=current.next;
        }
    }

    public Node<T> getHead() {
        return head;
    }
}
