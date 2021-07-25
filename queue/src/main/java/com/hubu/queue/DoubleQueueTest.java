package com.hubu.queue;

public class DoubleQueueTest {
    public static void main(String[] args) {
        Queue<Integer> queue=new DoubleQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        queue.poll();
    }
}
