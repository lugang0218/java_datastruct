package com.hubu.concurrent.singlethread;

public class Gate {
 
    private String name = "nobody";
    private String destination = "nowhere";
    private int counter;
 
    public synchronized void pass(String name, String destination) {
        counter++;
        this.name = name;
        this.destination = destination;
        verify();
    }
 
    private void verify() {
        if (name.charAt(0) != destination.charAt(0)) {
            System.out.println("No." + counter + "==============broken : name -> " + name + " , destination -> " + destination);
        }
    }
}
