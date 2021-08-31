package com.hubu.list;

import java.util.Comparator;

public class SkipList <K,V>{
    private static final int Max_LEVEL=32;
    private static final double P=0.25;
    /**
     * 有效长度
     */


    /**
     * 返回节点的高度
     * @return
     */
    public int level(){
        return level;
    }
    private int level;
    private int size;
    private Comparator<K> comparator;
    private Node<K,V> head;
    public SkipList(Comparator<K> comparator){
        this.comparator=comparator;
        head=new Node<K,V>(null,null,Max_LEVEL);
    }
    public SkipList() {
        this(null);
    }
    private V put(K key,V value,boolean flag){
        Node<K,V> node=head;
        //用于保存插入所有的前驱节点
        Node<K,V> []prevs=new Node[level];
        for(int i=level-1;i>=0;i--){
            int compare=-1;
            while(node.nexts[i]!=null&&(compare=compare(key,node.nexts[i].key))>0){
                node=node.nexts[i];
            }
            //如果添加遇到相等的情况
            if(compare==0){
                if(flag){
                    V oldValue=node.nexts[i].value;
                    node.nexts[i].value=value;
                    size++;
                    return oldValue;
                }
                else{
                    size++;
                    return null;

                }
            }
            //如果不相等，说明马上就要往下跳一步,将node保存下来
            prevs[i]=node;
        }
        //概率获取插入的新节点的层数
        int newLevel=randomLevel();
        Node<K,V> newNode=new Node<K,V>(key,value,newLevel);
        for(int i=0;i<newLevel;i++){
            if(i>=level){
                head.nexts[i]=newNode;
            }
            else{
                newNode.nexts[i]=prevs[i].nexts[i];
                prevs[i].nexts[i]=newNode;
            }

        }
        size++;
        level=Math.max(level,newLevel);
        return null;
    }

    public V put(K key, V value) {
        return put(key,value,true);
    }

    public V remove(K key) {

        Node<K, V> node = head;
        Node<K, V>[] prevs = new Node[level];
        boolean exist = false;
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nexts[i] != null
                    && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            prevs[i] = node;
            if (cmp == 0) exist = true;
        }
        if (!exist) return null;
        Node<K, V> removedNode = node.nexts[0];

        size--;
        for (int i = 0; i < removedNode.nexts.length; i++) {
            prevs[i].nexts[i] = removedNode.nexts[i];
        }
        int newLevel = level;
        while (--newLevel >= 0 && head.nexts[newLevel] == null) {
            level = newLevel;
        }

        return removedNode.value;
    }


    public V get(K key){
        Node<K,V> node=head;
        int compare=-1;
        for(int i=level-1;i>=0;i--){
            while(node.nexts[i]!=null&&(compare=compare(key,node.nexts[i].key))>0){
                node=node.nexts[i];
            }
            //能来到这儿 可能是相等退出的
            if(compare==0){
                return node.nexts[i].value;
            }
        }
        return null;
    }


    /**
     * 随机生成高度
     * @return
     */
    private int randomLevel() {
        int level = 1;
        while (Math.random() < P && level < Max_LEVEL) {
            level++;
        }
        return level;
    }
    private int compare(K key1,K key2){
        return comparator!=null?comparator.compare(key1,key2):((Comparable)key1).compareTo(key2);
    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size()==0;
    }
    private static class Node<K,V>{
        K key;
        V value;
        Node<K, V>[] nexts;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            nexts = new Node[level];
        }
    }

}
