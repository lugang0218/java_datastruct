package com.hubu.map;
/**
 * 简单利用数组+链表实现HashMap 链表使用头插入法实现
 */
public class SimpleHashMap<K,V> {
    private static final int DEFAULT_CAPACITY=16;
    private Node<K,V> table[]=null;//哈希表
    private int capacity=0;
    private int size=0;
    //简单模拟实现
    public SimpleHashMap(int capacity){
        this.capacity=16;
    }
    public void put(K key,V value){
        if(table==null){
            table=(Node<K,V>[]) new Node[capacity];
        }
        int index=index(hash(key));
        if(table[index]!=null){
            table[index]=new Node<K,V>(key,value,table[index]);
            return ;
        }
        table[index]=new Node<K,V>(key,value,null);
    }
    public V get(K key){
        //根据key获取到一个索引
        int index=index(hash(key));
        if(table[index]==null){
            return null;
        }
        Node<K,V> current=table[index];
        while(current!=null){
            if(current.key==key){
                return current.value;
            }
            else if(current.key.equals(key)){
                return current.value;
            }
            else{
                current=current.next;
            }
        }
        return null;
    }

    //计算hash值 3先用来测试链表
    public int hash(K key){
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    int index(int hashValue){
        return hashValue%table.length;
    }
    static class Node<K,V>{
        private K key;
        private V value;
        private Node<K,V> next;

        public Node(K key,V value,Node<K,V> next){
            this.key=key;
            this.value=value;
            this.next=next;
        }
        public Node(V value,Node<K,V> next){
            this.value=value;
            this.next=next;
        }
        public Node(V value){
            this(value,null);
        }
    }
}
