package com.hubu.list.leetcode.mergeTwoLists;


class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
public class MergeTwoLists {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null&&l2==null){
            return null;
        }
        ListNode node1=l1;
        ListNode node2=l2;
        ListNode head=null;
        ListNode node=null;
        while(node1!=null&&node2!=null){
            if(node1.val<=node2.val){
                if(head==null){
                    head=new ListNode(node1.val);
                    node=head;
                }
                else{
                    ListNode newNode=new ListNode(node1.val);
                    node.next=newNode;
                    node=newNode;
                }
                node1=node1.next;

            }
            else{
                if(head==null){
                    head=new ListNode(node2.val);
                    node=head;
                }
                else{
                    ListNode newNode=new ListNode(node2.val);
                    node.next=newNode;
                    node=newNode;
                }
                node2=node2.next;
            }

        }
        while(node1!=null){

            if(head==null){
                head=new ListNode(node1.val);
                node=head;
            }
            else{
                ListNode newNode=new ListNode(node1.val);
                node.next=newNode;
                node=newNode;
            }
            node1=node1.next;
        }
        while(node2!=null){
            if(head==null){
                head=new ListNode(node2.val);
                node=head;
            }
            else{
                ListNode newNode=new ListNode(node2.val);
                node.next=newNode;
                node=newNode;
            }
            node2=node2.next;
        }
        return head;
    }



    public static void main(String[] args) {
        ListNode node3=new ListNode(4,null);
        ListNode node2=new ListNode(2,node3);
        ListNode node1=new ListNode(1,node2);


        ListNode node6=new ListNode(4,null);
        ListNode node5=new ListNode(3,node6);
        ListNode node4=new ListNode(1,node5);
        new MergeTwoLists().mergeTwoLists(node1,node4);

    }
}
