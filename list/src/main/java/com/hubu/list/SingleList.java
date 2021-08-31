package com.hubu.list;
import com.hubu.list.util.InsertPolicy;
import com.hubu.list.util.Iterator;
import com.hubu.list.util.ListException;
import com.hubu.list.util.Printer;

import java.io.Serializable;

public class SingleList <T> extends AbstractList<T> implements List<T>{
    private Node<T> head;
    public SingleList(Printer printer) {
        super(printer,null);
    }
    public SingleList(Printer<T> printer, InsertPolicy insertPolicy){
        super(printer,insertPolicy);
    }
    @Override
    public void clear() {
        //将head置空即可
        if(head!=null){
            head=null;
            size=0;
        }
    }
    @Override
    public boolean add(T value) {
        //如果插入策略为空 默认使用头插法
        if(getInsertPolicy()==null){
            return addFromHead(value);
        }
        InsertPolicy insertPolicy = getInsertPolicy();
        if(insertPolicy==InsertPolicy.Head){
            return addFromHead(value);
        }
        return addFromTail(value);
    }
    private boolean addFromHead(T value){
        //反向插入
        Node<T> newNode=new Node<>(value);
        if(head == null) {
            head=newNode;
            size++;
            return true;
        }
        newNode.next=head;
        head=newNode;
        size++;
        return true;
    }
    private boolean addFromTail(T value){
        if(head == null) {
            head=new Node<>(value);
            size++;
            return true;
        }
        Node<T> current=head;
        while(current.next!=null){
            current=current.next;
        }
        current.next=new Node(value);
        return true;
    }
    private boolean addFromIndex(final int index,T value){
        if(index<0||index>size){
            return false;
        }
        if(index==0){
            head=new Node<>(value,head);
            return true;
        }
        Node<T> node = findNode(index - 1);
        node.next=new Node<>(value,node.next);
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
//        head.next=null;
        head=prev;
    }
    @Override
    public T get(int index) {
        Node<T> node=findNode(index);
        if(node!=null){
            return node.value;
        }
        return null;
    }

    private Node<T> findNode(int index) {

        //check index
        Node<T> current=head;
        for(int i=0;i<index;i++){
            current=current.next;
        }
        return current;
    }
    @Override
    public T set(int index, T newValue) {
        if(index<0||index>=size){
            throw new ListException("size="+size+"index="+index);
        }
        Node<T> node = findNode(index);
        T oldValue=node.value;
        node.value=newValue;
        return oldValue;
    }
    @Override
    public boolean add(int index, T value) {
        return addFromIndex(index,value);
    }
    @Override
    public T remove(int index) {
        //check index
        Node<T> beforeNode=findNode(index-1);
        Node<T> currentNode=beforeNode.next;
        Node<T> next=currentNode.next;
        T value=beforeNode.next.value;
        beforeNode.next=next;
        //让currentNode置空
        currentNode=null;
        System.gc();
        return value;
    }
    @Override
    protected void finalize() throws Throwable {
        System.out.println("触发垃圾回收");
    }

    static class Node<E> implements Serializable {
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
    public Iterator<T> iterator(){
        return new SingleListIterator<>();
    }

    private class SingleListIterator<T> implements Iterator<T>{
        //用于保存当前正在遍历的节点位置
        Node<T> cursor;

        public SingleListIterator(){
            this.cursor= (Node<T>) head;
        }

        @Override
        public boolean hasNext() {
            return cursor!=null;
        }

        @Override
        public T next() {
            T value=cursor.value;
            cursor=cursor.next;
            return value;
        }
    }



    public void hello(){
        head=reverse(head);
        show();
    }
    public static  <T> void test(Node<T> node){
        node=null;
    }
    public static<T> Node<T> reverse (Node<T> node){
        Node<T> prev = null;
        Node current = node;
        Node next=null;
        while (current != null) {
            next =current.next;
            current.next=prev;
            prev=current;
            current=next;
        }
        return prev;
    }
}


