package com.hubu.tree.bplustree;

import java.util.Comparator;
import java.util.Map;
import java.util.*;
public class BPlusTree <K,V>{
    private int order;
    private Comparator<K> comparator;
    private Node<K,V> root=null;
    private Node<K,V> head=null;
    private int size;

    public BPlusTree(int order,Comparator<K> comparator){
        this.order=order;
        this.comparator=comparator;
    }
    public BPlusTree(int order){
        this(order,null);
    }
    public BPlusTree(Comparator<K> comparator){
        this(3,comparator);
    }
    public BPlusTree() {
        this(3, null);
    }
    public V put(K key, V value){
        Node<K,V> node=root;
        if(node==null){
            root=new Node<>(true,true);
            node=root;
        }
        return put(node,key,value);
    }
    private V put(Node<K,V> node, K key, V value){
        if(node.isLeaf){
            if(isNotFull(node)){
                return add0(node,key,value);
            }
            //先一个之后到满 后分裂
            V result=add0(node,key,value);
            //左边元素个数等于总的个数除以2向下取整
            int leftSize=order/2;

            //左边元素个数等于总的个数除以2向上取整
            int rightSize=(int)(Math.ceil((double) order/2));
            Node<K,V> leftNode=new Node(false,true);
            Node<K,V> rightNode=new Node<>(false,true);
            for(int i=0;i<leftSize;i++){
                leftNode.entryList.add(node.entryList.get(i));
            }
            for(int j=0;j<rightSize;j++){
                rightNode.entryList.add(node.entryList.get(leftSize+j));
            }
            Node<K,V> newParent=node.parent;
            int index=0;



            leftNode.next=rightNode;
            rightNode.prev=leftNode;


            Node<K,V> temp=node;
            if(node.prev==null){
                head=leftNode;
            }
            else{
                leftNode.prev=node.prev;
                node.prev.next=leftNode;
                rightNode.next=node.next;
                if(node.next!=null) {
                    node.next.prev = rightNode;
                }
            }
            temp.prev=null;
            temp.next=null;
            if(newParent==null){
                newParent = new Node<>(true, false);
                root = newParent;
            }
            else{
                index=node.parent.children.indexOf(node);
                node.parent.children.remove(node);
            }
            leftNode.parent = newParent;
            rightNode.parent = newParent;
            newParent.children.add(index,leftNode);
            newParent.children.add(index+1,rightNode);
            newParent.entryList.add(rightNode.entryList.get(0));
            afterPut(node.parent);
            node.children=null;
            node.entryList=null;
            node.parent=null;
            return result;
        }

        int compareResult=0;
        compareResult=compare(key,node.entryList.get(0).getKey());
        if(compareResult<0){
            return put(node.children.get(0),key,value);
        }
        compareResult=compare(key,node.entryList.get(node.entryList.size()-1).getKey());
        if(compareResult>=0){
            return put(node.children.get(node.children.size()-1),key,value);
        }
        int low=0;
        int mid=0;
        int high=node.entryList.size()-1;
        while(low<=high){
            mid=(low+high)/2;
            compareResult=compare(key,node.entryList.get(mid).getKey());
            if(compareResult>0){
                low=mid+1;
            }
            else if(compareResult==0){
                return put(node.children.get(mid+1),key,value);
            }
            else{
                high=mid-1;
            }
        }
        return put(node.children.get(low),key,value);
    }
    private void afterPut(Node<K, V> node) {
        if(node!=null&&node.children.size()>order){
            int leftSize=(int)Math.ceil((double) node.children.size()/2);
            int rightSize=node.children.size()/2;
            Node<K,V> leftNode=new Node<>(false,false);
            Node<K,V> rightNode=new Node<>(false,false);
            for(int i=0;i<leftSize;i++){
                leftNode.children.add(node.children.get(i));
                node.children.get(i).parent=leftNode;
            }
            for(int i=0;i<rightSize;i++){
                rightNode.children.add(node.children.get(leftSize+i));
                node.children.get(leftSize+i).parent=rightNode;
            }
            for(int i=0;i<leftSize-1;i++){
                leftNode.entryList.add(node.entryList.get(i));
            }
            for(int i=0;i<rightSize-1;i++){
                rightNode.entryList.add(node.entryList.get(leftSize+i));
            }

            Node<K,V> newParent=node.parent;
            int index=0;
            if(newParent==null){
                newParent=new Node(true,false);
                root=newParent;
            }
            else {
                index=newParent.children.indexOf(node);
                newParent.children.remove(node);
            }
            leftNode.parent=newParent;
            rightNode.parent=newParent;
            newParent.children.add(index,leftNode);
            newParent.children.add(index+1,rightNode);
            newParent.entryList.add(node.entryList.get(leftSize-1));
            afterPut(node.parent);
            node.entryList=null;
            node.parent=null;
            node.children=null;
        }
    }
    public V remove(K key){
        if(root==null) return null;
        checkKeyNotNull(key);
        return remove(root,key);
    }
    private boolean canRemoveBySelf(Node<K,V> self){
        if(self==null) return false;
        //最小需要的节点数是order/2向上取整-1
        int minSize=((int)Math.ceil((double)order/2))-1;
        return self.entryList.size()>minSize;
    }
    private boolean canBorrowFromNode(Node<K,V> node){
        if(node==null) return false;
        int minSize=((int)Math.ceil((double)order/2))-1;
        return node.entryList.size()>minSize;
    }
    private boolean canMerge(Node<K,V> node1,Node<K,V> node2){
        if(node1==null||node2==null){
            return false;
        }
        return node1.entryList.size()+node2.entryList.size()<=order-1;
    }
    private V remove(Node<K,V> node,K key){
        if(node==null) return null;
        if(node.isLeaf){
            if(node.isRoot||canRemoveBySelf(node)){
                return remove0(node,key);
            }
            if(node.prev!=null&&node.prev.parent==node.parent&&canBorrowFromNode(node.prev)){
                int index=node.parent.children.indexOf(node.prev);
                Map.Entry<K, V> prevEntry = node.prev.entryList.remove(node.prev.entryList.size() - 1);
                V result=remove0(node,key);
                node.entryList.add(0,prevEntry);
                node.parent.entryList.set(index,node.prev.entryList.get(node.prev.entryList.size()-1));
                return result;
            }
            if(node.next!=null&&node.next.parent==node.parent&&canBorrowFromNode(node.next)){
                int index=node.parent.children.indexOf(node);
                Map.Entry<K, V> nextEntry= node.next.entryList.remove(0);
                V result=remove0(node,key);
                node.entryList.add(nextEntry);
                node.parent.entryList.set(index,node.next.entryList.get(0));
                return result;
            }
            if(node.prev!=null&&node.prev.parent==node.parent&&canMerge(node.prev,node)){
                for(int i=0;i<node.entryList.size();i++){
                    node.prev.entryList.add(node.entryList.get(i));
                }
                node.entryList=node.prev.entryList;
                node.prev.entryList=null;
                node.prev.parent=null;
                node.parent.children.remove(node.prev);
                if(node.prev.prev!=null){
                    Node<K,V> temp=node.prev;
                    node.prev.prev.next=node;
                    node.prev=node.prev.prev;
                    temp.prev=null;
                    temp.next=null;
                }else{
                    head=node;
                    node.prev.next=null;
                    node.prev=null;
                }
                V result=remove0(node,key);
                int index=node.parent.children.indexOf(node);
                node.parent.entryList.remove(index);
                afterRemove(node.parent);
            }
            if(node.next!=null&&node.next.parent==node.parent&&canMerge(node.next,node)){
                for(int i=0;i<node.next.entryList.size();i++){
                    node.entryList.add(node.next.entryList.get(i));
                }
                node.next.entryList=null;
                node.next.parent=null;
                node.parent.children.remove(node.next);
                if (node.next.next != null) {
                    Node<K, V> temp = node.next;
                    node.next.next.prev=node;
                    node.next=node.next.next;
                    temp.prev = null;
                    temp.next = null;
                } else {
                    node.next.prev = null;
                    node.next = null;
                }
                V result=remove0(node,key);
                int index=node.parent.children.indexOf(node);
                node.parent.entryList.remove(index);
                afterRemove(node.parent);
            }
        }
        else{
            int compareResult=0;
            compareResult=compare(key,node.entryList.get(0).getKey());
            if(compareResult<0){
                return remove(node.children.get(0),key);
            }
            compareResult=compare(key,node.entryList.get(node.entryList.size()-1).getKey());
            if(compareResult>=0){
                return remove(node.children.get(node.children.size()-1),key);
            }
            int low=0;
            int high=node.entryList.size()-1;
            int mid=0;
            while(low<=high){
                mid=(low+high)/2;
                compareResult=compare(key,node.entryList.get(mid).getKey());
                if(compareResult>0){
                    low=mid+1;
                }
                else if(compareResult==0){
                    return remove(node.children.get(mid+1),key);
                }
                else{
                    high=mid-1;
                }
            }
            return remove(node.children.get(low),key);
        }
        return null;
    }
    private int minChildSize(int order){
        return (int)Math.ceil((double) order/2);
    }
    private void afterRemove(Node<K,V> node){
        //递归向上处理父节点的条件 孩子节点的数量小于阶/2向上取整
        if(node==null){
            return ;
        }
        if(node.children.size()<minChildSize(order)||node.children.size()<2){
            /**
             * 如果是根节点
             */
            if(node.isRoot){
                if(node.children.size()>=2){
                    return ;
                }
                Node<K,V> newRoot=node.children.get(0);
                newRoot.parent=null;
                newRoot.isRoot=true;
                node.children=null;
                node.parent=null;
                node.entryList=null;
                root=newRoot;
            }
            else{
                //计算node节点的左后节点
                int currentIndex= node.parent.children.indexOf(node);
                int prevIndex=currentIndex-1;
                int nextIndex=currentIndex+1;
                Node<K,V> prev=null;
                Node<K,V> next=null;
                if(prevIndex>=0){
                    prev=node.parent.children.get(prevIndex);
                }
                if(nextIndex<node.parent.children.size()){
                    next=node.parent.children.get(nextIndex);
                }
                if(prev!=null&&prev.parent==node.parent&&canRemoveBySelf(node)){
                    int index = prev.children.size() - 1;
                    Node<K, V> borrow = prev.children.remove(index);
                    borrow.parent = node;
                    node.children.add(0, borrow);
                    int parentIndex =node.parent.children.indexOf(prev);
                    node.entryList.add(0, node.parent.entryList.get(parentIndex));
                    node.parent.entryList.set(parentIndex, prev.entryList.remove(index - 1));
                    return;
                }
                else if (next != null&&next.parent==node.parent&&canRemoveBySelf(node)) {
                    //后叶子节点首位添加到末尾
                    Node<K, V> borrow = next.children.get(0);
                    next.children.remove(0);
                    borrow.parent = node;
                    node.children.add(borrow);
                    int preIndex = node.parent.children.indexOf(this);
                    node.entryList.add(node.parent.entryList.get(preIndex));
                    node.parent.entryList.set(preIndex, next.entryList.remove(0));
                    return;
                }
                else if (prev != null
                        &&canMerge(node,prev)) {
                    for (int i = 0; i < node.children.size(); i++) {
                        prev.children.add(node.children.get(i));
                    }
                    for (int i = 0; i < prev.children.size(); i++) {
                        prev.children.get(i).parent = node;
                    }
                    int indexPre = node.parent.children.indexOf(prev);
                    prev.entryList.add(node.parent.entryList.get(indexPre));
                    for (int i = 0; i < node.entryList.size(); i++) {
                        prev.entryList.add(node.entryList.get(i));
                    }
                    node.children = prev.children;
                    node.entryList = prev.entryList;

                    //更新父节点的关键字列表
                    node.parent.children.remove(prev);
                    prev.parent = null;
                    prev.children = null;
                    prev.entryList= null;
                    node.parent.entryList.remove(node.parent.children.indexOf(node));
                    afterRemove(node.parent);
                    return;
                }

                // 同后面节点合并
                else if (next != null&&next.parent==node.parent) {
                    for (int i = 0; i < next.children.size(); i++) {
                        Node<K, V> child = next.children.get(i);
                        node.children.add(child);
                        child.parent = node;
                    }
                    int index = node.parent.children.indexOf(node);
                    node.entryList.add(node.parent.entryList.get(index));
                    for (int i = 0; i < next.entryList.size(); i++) {
                        node.entryList.add(next.entryList.get(i));
                    }
                    node.parent.children.remove(next);
                    next.parent = null;
                    next.children = null;
                    next.entryList = null;
                    node.parent.entryList.remove(index);
                    afterRemove(node.parent);
                    return;
                }
                else if(prev==null&&next==null){
                    System.out.println("prev=null and next=null");
                }
            }
        }
    }

