package com.hubu.concurrent.listener;

public class ObserverClient {
    public static void main(String[] args) {
        IntegerObservable integerObservable = new IntegerObservable();
        Observer observer1 = new OctalObserver(integerObservable);
        Observer observer2 = new BinaryObserver(integerObservable);
        integerObservable.setState(5);  // changed
        integerObservable.setState(5);  // do write, but not changed
        integerObservable.setState(10); // changed

    }
}
