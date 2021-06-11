package com.hubu.list.leetcode;
public class TwoNumberSum {
}
class Node {
    int val;
    Node next;
    Node() {}
    Node(int val) { this.val = val; }
    Node(int val, Node next) { this.val = val; this.next = next; }
}
class Solution {
    public Node addTwoNumbers(Node l1, Node l2){
        boolean isNode1=true;//默认返回node1
        if(l1==null||l2==null){
            return null;
        }
         Node tempNode1=l1;
         Node tempNode2=l2;
        int cmp=0;
        Node before=null;
        while(tempNode1!=null&&tempNode2!=null){
            before=tempNode1;
            int oldValue=tempNode1.val+tempNode2.val+cmp;
            tempNode1.val=(tempNode1.val+tempNode2.val+cmp)%10;
            cmp=oldValue/10;
            tempNode1=tempNode1.next;
            tempNode2=tempNode2.next;
        }
        if(tempNode1==null&&tempNode2==null){
            if(cmp!=0){
                Node node=new Node(1);
                before.next= node;
            }
        }
        //可能还有进位,此时tempNode2一定为空
        if(tempNode1!=null){
            Node temp=null;
            while(tempNode1!=null){
                temp=tempNode1;
                int oldValue=tempNode1.val+cmp;
                tempNode1.val=(tempNode1.val+cmp)%10;
                cmp=oldValue/10;
                tempNode1=tempNode1.next;
            }

            if(cmp!=0){
                Node node=new Node(1);
                temp.next= node;
            }
        }

        //如果tempNode2不为空 说明tempNode2比较长，将所有数据拷贝到tempNode2身上，否则什么也不做
        if(tempNode2!=null){
            isNode1=false;
            tempNode2=l2;
            tempNode1=l1;
            while(tempNode1!=null){

                tempNode2.val=tempNode1.val;
                tempNode1=tempNode1.next;
                tempNode2=tempNode2.next;
            }
            Node temp=null;
            //这一步用于判断进位 如果还有进位 需要加上
            while(tempNode2!=null){
                //保存上一个节点，保证不为空
                temp=tempNode2;
                int oldValue=tempNode2.val+cmp;
                tempNode2.val=(tempNode2.val+cmp)%10;
                cmp=oldValue/10;
                tempNode2=tempNode2.next;
            }
            if(cmp!=0){
                Node node=new Node(1);
                temp.next= node;
            }
        }
        if(isNode1){
            return l1;
        }
        return l2;
    }
    public static void main(String[] args) {
        Node nodea1=new Node(9);
        Node nodea2=new Node(9);
        Node nodea3=new Node(9);
        Node nodea4=new Node(9);
        Node nodea5=new Node(9);
        Node nodea6=new Node(9);
        Node nodea7=new Node(9);
        nodea1.next=nodea2;
        nodea2.next=nodea3;
        nodea3.next=nodea4;
        nodea4.next=nodea5;
        nodea5.next=nodea6;
        nodea6.next=nodea7;
        Node nodeb1=new Node(9);
        Node nodeb2=new Node(9);
        Node nodeb3=new Node(9);
        Node nodeb4=new Node(9);
        nodeb1.next=nodeb2;
        nodeb2.next=nodeb3;
        nodeb3.next=nodeb4;
        Solution solution=new Solution();
        solution.addTwoNumbers(nodea1,nodeb1);
    }
}
