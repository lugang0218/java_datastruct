package com.hubu.tree.btree;

import java.util.*;

public class BPlusTree <K extends Comparable<K>,V>{
    /**
     *
     * 根节点
     */
    private Node<K,V> root=null;
    /**
     *
     * 指向叶子节点链表的头节点
     */
    private Node<K,V> head=null;
    public BPlusTree(int order){
        this.order=order;
    }

    private  void add(Node<K,V> node, Map.Entry<K,V> entry){
        /**
         *
         *
         * 如果是叶子节点
         */
        if(node.isLeaf){

            /**
             * 如果还没有满，就直接将key和value添加到集合中
             */

            doAdd(entry,node);


            /**
             *
             *
             * 添加做出调整，看看当前叶子节点是否需要划分
             */
            afterAdd(node);
        }

    }
    public void add(K key,V  value){
        if(key==null){
            return ;
        }
        if(root==null){
            root=new Node<>(order,true,true);
        }
        Map.Entry<K,V> entry=new AbstractMap.SimpleEntry<>(key,value);
        add(root,entry);
    }

    public void setHead(Node<K, V> head) {
        this.head = head;
    }
    /**
     *
     *
     * 添加到叶子节点满之后做出调整，将this的后半部分拆分出来创建一个右节点
     * this作为左节点
     */
    private void afterAdd(Node<K,V> node) {
        /**
         *
         * 如果满了做出调整
         */
        if(node.isFull()){
            /**
             * 右节点的开始节点
             */
            int rightBeginIndex=node.size()/2;
            /**
             * 新建一个节点作为右子节点
             */
            Node<K,V> right=new Node<>(order,false,true);
            Node<K,V> left=new Node<>(order,false,true);
            for(int i=rightBeginIndex;i<node.size();i++){
                right.entries.add(node.entries.get(i));
            }
            for(int i=0;i<rightBeginIndex;i++){
                left.entries.add(node.entries.get(i));
            }
            /**
             *
             *
             * 组成链表
             */
            left.next=right;
            right.prev=left;

            left.prev=node.prev;
            if(node.prev!=null){
                node.prev.next=left;
            }
            right.next=node.next;
            if(node.next!=null) {
                node.next.prev = right;
            }
            /**
             * 断开原来的连接
             */
            node.prev=null;
            node.next=null;


            /**
             * 设置头节点
             */

            if(left.prev==null){
               this.head=left;
            }
            /**
             *
             *
             * 创建parent
             */
            Node<K,V> parent;
            /**
             *
             * 为父节点准备一个entry
             */
            Map.Entry<K,V> newEntry=new AbstractMap.SimpleEntry<>
                    (node.entries.get(rightBeginIndex).getKey(),node.entries.get(rightBeginIndex).getValue());
            parent=node.parent;
            /**
             * 将左节点和右节点设置成为parent的子节点
             */
            if(parent!=null){
                /**
                 * 从父节点中移除当前节点、
                 */
                /**
                 * 获取当前节点在父节点中的位置
                 */
                int index = parent.children.indexOf(this);
                boolean remove = parent.children.remove(this);
                parent.children.add(index,left);
                parent.children.add(index+1,right);

                /**
                 *
                 * 将新的数据添加到父节点的数据数组中
                 */
                parent.entries.add(index,newEntry);
            }
            else{
                Node<K,V> newRoot=new Node<>(order,true,false);
                root=newRoot;
                parent=newRoot;
                parent.children.add(left);
                parent.children.add(right);
                parent.entries.add(right.entries.get(0));
            }

            /**
             * 将parent设置成为左右节点的父节点
             */
            left.parent=parent;
            right.parent=parent;


            node.entries.clear();
            node=null;
        }
    }
    private void doAdd(Map.Entry<K,V> entry,Node<K,V> node) {
        /**
         * 有序中寻找一个添加的位置
         */
        boolean isUpdate=false;
        int low=0;
        int high=node.size()-1;
        while(low<=high){
            int mid=(low+high)/2;
            if(entry.getKey().compareTo(node.entries.get(mid).getKey())<0){
                high=mid-1;
            }
            else if(entry.getKey().compareTo(node.entries.get(mid).getKey())==0){
                node.entries.set(mid,entry);
                isUpdate=true;
                break;
            }
            else{
                low=mid+1;
            }
        }
        /**
         *
         * 如果不是更新，直接插入
         */
        if(!isUpdate){
            node.entries.add(low,entry);
        }
    }



















    static class Node<K extends Comparable<K>,V>{
        boolean isRoot;
        boolean isLeaf;
        int max;
        Node<K,V> parent;
        Node<K,V> prev;
        Node<K,V> next;
        /**
         *
         * 数据的集合
         */
        List<Map.Entry<K,V>> entries;
        /**
         *
         * 子节点的集合
         */
        List<Node<K,V>> children;
        public Node(int max,boolean isRoot,boolean isLeaf){
            this.max=max;
            this.isRoot=isRoot;
            this.isLeaf=isLeaf;
            this.entries=new ArrayList<>();
        }
        public boolean isFull(){
            return entries.size()==max;
        }
        public int size(){
            return entries.size();
        }
        /**
         *
         *
         * 二分查找元素是否存在
         * @return
         */
        public V search(K key){
            if(key==null) return null;
            /**
             *
             * 如果是子节点
             */
            if(isLeaf){
                int low=0;
                int high=size()-1;
                while(low<=high){
                    int mid=(low+high)/2;
                    if(key.compareTo(entries.get(mid).getKey())<0){
                        high=mid-1;
                    }
                    else if(key.compareTo(entries.get(mid).getKey())==0){
                        return entries.get(mid).getValue();
                    }
                    else{
                        low=mid+1;
                    }
                }
                return null;
            }
            else{
                if(key.compareTo(entries.get(0).getKey())<0){
                    return children.get(0).search(key);
                }
                else if(key.compareTo(entries.get(size()-1).getKey())>=0){
                    return children.get(size()-1).search(key);
                }
                else{
                    int low=0;
                    int high=size()-1;
                    while(low<=high){
                        int mid=(low+high)/2;
                        if(key.compareTo(entries.get(mid).getKey())<0){
                            high=mid-1;
                        }
                        else if(key.compareTo(entries.get(mid).getKey())==0){
                            return children.get(mid+1).search(key);
                        }
                        else{
                            low=mid+1;
                        }
                    }
                    return children.get(low).search(key);
                }
            }
        }
    }
    private int order;
}
