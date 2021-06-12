package com.hubu.list.leetcode.isPalindrome;


/**
 * 判断是否回文链表
 */
 class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class IsPalindrome {
    public boolean isPalindrome(ListNode head) {
        ListNode midListNode = findMidListNode(head);
        ListNode head2=midListNode.next;
        midListNode.next=null;
        head2 = reverseList(head2);
        ListNode current1=head;
        ListNode current2=head2;
        while(current1!=null&&current2!=null){
            if(current1.val!=current2.val){
                return false;
            }
            current1=current1.next;
            current2=current2.next;
        }
        return true;
    }


    /**
     * 快慢指针寻找中间节点
     * @return
     */
    public ListNode findMidListNode(ListNode head){
        ListNode fast=head;
        ListNode slow=head;
        while(fast!=null&&fast.next!=null){
            fast=fast.next.next;
            if(fast!=null){
                slow=slow.next;
            }
        }
        return slow;
    }

    public ListNode reverseList(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode prev=null;
        ListNode current=head;
        ListNode next=null;
        while(current!=null){
            next=current.next;
            current.next=prev;
            prev=current;
            current=next;
        }
        head.next=null;
        head=prev;
        return head;
    }

    public static void main(String[] args) {
//        ListNode node8=new ListNode(8,null);
        ListNode node7=new ListNode(7,null);
        ListNode node6=new ListNode(6,node7);
        ListNode node5=new ListNode(5,node6);
        ListNode node4=new ListNode(4,node5);
        ListNode node3=new ListNode(3,node4);
        ListNode node2=new ListNode(2,node3);
        ListNode node1=new ListNode(1,node2);
         new IsPalindrome().isPalindrome(node1);
    }
}
