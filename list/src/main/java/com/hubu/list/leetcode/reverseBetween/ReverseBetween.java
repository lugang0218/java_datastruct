package com.hubu.list.leetcode.reverseBetween;

import com.hubu.list.SingleList;

import java.util.Arrays;

/**
 * 反转链表
 */
 class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
public class ReverseBetween {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode tempHead=head;
        //查找到左边索引的前一个节点
        ListNode leftNode=findNode(head,left-1);
        //查找右边索引的节点
        ListNode rightNode=findNode(head,right);
        ListNode rightNodeNext=rightNode.next;

        head=leftNode.next;
        ListNode node= reverse(head, rightNode);
        rightNode.next=rightNodeNext;
        leftNode.next=head;
        head=leftNode;
        return head;
    }

    public ListNode findNode(ListNode head, int index){
        //查找第index个索引的节点
        ListNode current=head;
        for(int i=0;i<index-1;i++){
            current=current.next;
        }
        return current;
    }
    //从头节点到尾节点进行反转
    public ListNode reverse(ListNode head,ListNode tail){
        tail.next=null;
        ListNode prev = null;
        ListNode current = head;
        ListNode next=null;
        while (current != null) {
            next =current.next;
            current.next=prev;
            prev=current;
            current=next;
        }
        head.next=null;
        head=prev;
        return head;
    }

    public static void main(String[] args) {
        ListNode node8=new ListNode(8,null);
        ListNode node7=new ListNode(7,node8);
        ListNode node6=new ListNode(6,node7);
        ListNode node5=new ListNode(5,node6);
        ListNode node4=new ListNode(4,node5);
        ListNode node3=new ListNode(3,node4);
        ListNode node2=new ListNode(2,node3);
        ListNode node1=new ListNode(1,node2);
        new ReverseBetween().reverseBetween(node1,3,6);
    }
}
