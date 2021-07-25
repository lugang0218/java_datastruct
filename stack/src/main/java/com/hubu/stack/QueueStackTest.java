package com.hubu.stack;

public class QueueStackTest {



    public static void main(String[] args) {

        QueueStack<Integer> queueStack=new QueueStack<>();
        queueStack.push(100);
        queueStack.push(200);
        queueStack.push(300);
        queueStack.push(400);
        queueStack.push(500);
        while(!queueStack.isEmpty())
            System.out.println(queueStack.pop());
    }
}
