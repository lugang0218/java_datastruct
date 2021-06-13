package com.hubu.queue;
public class SingleQueueTest {
    public static void main(String[] args) {
        Queue<Integer> queue=new SingleQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        System.out.println(queue.contains(5));
        System.out.println(queue.contains(6));
    }
}
