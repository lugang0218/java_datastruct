package com.hubu.concurrent.singlethread;

public class Walker extends Thread{
 
    private String name;
    private String destination;
    private Gate gate;
 
    public Walker(String name, String destination,Gate gate) {
        this.name = name;
        this.destination = destination;
        this.gate = gate;
    }
 
    @Override
    public void run() {
        System.out.println(name + " start walking...");
        while (true) {
            gate.pass(name, destination);
        }
    }
}
