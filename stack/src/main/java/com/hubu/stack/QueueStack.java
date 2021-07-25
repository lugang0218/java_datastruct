package com.hubu.stack;


import java.util.LinkedList;
import java.util.Queue;

/**
 * 用队列实现栈
 */
public class QueueStack<T> {
    private Queue<T> dataQueue=new LinkedList<>();

    private Queue<T> tempQueue=new LinkedList<>();

    public int size(){
        return dataQueue.size();
    }

    public boolean isEmpty(){
        return dataQueue.isEmpty();
    }

    public T pop(){
        return dataQueue.poll();
    }

    public void push(T data){
        if(dataQueue.isEmpty()){
            dataQueue.offer(data);
            return ;
        }
        while(!dataQueue.isEmpty()) tempQueue.offer(dataQueue.poll());
        dataQueue.offer(data);
        while(!tempQueue.isEmpty())dataQueue.offer(tempQueue.poll());
    }
}
