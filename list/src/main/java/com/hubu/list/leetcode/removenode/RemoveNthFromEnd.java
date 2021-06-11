package com.hubu.list.leetcode.removenode;
/**
 * 删除单项链表中的倒数第k个节点 待优化成为一趟遍历 这次遍历了两次
 *
 */
class Node2 {
    int val;
    Node2 next;
    Node2() {}
    Node2(int val) { this.val = val; }
    Node2(int val, Node2 next) { this.val = val; this.next = next; }
}
public class RemoveNthFromEnd {
    public Node2 removeNthFromEnd(Node2 head, int n) {
        if(head==null||head.next==null){
            return null;
        }
        int nodeSum=count(head);
        int index=nodeSum-n+1;
        return remove(head,index);
    }
    public int count(Node2 head){
        int result=0;
        Node2 current=head;
        while(current!=null){
            result++;
            current=current.next;
        }
        return result;
    }

    /**
     * 删除正数第n个节点
     * @param head
     * @param n
     * @return
     */
    public Node2 remove(Node2 head, int n){
        //下标从0开始，传入第i个位置，就找到第i个位置的节点
        //查找到第n-1个位置的节点
        Node2 node = findNode(head, n-1);

        //删除的是第一个节点
        if(n==1){
            head=node.next;
        }
        else{
            node.next=node.next.next;
        }
        return head;
    }

    //查找第i个位置的节点
    public Node2 findNode(Node2 head, int index){
        //查找第index个索引的节点
        Node2 current=head;
        for(int i=0;i<index-1;i++){
            current=current.next;
        }
        return current;
    }
    public static void main(String[] args) {
        Node2 node4=new Node2(2,null);
        Node2 node3=new Node2(1,node4);
        RemoveNthFromEnd removeNthFromEnd=new RemoveNthFromEnd();
        removeNthFromEnd.removeNthFromEnd(node3,2);
    }
}
