package com.hubu.thread;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 阻塞队列
 */
public class LinkedBlockingQueue<T>{
    private AtomicInteger state=new AtomicInteger(0);
    private int size=0;
    private Node<T> head;
    private Node<T> tail;
    private static Lock lock=new ReentrantLock();
    public void offer(T value){
        try{
            lock.lock();
            Node<T> newNode=new Node<>(value,null,null);
            if(tail==null){
                head=tail=newNode;
            }
            else{
                newNode.prev=tail;
                tail.next=newNode;
                tail=newNode;
            }
            size++;
        }
        finally {
            lock.unlock();
        }
    }
    public T poller() {
       try{
           lock.lock();
           if(size==0){
               return null;
           }
           T value=head.data;
           Node<T> next=head.next;
           head.next=null;
           head=next;
           if(head!=null){
               head.prev=null;
           }
           else{
               tail=null;
           }
           size--;
           return value;
       }finally {
           lock.unlock();
       }
    }

    public int size() {
        return size;
    }

    public void clear() {
        this.size=0;
        head=tail=null;
    }

    static class Node<E>{
        E data;
        Node<E> next;
        Node<E> prev;
        public Node(E value,Node<E> prev,Node<E> next){
            this.data=value;
            this.prev=prev;
            this.next=next;
        }
    }
}
