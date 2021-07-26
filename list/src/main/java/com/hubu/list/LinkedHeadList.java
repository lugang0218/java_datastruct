package com.hubu.list;

import com.hubu.list.util.Printer;

public class LinkedHeadList <T> extends AbstractList<T> implements List<T>{
    private LinkedList.Node<T> head=null;
    private LinkedList.Node<T> tail=null;
    public LinkedHeadList(Printer printer) {
        super(printer);
        head=new  LinkedList.Node(null,null,null);
        tail=new  LinkedList.Node(null,null,null);
        head.next=tail;
        tail.prev=head;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean add(T value) {
        addFromTail(value);
        return true;
    }

    public void addFromHead(T value){
        LinkedList.Node<T> newNode=new LinkedList.Node<>(value,null,null);
        head.next.prev=newNode;
        newNode.next=head.next;
        head.next=newNode;
        newNode.prev=head;
    }

    public void addFromTail(T value){
        LinkedList.Node<T> newNode=new LinkedList.Node<>(value,null,null);
        tail.prev.next=newNode;
        newNode.prev=tail.prev;
        tail.prev=newNode;
        newNode.next=tail;
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
        return false;
    }

    @Override
    public T remove(int index) {
        return null;
    }
}
