package com.hubu.queue;
public class DoubleQueue<T> extends AbstractQueue<T> implements Queue<T> {
    private DoubleQueueNode<T> head;
    private DoubleQueueNode<T> tail;
    @Override
    public boolean contains(T value) {
        return false;
    }

    @Override
    public void clear() {
    }

    public T getFirstValue(){
        return head!=null?head.value:null;

    }

    public T getLastValue(){
        return tail!=null?tail.value:null;

    }
    @Override
    public void offer(T value) {
        DoubleQueueNode<T> newNode=new DoubleQueueNode<>(value,null,null);
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

    @Override
    public T poll() {
        if(size==0){
            return null;
        }
        T value=head.value;
        DoubleQueueNode<T> next=head.next;
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
    }

    @Override
    public T peek() {
        return getFirstValue();
    }

    static class DoubleQueueNode<T>{
        private  DoubleQueueNode prev;
        private  DoubleQueueNode next;
        private T value;
        public DoubleQueueNode(T value,DoubleQueueNode<T> prev,DoubleQueueNode<T> next){
            this.value=value;
            this.next=next;
            this.prev=prev;
        }
    }
}
