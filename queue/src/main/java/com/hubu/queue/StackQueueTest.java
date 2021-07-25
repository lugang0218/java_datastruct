package com.hubu.queue;
/**
 * 栈实现队列测试
 */
public class StackQueueTest {
    public static void main(String[] args) {
        StackQueue<Integer> stackQueue=new StackQueue<>();
        stackQueue.offer(100);
        stackQueue.offer(200);
        stackQueue.offer(300);
        stackQueue.offer(400);
        while(!stackQueue.isEmpty()){
            System.out.println(stackQueue.poller());
        }
    }
}
