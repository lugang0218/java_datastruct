package com.hubu.thread;
import java.util.UUID;
public class LinkedBlockingQueueTest {
    public static void main(String[] args) {
        LinkedBlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
        int number=1;
        for(;;){
            Thread t1=new Thread(()->{
                int count=0;
                while((count++)<100){
                    Runnable task=()->{
                        System.out.println(Thread.currentThread().getName()+UUID.randomUUID().toString());
                    };
                    queue.offer(task);
                }
            },"t1");

            Thread t2=new Thread(()->{
                int count=0;
                while((count++)<100){
                    Runnable task=()->{
                        System.out.println(Thread.currentThread().getName()+UUID.randomUUID().toString());
                    };
                    queue.offer(task);
                }
            },"t2");


            Thread t3=new Thread(()->{
                int count=0;
                while((count++)<100){
                    Runnable task=()->{
                        System.out.println(Thread.currentThread().getName()+UUID.randomUUID().toString());
                    };
                    queue.offer(task);
                }
            },"t3");


            t1.start();
            t2.start();
            t3.start();

            try {
                t1.join();
                t2.join();
                t3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(queue.size()!=300){
                System.out.println(queue.size());
                System.out.println("error");
                break;
            }
            System.out.println("第"+number+"次成功");
            number++;
            queue.clear();
        }
    }


}
