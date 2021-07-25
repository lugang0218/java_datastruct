package com.hubu.queue;

public class SingleQueue<T> extends AbstractQueue<T> implements Queue<T> {
    private QueueNode<T> head=null;
    @Override
    public boolean contains(T value) {
        if(head==null){
            return false;
        }
        for(QueueNode<T> current=head;current!=null;current=current.next){
            if(current.value.equals(value)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void offer(T value) {
        if(head==null){
            head=new QueueNode<>(value);
            size++;
            return ;
        }
        else{
            QueueNode<T> current=null;
            QueueNode<T> temp=null;
            for(current=head;current!=null;current=current.next){
                temp=current;
            }
            QueueNode<T>queueNode=new QueueNode<>(value);
            temp.next=queueNode;
            size++;
        }
    }

    @Override
    public T poll() {
        if(head==null){
            return null;
        }
        T value=head.value;
        head=head.next;
        size--;
        return value;
    }

    @Override
    public T peek() {
        if(head==null){
            return null;
        }
        return head.value;
    }


    static class QueueNode<T>{
        private T value;
        private QueueNode<T> next;

        public QueueNode(T value,QueueNode<T> next){
            this.value=value;
            this.next=next;
        }

        public QueueNode(T value){
            this(value,null);
        }

    }
}
