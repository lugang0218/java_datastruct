package com.hubu.list;
/**
 *
 * 头尾节点的单链表
 */
import com.hubu.list.util.Printer;
public class SingleHeadTailList <T>
        extends AbstractList<T>
        implements List<T>,Queue<T>{
    private Node<T> head=null;
    private Node<T> tail=null;
    public SingleHeadTailList(Printer printer) {
        super(printer);
    }

    @Override
    public void clear() {
        if(head!=null) head=null;
        if(tail!=null) tail=null;
        size=0;
    }

    @Override
    public boolean add(T value){
        addFromTail(value);
        size++;
        return true;
    }
    public void addFromTail(T value){
        Node<T>newNode=new Node<>(value,null);
        if(head==null){
            head=tail=newNode;
        }else{
            tail.next=newNode;
            tail=newNode;
        }
    }

    public void addFromHead(T value){
        Node<T> newNode=new Node<>(value,null);
        if(head==null) {
            head=tail=newNode;
        }else{
            newNode.next=head;
            head=newNode;
        }
    }


    private void checkIndex(int index){
        if(index<0||index>=size){
            throw new IndexOutOfBoundsException("index="+index+","+"size="+size);
        }
    }
    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current=head;
        for(int i=0;i<index;i++){
            current=current.next;
        }
        return current.data;
    }



    private void addFirst(T value){
        Node<T> newNode=new Node<>(value,null);
        if(head==null){
            head=tail=newNode;
        }
    }

    @Override
    public T set(int index, T newValue) {
        checkIndex(index);
        Node<T> current=head;
        for(int i=0;i<index;i++){
            current=current.next;
        }
        T oldData=current.data;
        current.data=newValue;
        return oldData;
    }
    private Node<T> getNode(int index){
        checkIndex(index);
        Node<T> current=head;
        for(int i=0;i<index;i++){
            current=current.next;
        }
        return current;
    }
    @Override
    public boolean add(int index, T value){
        if(head==null){
            addFirst(value);
        }else if(index==0){
            addFromHead(value);
        }else if(index==size){
            addFromTail(value);
        }else{
            Node<T> newNode=new Node<>(value,null);
            Node<T> prev=getNode(index-1);
            newNode.next=prev.next;
            prev.next=newNode;
        }
        size++;
        return true;
    }

    public void show(){
        Node<T> current=head;
        while(current!=null){
            System.out.println(current.data);
            current=current.next;
        }
    }
    @Override
    public T remove(int index) {
        checkIndex(index);
        if(index==0) {
            return removeFromHead();
        }
        else if(index==size-1){
            return removeFromTail();
        }
        else{
            size--;
            Node<T> prevNode= getNode(index);
            Node<T> deleteNode=prevNode.next;
            prevNode.next=deleteNode.next;
            deleteNode.next=null;
            T data= deleteNode.data;
            return data;
        }
    }
    private T removeFromHead(){
        checkNotEmpty();
        Node<T> next=head.next;
        Node<T> deleteNode=head;
        T data=head.data;
        head=head.next;
        deleteNode.next=null;
        size--;
        return data;
    }
    private void checkNotEmpty(){
        if(isEmpty()) {
            throw new RuntimeException("list is empty");
        }
    }
    private T removeFromTail(){
        checkNotEmpty();
        T data=null;
        if(head.next==null){
            data=head.data;
            head=tail=null;
        }
        else{
            //find prev node
            Node<T> prev = getNode(size - 2);
            Node<T> next=prev.next;
            data=next.data;
            prev.next=null;
            tail=prev;
        }
        size--;
        return data;
    }

    @Override
    public void offer(T value) {
        addFromTail(value);
        size++;
    }
    @Override
    public T poll() {
        return removeFromHead();
    }
    static class Node<E>{
        E data;
        Node next;
        public  Node(E data,Node<E> next){
            this.data=data;
            this.next=next;
        }
        public Node(E data){
            this(data,null);
        }
    }
}
