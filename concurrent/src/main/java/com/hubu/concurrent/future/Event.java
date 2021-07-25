package com.hubu.concurrent.future;

public  class Event {


    private ThreadState threadState;


    private Exception exception;


    public Exception getException() {
        return exception;
    }

    public ThreadState getThreadState() {
        return threadState;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setThreadState(ThreadState threadState) {
        this.threadState = threadState;
    }
}
