package com.hubu.tree.printer;

import java.util.ArrayList;
import java.util.HashMap;
public class Trie<V> {
    private int size;
    private Node<V> root;

    public int size(){
        return size;
    }

    public void clear(){
        size = 0;
        root = null;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public V get(String prefix){
        Node<V> node = node(prefix);
        return (node != null && node.word) ? node.value : null;
    }

    public boolean contains(String key){
        Node<V> node = node(key);
        return (node != null && node.word);
    }

    public boolean startsWith(String prefix){
        return node(prefix) != null;
    }



    public V remove(String key){
        keyCheck(key);

        // 找到最后一个节点
        Node<V> node = node(key);
        //********** 如果不是单词结尾，不用作任何处理
        if(!node.word || node==null) return null;

        //**********单词结尾*******
        size--;
        V oldValue = node.value;
        //---------还有子结点,只需换颜色，删除value----
        if(node.children != null || !node.children.isEmpty()){
            node.word=false;
            node.value = null;
            return oldValue;
        }
        //---------没有子结点
        //不断向上找父节点直到该父节点有其他子结点，或者根节点
        Node<V> parent = null;
        while((parent=node.parent) != null){
            parent.children.remove(node.character); //删除结点
            //该节点是某个单词的结尾或还有其他子结点，不能删除
            if(parent.word || !parent.children.isEmpty()) break;
            node = parent;
        }

        return oldValue;

    }

    public V add(String key, V value){
        keyCheck(key);

        //创建根结点(添加时再建，而不是加载类的时候就建)
        if(root==null){
            root = new Node<>(null);
        }

        Node<V> node = root;

        //逐个字符的遍历存储
        for(int i=0;i<key.length();i++){
            char c = key.charAt(i);
            boolean emptyChild = (node.children == null);
            Node<V> childNode = emptyChild ? null : node.children.get(c);
            if(childNode == null) {
                //记录父节点，和本节点对应的字符
                childNode = new Node<>(node);
                childNode.character = c;

                node.children = emptyChild? new HashMap<>() : node.children;
                node.children.put(c,childNode);
            }
            node = childNode;
        }

        if(node.word){
            //已经有这个词，则覆盖
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        //新增一个单词
        node.word = true;
        node.value = value;
        size++;
        return null;
    }
    /**
     * 根据传入字符串，找到单词最后一个节点
     * 例如输入 dog
     * 找到 g
     */
    private Node<V> node(String key){
        keyCheck(key);
        Node<V> node = root;

        for (int i = 0;i<key.length();i++){
            if(node==null || node.children==null || node.children.isEmpty()) return null;
            Character c = key.charAt(i);
            node = node.children.get(c);
        }
        return node;
    }

    private void keyCheck(String key) {
        if(key == null || key.length()==0)
            throw new IllegalArgumentException("key must not be empty");
    }


    /**
     *
     *
     * 节点类
     * @param <V>
     */
    private static class Node<V> {

        //父节点
        Node<V> parent;

        //该节点对应的所有所有子节点
        HashMap<Character,Node<V>> children;


        //节点存储的字符
        Character character;

        /**
         * 节点存储的值
         */
        V value;
        boolean word;

        public Node(Node<V> parent){
            this.parent = parent;
        }
    }
}