package com.hubu.list.leetcode.hasCycle;

import java.util.HashMap;
import java.util.Map;

class ListNode {
     int val;
     ListNode next;
     ListNode(int x) {
         val = x;
         next = null;
     }
 }
public class HasCycle {
    public ListNode hasCycle(ListNode head) {
        Map<ListNode,Boolean> map=new HashMap<>();
        if(head==null){
            return head;
        }
        ListNode findNode=null;
        ListNode current=head;
        while(current!=null){
            if(map.get(current)!=null){
                findNode=current;
                break;
            }
            else{
                map.put(current,true);
            }
            current=current.next;
        }
        return findNode;
    }

    public static void main(String[] args) {
        ListNode node4=new ListNode(4);
        ListNode node3=new ListNode(3);
        ListNode node2=new ListNode(2);
        ListNode node1=new ListNode(1);
        node1.next=node2;
        node2.next=node3;
        node3.next=node4;
        node4.next=node2;
    }
}
