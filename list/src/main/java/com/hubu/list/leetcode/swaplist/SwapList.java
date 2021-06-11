package com.hubu.list.leetcode.swaplist;
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
public class SwapList {
    public ListNode swapPairs(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode prev=head.next;
        ListNode next=head;
        while(prev!=null){
            int temp=prev.val;
            prev.val=next.val;
            next.val=temp;
            if(prev.next==null){
                break;
            }
            prev=prev.next.next;
            next=next.next.next;
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode node4=new ListNode(4,null);
        ListNode node3=new ListNode(3,node4);
        ListNode node2=new ListNode(2,node3);
        ListNode node1=new ListNode(1,node2);
       new SwapList().swapPairs(node1);

    }
}
