package com.hubu.list.leetcode.rotateRight;

/**
 * 旋转链表
 */
   class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
public class RotateRight {
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null||head.next==null){
            return head;
        }
        int count=0;
        ListNode current=head;
        ListNode temp=null;
        while(current!=null){
            temp=current;
            count++;
            current=current.next;
        }
        //temp为末尾节点
        //不需要旋转 或者旋转之后还是一样的

        if(k%count==0){
            return head;
        }
        //组环
        temp.next=head;
        for(int i=1;i<=count-k;i++){
            temp=temp.next;
        }
        ListNode newHead=temp.next;
        //消除环
        temp.next=null;
        return newHead;
    }
    public static void main(String[] args) {
       ListNode node4=new ListNode(4,null);
       ListNode node3=new ListNode(3,node4);
       ListNode node2=new ListNode(2,node3);
       ListNode node1=new ListNode(1,node2);
       RotateRight rotateRight=new RotateRight();
       rotateRight.rotateRight(node1,2);
    }
}
