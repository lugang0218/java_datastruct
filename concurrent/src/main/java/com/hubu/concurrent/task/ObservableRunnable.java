package com.hubu.concurrent.task;

public class ObservableRunnable implements Runnable {

    private Runnable task;

    private LifecycleObserver observer;

    public ObservableRunnable(Runnable task, RunnableLifecycleObserver observer) {
        this.task=task;
        this.observer=observer;
    }

    @Override
    public void run() {

        //通知观察者有一个事件发生了，该事件的线程运行的状态是RUNNING，没有异常发生
        notifyObserver(new Event(ThreadState.RUNNING,null));
        try {
            task.run();
        } catch (Throwable e) {
            //如果发生异常，就通知所有的观察者，捕获事件
            notifyObserver(new Event(ThreadState.ERROR, e));
        }

        //最后通知所有的观察者一个成功返回的事件
        notifyObserver(new Event(ThreadState.DOWN, null));
    }


    public enum ThreadState{
        START,RUNNING,DOWN,ERROR;
    }


    //当某一个事件发生之后 通知所有的观察者

    public void notifyObserver(Event event) {
        observer.onEvent(event);
    }
    public static class Event{
        private ThreadState state;
        private Throwable cause;

        public ThreadState getState() {
            return state;
        }

        public Throwable getCause() {
            return cause;
        }

        public Event(ThreadState state, Throwable cause) {
            this.state = state;
            this.cause = cause;
        }
    }
}
