package com.hubu.concurrent.future;

public interface Observer<T> {
    void notifyUpdate(T result);





    void notifySuccess(Event event);




    void notifyFail(Event event);




}
