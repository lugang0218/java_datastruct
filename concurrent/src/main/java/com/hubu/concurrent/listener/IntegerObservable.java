package com.hubu.concurrent.listener;

import java.util.ArrayList;
import java.util.List;

public class IntegerObservable implements Observable{


    /**
     * 待改变的值
     */
    private int state;


    private List<Observer> observerList=new ArrayList<>();
    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    public int getState() {
        return state;
    }


    public void setState(int state) {
        if(this.state==state){
            return ;
        }
        else{
            //数据改变之后通知所有观察者
            this.state=state;
            notifyAllObservers();
        }
    }

    @Override
    public void deleteObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for(Observer item:observerList){
            item.notifyUpdate();
        }
    }

}
