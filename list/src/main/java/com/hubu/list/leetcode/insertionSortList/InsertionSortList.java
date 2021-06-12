package com.hubu.list.leetcode.insertionSortList;


 class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
public class InsertionSortList {
    public ListNode insertionSortList(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode newHead=null;
        ListNode current=head;
        ListNode next=null;
        while(current!=null){
            next=current.next;
            newHead = doInsertNode(newHead, current);
            current=current.next;
        }
        return newHead;
    }
    public ListNode doInsertNode(ListNode newHead,ListNode node){


        ListNode newNode=new ListNode(node.val);
        if(newHead==null){
            newHead=newNode;
        }
        else if(newNode.val<=newHead.val){
            newNode.next=newHead;
            newHead=newNode;
        }
        else{
            ListNode current=newHead;
            while(current!=null){
                ListNode next=current.next;
                if(node.val>=current.val){
                    if(next==null){
                        current.next=newNode;
                        break;
                    }
                    else{
                        if(newNode.val<=next.val){
                            newNode.next=current.next;
                            current.next=newNode;
                            break;
                        }
                        current=next;
                    }
                }
            }
        }
        return newHead;
    }

    public static void main(String[] args) {
        ListNode node4=new ListNode(3,null);
        ListNode node3=new ListNode(1,node4);
        ListNode node2=new ListNode(2,node3);
        ListNode node1=new ListNode(4,node2);
        new InsertionSortList().insertionSortList(node1);
    }
}
