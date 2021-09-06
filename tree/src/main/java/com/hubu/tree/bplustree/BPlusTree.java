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



    public V add(K key,V value){
        Node<K,V> node=root;
        if(node==null){
            root=new Node<>(true,true);
            node=root;
        }
        return add(node,key,value);
    }
    private V add(Node<K,V> node,K key,V value){
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
            node.children=null;
            node.entryList=null;

            if(node.parent==null) {
                Node<K, V> newParent = new Node<>(true, false);
                leftNode.parent = newParent;
                rightNode.parent = newParent;
                newParent.children.add(leftNode);
                newParent.children.add(rightNode);
                newParent.entryList.add(rightNode.entryList.get(0));
                root = newParent;
            }
            else{
                int index=node.parent.children.indexOf(node);
                node.parent.children.remove(node);
                leftNode.parent=node.parent;
                rightNode.parent=node.parent;
                node.parent.children.add(index,leftNode);
                node.parent.children.add(index+1,rightNode);
                node.parent.entryList.add(rightNode.entryList.get(0));
                afterPut(node.parent);
                node.parent=null;
            }
            return result;
        }

        int compareResult=0;
        compareResult=compare(key,node.entryList.get(0).getKey());
        if(compareResult<0){
            return add(node.children.get(0),key,value);
        }
        compareResult=compare(key,node.entryList.get(node.entryList.size()-1).getKey());
        if(compareResult>=0){
            return add(node.children.get(node.children.size()-1),key,value);
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
                return add(node.children.get(mid+1),key,value);
            }
            else{
                high=mid-1;
            }
        }
        return add(node.children.get(low),key,value);
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
    private int compare(K key1,K key2){
        return comparator!=null?comparator.compare(key1,key2):((Comparable)key1).compareTo(key2);
    }
    static class Node<K,V> {
        boolean isLeaf;
        boolean isRoot;
        List<Map.Entry<K,V>> entryList;
        List<Node<K,V>> children;
        Node<K,V> parent;
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
