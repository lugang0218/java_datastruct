package com.hubu.queue;
import com.hubu.stack.ArrayStack;
import com.hubu.stack.Stack;

/**
 *
 *
 *
 * 用栈实现队列
 */
public class StackQueue<T> {


    /**
     * 数据栈
     */
    Stack<T> dataStack=new ArrayStack<>(100);


    /**
     * 临时栈
     */
    Stack<T> tempStack=new ArrayStack<>(100);

    public boolean isEmpty(){

        return dataStack.isEmpty();
    }

    public int getSize(){
        return dataStack.size();
    }
    public void offer(T t){
        if(dataStack.isEmpty()){
            dataStack.push(t);
            return ;
        }
        while(!dataStack.isEmpty()){
            tempStack.push(dataStack.pop());
        }
        dataStack.push(t);
        while(!tempStack.isEmpty())dataStack.push(tempStack.pop());
    }

    public T poller(){
        return dataStack.pop();
    }
}
