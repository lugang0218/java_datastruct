package com.hubu.list.leetcode.getIntersectionNode;


/**
 * 待优化
 */

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
public class GetIntersectionNode {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA==null||headB==null){
            return null;
        }
        ListNode l1=headA;
        ListNode l2=headB;
        Map<ListNode,ListNode> map=new HashMap<>();
        while(l1!=null){
            map.put(l1,l1);
            l1=l1.next;
        }
        boolean find=false;
        //o(n)复杂度
        while(l2!=null){
            if(map.get(l2)!=null){
                find=true;
                break;
            }
            l2=l2.next;
        }
        if(find){
            return l2;
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
