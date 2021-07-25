package com.hubu.concurrent.singlethread;

public class ConcurrentPassOneGate {
 
    public static void main(String[] args) {
        Gate gate = new Gate();
        Walker bob = new Walker("bob","beijing",gate);
        Walker jack = new Walker("jack","jinan",gate);
        Walker tom = new Walker("tom", "tianjin", gate);
        bob.start();
        jack.start();
        tom.start();
    }
 
} 
