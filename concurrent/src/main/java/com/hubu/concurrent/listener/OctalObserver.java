package com.hubu.concurrent.listener;

public class OctalObserver implements Observer {
    private IntegerObservable observable;

    public OctalObserver(IntegerObservable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void notifyUpdate() {
        System.out.println(this.getClass().getName() + " received the notify: state -> " + Integer.toOctalString(this.observable.getState()));
    }
}

