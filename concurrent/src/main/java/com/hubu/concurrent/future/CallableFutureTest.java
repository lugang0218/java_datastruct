package com.hubu.concurrent.future;
import java.util.concurrent.Callable;
public class CallableFutureTest {
    public static void main(String[] args) {
        CallableFuture<String> future=new CallableFuture(new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(2000);
                return "Hello world";
            }
        });
        future.addObserver(new Observer() {
            @Override
            public void notifyUpdate(Object result) {
                System.out.println(Thread.currentThread().getName());
                System.out.println("result="+result);
            }

            @Override
            public void notifySuccess(Event event) {
                System.out.println(event.getException());
                System.out.println(event.getThreadState());
            }

            @Override
            public void notifyFail(Event event) {

            }
        });
        Thread t1=new Thread(future);
        t1.start();
    }

}
