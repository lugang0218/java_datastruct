package com.hubu.list;
public class MyLinkedList<T> extends AbstractList<T> implements List<T>{
    private Node<T> head;
    private Node<T> tail;
    @Override
    public void clear() {

    }
    @Override
    public boolean add(T value) {
        Node<T> l=tail;
        Node<T> newNode=new Node<>(value,l,null);
        tail=newNode;
        if(l==null){
            head=newNode;
        }
        else{
            l.next=newNode;
        }
        size++;
        return true;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(int index, T newValue) {
        return null;
    }

    @Override
    public boolean add(int index, T value) {
        return true;
    }




    static class Node<T>{
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value,Node<T> prev,Node<T> next) {
            this.value=value;
            this.prev=prev;
            this.next=next;
        }
        public Node(T value){
            this(value,null,null);
        }
    }
}
