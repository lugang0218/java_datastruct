package com.hubu.stack.leetcode.isValid;

import java.util.Stack;

public class IsValid {
    public boolean isValid(String s) {
        if(s==null||s==""||s.length()==1){
            return false;
        }
        Stack<Character> stack=new Stack<>();
        char[] chars = s.toCharArray();
        for(char item:chars){
            if(item=='['||item=='{'||item=='('){
                stack.push(item);
            }
            else if(item=='}'){
                if(stack.isEmpty()){
                    return false;
                }
                Character current = stack.pop();
                if(current!='{'){
                    return false;
                }

            }else if(item==']'){
                if(stack.isEmpty()){
                    return false;
                }
                Character current = stack.pop();
                if(current!='['){
                    return false;
                }
            }else if(item==')'){
                if(stack.isEmpty()){
                    return false;
                }
                Character current = stack.pop();
                if(current!='('){
                    return false;
                }
            }else{
                return false;
            }
        }
        if(stack.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    public static void main(String[] args) {
        boolean valid = new IsValid().isValid("}{");
        System.out.println("valid="+valid);
    }
}
