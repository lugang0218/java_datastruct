package com.hubu.stack;


import java.util.EmptyStackException;
import java.util.Objects;

/**
 * 基于动态数组实现栈
 */
public class ArrayStack<T> extends AbstractStack<T> implements Stack<T> {
    private static final int DEFAULT_CAPACITY=10;
    private int capacity=0;
    private T []elementData=null;

    public ArrayStack(int capacity){
        //check capacity
        this.capacity=capacity;
    }
    @Override
    public void push(T value) {
        if(elementData==null){
            elementData=(T [])new Object[this.capacity];
        }
        elementData[size++]=value;
    }
    @Override
    public T pop() {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return elementData[--size];
    }

    @Override
    public T peek() {
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return elementData[size-1];
    }
    @Override
    public void clear() {
        //todo
    }
}
