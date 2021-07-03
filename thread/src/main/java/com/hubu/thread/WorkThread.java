package com.hubu.thread;


import com.hubu.queue.SingleQueue;

import java.util.concurrent.locks.LockSupport;

/**
 * 工作线程
 */
public class WorkThread extends Thread {
    //任务队列
    private volatile SingleQueue<Runnable> singleQueue=new SingleQueue<>();
    @Override
    public void run() {
        while(true) {
            //如果当前队列为空，线程阻塞
            if (singleQueue.isEmpty()) {
                System.out.println("没有任务，线程阻塞");
                LockSupport.park();
            }
            Runnable task = singleQueue.pool();
            if(task!=null){
                task.run();
            }
        }
    }
    public SingleQueue<Runnable> getSingleQueue() {
        return singleQueue;
    }
}
