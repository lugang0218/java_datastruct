package com.hubu.concurrent.task;


/**
 * 生命周期
 */
public interface LifecycleObserver<T>{


    //当事件发售那个之后，onEvent将会被查法调用
    void onEvent(T event);
}
