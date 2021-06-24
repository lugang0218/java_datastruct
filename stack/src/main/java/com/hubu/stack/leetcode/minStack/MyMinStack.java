package com.hubu.stack.leetcode.minStack;
class MinStack {
    private Node head;
    private int minValue;
    public MinStack() {

    }

    public void push(int val) {
        if(head==null){
            minValue=val;
            head=new Node(val);
        }
        else{
            if(minValue>val){
                minValue=val;
            }
            Node newNode=new Node(val);
            newNode.next=head;
            head=newNode;
        }
    }

    public void pop() {
        int value=head.value;
        head=head.next;
        updateMinValue();

    }
    public void updateMinValue(){
        Node current=head;
        if(head!=null){
            minValue=head.value;
        }
        while(current!=null){
            if(minValue>current.value){
                minValue=current.value;
            }
            current=current.next;
        }
    }
    public int top() {
        return head.value;
    }

    public int getMin() {
        return minValue;
    }

    static class Node{
        private int value;
        private Node next;
        public Node(int value, Node next){
            this.value=value;
            this.next=next;
        }
        public Node(int value){
            this(value,null);
        }
    }
}

public class MyMinStack {


    public static void main(String[] args) {
        MinStack minStack=new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        minStack.pop();
        System.out.println(minStack.getMin());

    }
}
