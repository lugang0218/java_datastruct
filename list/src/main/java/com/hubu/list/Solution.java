package com.hubu.list;
 class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
public class Solution {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public static  ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode tempHead = new ListNode(0, head);
        ListNode pre = tempHead;
        ListNode cur = head;
        ListNode next = head.next;
        while (next != null) {
            if (cur.val != next.val) {
                cur = cur.next;
                next = next.next;
                pre = pre.next;
            } else {
                while (true) {
                    if(next==null){
                        break;
                    }
                    else if ( next.val != cur.val) {
                        break;
                    }
                    else {
                        next = next.next;
                    }

                }
                pre.next = next;
                cur = next;
                if (next != null) {
                    next = next.next;
                }
            }
        }
        head = tempHead.next;
        return head;
    }
// 1 4 3 2 5 2
    public static ListNode partition(ListNode head, int x) {
        ListNode tempHead=null;
        ListNode tempTail=null;
        ListNode r=null;
        ListNode cur=head;
        ListNode next=null;
        while(cur!=null){
            next=cur.next;
            if(cur.val<x){
                if(tempTail==null){
                    tempHead=tempTail=cur;
                }
                else{
                    cur.next=tempTail.next;
                    tempTail.next=cur;
                    cur.next=r;
                }

            }
            else if(cur.val>=x){
                if(r==null){
                    r=cur;
                }
                else{
                    r.next=cur;
                    r=r.next;
                }
            }
            cur=next;
        }
        return tempHead;
    }

    public static void main(String[] args) {
        ListNode node1=new ListNode(1);
        ListNode node2=new ListNode(4);
        ListNode node3=new ListNode(3);
        ListNode node4=new ListNode(2);
        ListNode node5=new ListNode(5);
        ListNode node6=new ListNode(2);
        node1.next=node2;
        node2.next=node3;
        node3.next=node4;
        node4.next=node5;
        node5.next=node6;
        partition(node1,3);


    }
}