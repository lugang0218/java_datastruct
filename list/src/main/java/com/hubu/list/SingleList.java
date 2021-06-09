package com.hubu.list;
public class SingleList <T> extends AbstractList<T> implements List<T>{
    private Node<T> head;
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
        return true;
    }
    public void reverse(){
        Node<T> prev = head;
        Node current = head.next;
        Node tempNode=null;
        while (current != null) {
            tempNode =current.next;
            current.next=prev;
            prev=current;
            current=tempNode;
        }
        head.next=null;
        head=prev;
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
    static class Node<E>{
        private E value;
        private Node<E> next;
        public Node(E value,Node<E> next){
            this.value=value;
            this.next=next;
        }
        public Node(E value){
            this(value,null);
        }
    }
}
