package com.hubu.tree.btree;
import com.hubu.tree.btree.core.NodeWrapper;
import java.util.*;
public class BPlusTree<K extends Comparable<K>,V>{
    private Comparator<K> comparator;
    private int order;
    private Node<K,V> root;

    private int size;
    /**
     * 指向叶子节点的头节点，方便查询节点使用
     */
    private Node<K,V> head;

    private int maxValue;//添加的临界条件 它的值为order-1
    public BPlusTree(Comparator<K> comparator, int order){
        this.comparator=comparator;
        this.order=order;
        this.maxValue=order-1;
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
    public int getMaxValue() {
        return maxValue;
    }
    public void setRoot(Node<K, V> root) {
        this.root = root;
    }
    public void setHead(Node<K, V> head) {
        this.head = head;
    }
    /**
     * 从叶子节点遍历所有数据
     */
    public void visitorLeafNode(){
        if(head==null){
            return ;
        }
        Node<K,V> node=head;
        while(node!=null){
            List<Map.Entry<K, V>> entries=node.entries;
            for(Map.Entry<K,V> entry:entries){
                System.out.print(entry.getValue()+" ");
            }
            System.out.println();
            node=node.next;
        }
    }
    /**
     * 在子节点上查询大于等于key的所有数据
     * @param key
     * @return
     */
    public List<V> querySearchValueGq(K key){
        List<V> list=null;
        NodeWrapper<K,V> nodeWrapper=querySearchNode(key);
        if(nodeWrapper!=null){
            Node<K,V> node=nodeWrapper.getNode();
            int index=nodeWrapper.getIndex();
            Node<K,V> current=node;
            list=new ArrayList<>();
            while(current!=null){
                for(int i=index;i<current.entries.size();i++){
                    V value = current.entries.get(i).getValue();
                    list.add(value);
                }
                current=current.next;
                if(current!=null){
                    index=0;
                }
            }
        }
        return list;
    }
    /**
     * 子啊子节点上查询所有小于等于key的数据
     * @param key
     * @return
     */
    public List<V> querySearchValueLq(K key) {
        NodeWrapper<K, V> nodeWrapper = querySearchNode(key);
        List<V> list = null;
        if (nodeWrapper != null) {
            Node<K, V> node = nodeWrapper.getNode();
            int index = nodeWrapper.getIndex();
            //沿着node一直将数据取出来放入到集合中
            list = new ArrayList<>();
            Node<K, V> current = node;
            while (current != null) {
                for (int i = index; i >= 0; i--) {
                    V value = current.entries.get(i).getValue();
                    list.add(value);
                }

                current=current.next;
                if (current != null) {
                    index = current.entries.size() - 1;
                }

            }

        }
        return list;
    }
    public List<V> querySearchBetween(K low,K high){
        NodeWrapper<K,V> lowNodeWrapper=querySearchNode(low);
        NodeWrapper<K,V> highNodeWrapper=querySearchNode(high);
        List<V> list=null;
        Node<K, V> highNode=null;
        Node<K, V> lowNode=null;
        if(lowNodeWrapper!=null){
            lowNode= lowNodeWrapper.getNode();

        }
        if(highNodeWrapper!=null){
             highNode= highNodeWrapper.getNode();
        }
        int index=0;
        if(lowNodeWrapper!=null){
            index=lowNodeWrapper.getIndex();
        }
        if(lowNode!=null){
            Node<K,V> current=lowNode;
            list=new ArrayList<>();
            while(current!=highNode){
                for(int i=index;i<current.entries.size();i++){
                    V value = current.entries.get(i).getValue();
                    list.add(value);
                }
                current=current.next;
                if(current!=null){
                    index=0;
                }
            }

            if(current!=null){
                int highIndex=highNodeWrapper.getIndex();
                for(int i=0;i<=highIndex;i++){
                    list.add(current.entries.get(i).getValue());
                }
            }
        }
        /**
         * 从右向左边查找
         */
        else if(highNode!=null){

            list=new ArrayList<>();
            Node<K,V> current=highNode;
            int highIndex=highNodeWrapper.getIndex();
            while(current!=lowNode){
                for (int i = highIndex; i >= 0; i--) {
                    V value = current.entries.get(i).getValue();
                    list.add(value);
                }

                current=current.prev;
                if (current != null) {
                    index = current.entries.size() - 1;
                }
            }
        }
        return list;
    }
    private NodeWrapper<K,V> querySearchNode(K key) {
        return root.searchNode(key);
    }

    public int size(){
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
