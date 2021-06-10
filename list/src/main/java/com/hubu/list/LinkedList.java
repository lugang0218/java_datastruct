package com.hubu.list;

import com.hubu.list.util.LinkedListPrinter;
import com.hubu.list.util.Printer;

public class LinkedList<T> extends AbstractList<T> implements List<T>{
    private Node<T> head;
    private Node<T> tail;

    public LinkedList(Printer printer) {
        super(printer);
    }

    @Override
    public void clear() {
        Node<T> current=head;
        while(current!=null){
            Node<T> next=current.next;
            current.next=null;
            current.prev=null;
            if(next!=null){
                next.prev=null;
            }
            current=next;
        }
        head=tail=null;
        size=0;
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
        //TODO check index
        Node<T> newNode=new Node<>(value,null,null);
        if(index==0){
            if(head==null){
                head=tail=newNode;
            }
            else{
                head.prev=newNode;
                newNode.next=head;
                head=newNode;
            }
        }
        else if(index==size){
            Node<T> l=tail;//必须要用l，否则第一个节点会失效
            newNode.prev=l;
            tail=newNode;
            l.next=newNode;
        }
        else{
            Node<T> node=findNode(index);
            newNode.prev=node.prev;
            newNode.next=node;
            node.prev.next=newNode;
            node.prev=newNode;
        }
        size++;
        return true;
    }
    @Override
    public T remove(int index) {
        //todo 校验index
        T value=null;
        if(index==0){
            //删除首节点
            Node<T> h=head;
            value=h.value;
            head=head.next;
            h.next=null;
            if(h.prev!=null){
                head.prev=null;
            }
        }
        else{
            Node<T> node=findNode(index);
            value=node.value;
            node.prev.next=node.next;
            if(node.next!=null){
                node.next.prev=node.prev;
            }
        }
        return value;
    }

    public Node<T> findNode(int index){
        Node<T> headNode=head;
        for(int i=0;i<index;i++){
            headNode=headNode.next;
        }
        return headNode;
    }

    public void reverse(){
        if(head==null||head.next==null){
            return;
        }
        Node<T> tempTail=null;//用来保存尾节点
        Node<T>current=head;
        Node<T>prev=null;
        Node<T>next=null;
        while(current!=null){
            next=current.next;
            current.next=prev;
            if(prev==null){
                //说明当前current是尾节点
                tempTail=current;
            }
            current.prev=next;

            prev=current;
            current=next;
        }
        head.next=null;
        head=prev;
        tail=tempTail;
    }
    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public Node(T value) {
            this(value, null, null);
        }
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void show(){
        if(head==null||tail==null){
            return;
        }
        Node<T> current=head;
        if(!(printer instanceof LinkedListPrinter)) {
            throw new RuntimeException("printer error");

        }
        LinkedListPrinter<T> linkedListPrinter = (LinkedListPrinter<T>) printer;
        while(current!=null){
                if(current.next==null){
                    linkedListPrinter.print(current.value,true);
                }
                else{
                    linkedListPrinter.print(current.value,false);
                }
            current=current.next;
        }
    }
}
