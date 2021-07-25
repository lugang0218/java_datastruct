package com.hubu.concurrent.future;

import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableFuture<V> implements SyncFuture<V> ,Runnable {
    private Callable<V> task;


    /**
     * 执行结果
     */


    private V result;




    public CallableFuture(Callable<V> task){
        this.task=task;
    }

    private RunnableFuture<V> runnableFuture;

    private List<Observer> observerList=new ArrayList<>();
    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void run() {
        if(task!=null){

            Event event=new Event();
            event.setException(null);
            event.setThreadState(ThreadState.RUNNING);
            notifyAllObserver(event);
            try {
                result=task.call();
                for(Observer observer:observerList){
                    observer.notifyUpdate(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void notifyAllObserver(Event event){
        for(Observer observer:observerList){
            observer.notifySuccess(event);
        }
    }
}
