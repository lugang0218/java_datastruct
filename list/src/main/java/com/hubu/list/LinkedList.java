package com.hubu.list;
import com.hubu.list.util.InsertPolicy;
import com.hubu.list.util.LinkedListPrinter;
import com.hubu.list.util.Printer;
public class LinkedList<T> extends AbstractList<T> implements List<T>{
    //默认策略
    private InsertPolicy defaultInsertPolicy=InsertPolicy.Tail;
    private Node<T> head;
    private Node<T> tail;
    public LinkedList(Printer printer) {
        super(printer);
        setInsertPolicy(defaultInsertPolicy);
    }
    public LinkedList(Printer printer, InsertPolicy insertPolicy) {
        super(printer,insertPolicy);
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
        if(getInsertPolicy()==InsertPolicy.Head){
            return addFromHead(value);
        }
        return addFromTail(value);
    }
    public boolean addFromHead(T value){
        size++;
        Node<T> h=head;
        Node<T> newNode=new Node<>(value,null,h);
        head=newNode;
        if(h==null){
            tail=newNode;

            return true;
        }
        h.prev=newNode;
        head=newNode;
        return true;
    }
    public boolean addFromTail(T value){
        size++;
        Node<T> t=tail;
        Node<T> newNode=new Node<>(value,t,null);
        tail=newNode;
        if(t==null){
            head=newNode;
            return true;
        }
        t.next=newNode;
        tail=newNode;
        return true;
    }
    @Override
    public T get(int index) {
        return findNode(index).value;
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
            Node<T> l=tail;
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
    //是否从头节点开始操作
    private boolean fromHead(int index){
        return index<=size/2;
    }

    private Node<T> findNode(int index){
        //check index
        Node<T> current=null;
        if(fromHead(index)){
            current=head;
            for(int i=0;i<index;i++){
                current=current.next;
            }
            return current;
        }
        current=tail;
        for(int i=size-1;i>index;i--){
            current=current.prev;
        }
        return current;
    }
    //双向链表的反转
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
          T value;
         Node<T> next;
         Node<T> prev;

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
