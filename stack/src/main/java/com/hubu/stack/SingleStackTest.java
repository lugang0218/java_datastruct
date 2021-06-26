package com.hubu.stack;

public class SingleStackTest {
    public static void main(String[] args) {
//        SingleStack<Integer> stack=new SingleStack<>();
//        stack.push(1);
//        stack.push(2);
//        stack.push(3);
//        stack.push(4);
//        stack.push(5);
//        stack.push(6);
//        stack.push(7);


        ArrayStack<Integer> arrayStack=new ArrayStack<>(10);
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
