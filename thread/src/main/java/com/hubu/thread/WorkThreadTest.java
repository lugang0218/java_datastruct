package com.hubu.thread;
import com.hubu.queue.SingleQueue;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
public class WorkThreadTest {
    private static Object object=new Object();
    public static void main(String[] args) {
        WorkThread workThread=new WorkThread();
        SingleQueue<Runnable> singleQueue = workThread.getSingleQueue();
        Thread threads[]=new Thread[10];
        for(int i=0;i<10;i++){
            threads[i]=new Thread(()->{
                for(int j=0;j<10;j++){
                    Runnable task=new Runnable() {
                        @Override
                        public void run() {
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(UUID.randomUUID().toString());
                        }
                    };
                    synchronized (object){
                        singleQueue.offer(task);
                    }

                }
            });
        }
        for(int i=0;i<10;i++){
            threads[i].start();
        }
        for(int i=0;i<10;i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        workThread.start();
    }
}
