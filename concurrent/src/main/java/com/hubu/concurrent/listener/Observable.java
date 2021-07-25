package com.hubu.concurrent.listener;

public interface Observable {
    void addObserver(Observer observer);

    void deleteObserver(Observer observer);

    void notifyAllObservers();
}
