package com.hubu.concurrent.future;

import java.util.concurrent.Future;
public interface SyncFuture<V> extends Future<V> {
    boolean isSuccess();
    void addObserver(Observer observer);
}
