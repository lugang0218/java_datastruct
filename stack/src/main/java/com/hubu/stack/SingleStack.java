package com.hubu.stack;


/**
 * 基于单链表实现栈
 * @param <T>
 */
public class SingleStack<T> extends AbstractStack<T> implements Stack<T>{
    private Node<T> head;
    @Override
    public void push(T value) {
        if(head==null){
            head=new Node<>(value);
        }
        else{
            Node<T> newNode=new Node<>(value);
            newNode.next=head;
            head=newNode;
        }
        size++;
    }

    @Override
    public T pop() {
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
        return head!=null?head.value:null;
    }

    @Override
    public void clear() {

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
