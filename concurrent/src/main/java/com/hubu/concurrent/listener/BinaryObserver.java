package com.hubu.concurrent.listener;

public class BinaryObserver implements Observer{


    private IntegerObservable integerObservable;


    /**
     * 绑定关系
     * @param integerObservable
     */
    public BinaryObserver(IntegerObservable integerObservable){
        integerObservable.addObserver(this);
        this.integerObservable=integerObservable;
    }
    @Override
    public void notifyUpdate() {
        System.out.println(this.getClass().getName() + " received the notify: state -> " +
                Integer.toBinaryString(this.integerObservable.getState()));
    }
}
