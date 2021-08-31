package com.hubu.tree.btree;
import java.util.*;
public class BPTree <K extends Comparable<K>,V>{
    private Comparator<K> comparator;
    private int order;
    private Node<K,V> root;
    public BPTree(Comparator<K> comparator,int order){
        this.comparator=comparator;
        this.order=order;
        root=new Node<>(true,true);
    }
    public void  put(K key,V value){
        Node<K,V> node=root;
        node.put(key,value,this);
    }
    public V get(K key){
        return root.get(key);
    }

    public int getOrder() {
        return order;
    }

    public void setRoot(Node<K, V> root) {
        this.root = root;
    }
}
