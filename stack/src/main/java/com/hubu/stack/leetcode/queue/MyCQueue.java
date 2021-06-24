package com.hubu.stack.leetcode.queue;
import java.util.Stack;
class CQueue {
    private Stack<Integer> stack1=new Stack<>();
    private Stack<Integer> stack2=new Stack<>();
    public CQueue() {

    }

    public void appendTail(int value) {
         stack1.push(value);

    }

    public int deleteHead() {
        while(!stack1.isEmpty()){
            int result = stack1.pop();
            stack2.push(result);
        }
        return stack2.isEmpty()?-1:stack2.pop();
    }
}

public class MyCQueue {
}
