package com.hubu.list.leetcode.deleteDuplicates;

/**
 * 保留链表中不重复的元素，每个元素只能出现一次
 */


class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) {
        this.val = val; this.next = next;
    }
}

public class DeleteDuplicates {
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode before=head;
        ListNode after=head.next;
        while(after!=null){
            if(before.val!=after.val){
                after=after.next;
                before=before.next;
            }
            else{
                before.next=after.next;
                after=after.next;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode node4=new ListNode(2,null);
        ListNode node3=new ListNode(2,node4);
        ListNode node2=new ListNode(1,node3);
        ListNode node1=new ListNode(1,node2);
        new DeleteDuplicates().deleteDuplicates(node1);
    }
}
