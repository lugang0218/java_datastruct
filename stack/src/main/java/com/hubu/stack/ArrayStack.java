package com.hubu.stack;
import java.util.EmptyStackException;
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
        if(reSize(size+1)){
            int newCapacity=newCapacity(size+1);
            copyToNewStack(newCapacity);
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
        for(int i=0;i<size();i++){
            elementData[i]=null;
        }
        size=0;
    }
    //判断是否需要扩容
    public boolean reSize(int minNeedSize){
        return minNeedSize>capacity;
    }
    public int newCapacity(int minNeedSize){
        return this.capacity*2;
    }
    //创建size这么大的新数组，用于扩容
    public void copyToNewStack(int newCapacity){
        T []newArray=(T[])new Object[newCapacity];
        //拷贝原来的数组到新的数组
        for(int i=0;i<this.capacity;i++){
            newArray[i]=elementData[i];
        }
        elementData=newArray;
        capacity=newCapacity;
    }
}
