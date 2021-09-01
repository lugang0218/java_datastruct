package com.hubu.rbtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
public class BPlusTree<K,V> {
    private Comparator<K> comparator;
    /**
     * '
     *
     * 抽象的节点类
     */


    private Node<K,V> root=null;

    public BPlusTree(Comparator<K> comparator){
        this.comparator=comparator;
        this.root=new LeafNode<K, V>(true,true);
    }

    public void add(K key,V value){
        Node<K,V> node=root;
        if(node.isLeaf){
            Node<K,V> newNode=new LeafNode<K,V>(key,value,true,true);
            newNode.add(key,value);
        }
    }
    abstract class Node<K,V> {

        protected boolean isLeaf;


        protected boolean isRoot;



        public Node(boolean isLeaf,boolean isRoot){
            this.isLeaf=isLeaf;
            this.isRoot=isRoot;
        }

        abstract boolean isLeaf();

        abstract boolean isRoot();


        /**
         *
         * 非叶子节点只保留V
         * @param key
         * @param value
         */
        abstract void put(K key,V value);

        public abstract void add(K key, V value);
    }

    class LeafNode<K,V> extends Node<K,V> {


        List<Node<K,V>> children;


        List<Map.Entry<K,V>> entries;




        private K key;

        private V value;

        public LeafNode(boolean isLeaf, boolean isRoot) {
            super(isLeaf, isRoot);
        }


        public LeafNode(K key,V value,boolean isLeaf,boolean isRoot){
            super(isLeaf,isRoot);
            this.key=key;
            this.value=value;


            this.entries=new ArrayList<>();


            if(!isLeaf){
                this.children=new ArrayList<>();
            }
        }


        @Override
        boolean isLeaf() {
            return isLeaf;
        }

        @Override
        boolean isRoot() {
            return isRoot;
        }
        void put(K key,V value){

        }

        @Override
        public void add(K key, V value) {

        }
    }

     class NoLeafNode<K,V> extends Node<K,V> {

        public NoLeafNode(boolean isLeaf, boolean isRoot) {
            super(isLeaf, isRoot);
        }

        @Override
        boolean isLeaf() {
            return false;
        }

        @Override
        boolean isRoot() {
            return false;
        }

        @Override
        void put(K key, V value) {

        }

         @Override
         public void add(K key, V value) {

         }
     }
}
