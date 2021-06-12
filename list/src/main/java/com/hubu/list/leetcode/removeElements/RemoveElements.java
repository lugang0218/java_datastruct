package com.hubu.list.leetcode.removeElements;


 class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
public class RemoveElements {
    public ListNode removeElements(ListNode head, int val) {
        if(head==null){
            return head;
        }
        else if(head.next==null){
            if(head.val==val){
                head=null;
                return head;
            }
        }
        else{
            ListNode before=head;
            ListNode after=head.next;
            while(after!=null){
                if(after.val==val){
                    after=after.next;
                    before.next=after;
                }
                else{
                    before=after;
                    after=after.next;
                }
            }
            if(head.val==val){
                head=head.next;
            }
        }
        return head;
    }
}
