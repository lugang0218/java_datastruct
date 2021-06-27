package com.hubu.stack;

public class ArrayStackTest {
    public static void main(String[] args) {
        ArrayStack<Integer> arrayStack=new ArrayStack<>(2);
        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);
        arrayStack.push(4);
        System.out.println(arrayStack.pop());
        System.out.println(arrayStack.pop());
        System.out.println(arrayStack.pop());
        System.out.println(arrayStack.pop());
    }
}