    private void checkKeyNotNull(K key){
        if(key==null) throw new RuntimeException("key must not be null");
    }
    private boolean isNotFull(Node<K,V> node){
        return node.entryList.size()<order-1;
    }
    private V add0(Node<K,V> node,K key,V value){
        int low=0;
        int high=node.entryList.size()-1;
        int compareResult=0;
        while(low<=high){
            int mid=(low+high)/2;
            compareResult=compare(key,node.entryList.get(mid).getKey());
            if(compareResult>0){
                low=mid+1;
            }
            else if(compareResult==0){
                V oldValue=node.entryList.get(mid).getValue();
                node.entryList.get(mid).setValue(value);
                return oldValue;
            }
            else{
                high=mid-1;
            }
        }
        node.entryList.add(low,new AbstractMap.SimpleEntry<K,V>(key,value));
        return null;
    }


    public V get(K key){
        if(root==null) return null;
        return search(root,key);
    }
    public V search(Node<K,V> node,K key){
        if(node==null) return null;
        if(node.isLeaf){
            int low=0;
            int high=node.entryList.size()-1;
            int compareResult=0;
            while(low<=high){
                int mid=(low+high)/2;
                compareResult=compare(key,node.entryList.get(mid).getKey());
                if(compareResult>0){
                    low=mid+1;
                }
                else if(compareResult==0){
                    return node.entryList.get(mid).getValue();
                }
                else{
                    high=mid-1;
                }
            }
            return null;
        }
        else{
            int compareResult=0;
            compareResult=compare(key,node.entryList.get(0).getKey());
            if(compareResult<0){
                return search(node.children.get(0),key);
            }
            compareResult=compare(key,node.entryList.get(node.entryList.size()-1).getKey());
            if(compareResult>=0){
                return search(node.children.get(node.children.size()-1),key);
            }
            int low=0;
            int high=node.entryList.size()-1;
            int mid=0;
            while(low<=high){
                mid=(low+high)/2;
                compareResult=compare(key,node.entryList.get(mid).getKey());
                if(compareResult>0){
                    low=mid+1;
                }
                else if(compareResult==0){
                    return search(node.children.get(mid+1),key);
                }
                else{
                    high=mid-1;
                }
            }
            return search(node.children.get(low),key);
        }
    }
    private V remove0(Node<K,V> node,K key){
        if(node==null) return null;
        int compareResult=0;
        int low=0;
        int mid=0;
        int high=node.entryList.size()-1;
        while(low<=high){
            mid=(low+high)/2;
            compareResult=compare(key,node.entryList.get(mid).getKey());
            if(compareResult>0){
                low=mid+1;
            }
            else if(compareResult==0){
                return node.entryList.remove(mid).getValue();
            }
            else{
                high=mid-1;
            }
        }
        return null;
    }
    private int compare(K key1,K key2){
        return comparator!=null?comparator.compare(key1,key2):((Comparable)key1).compareTo(key2);
    }
    static class Node<K,V> {
        boolean isLeaf;
        boolean isRoot;
        List<Map.Entry<K,V>> entryList;
        List<Node<K,V>> children;
        Node<K,V> parent;
        Node<K,V> prev;
        Node<K,V> next;
        public Node(boolean isRoot,boolean isLeaf){
            this.isRoot=isRoot;
            this.isLeaf=isLeaf;
            entryList=new ArrayList<>();
            if(!isLeaf){
                this.children=new ArrayList<>();
            }
        }
    }
}
