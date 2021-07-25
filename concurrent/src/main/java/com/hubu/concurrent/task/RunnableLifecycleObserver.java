package com.hubu.concurrent.task;
import java.util.concurrent.TimeUnit;
//观察任务的执行状态
public class RunnableLifecycleObserver implements LifecycleObserver<ObservableRunnable.Event>{
    @Override
    public void onEvent(ObservableRunnable.Event event) {
        if (event.getCause() == null) {
            System.out.println(Thread.currentThread().getName() + " notify the state of task : state -> " + event.getState());
        } else {
            System.err.println(Thread.currentThread().getName() +" execute fail and the cause is " + event.getCause().getMessage());
            try {
                // you can deal with the failing task
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
