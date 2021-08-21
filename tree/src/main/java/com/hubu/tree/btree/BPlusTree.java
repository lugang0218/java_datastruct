package com.hubu.tree.btree;
import com.hubu.tree.BplusTree;

import javax.management.remote.rmi._RMIConnection_Stub;
import java.util.ArrayList;
import java.util.List;
public class BPlusTree <E>{
    private int order;
    private Node<E> root;
    public BPlusTree(){
        this(3);
    }
    public BPlusTree(int order){
        this.order=order;
    }
    /**
     *
     *
     * 添加数据到B树中
     * @param data
     */
    public void add(E data){

        Node<E> node=root;

        if(root==null) {
            node=root = new Node<>(order, true, true, null);
        }


        /**
         * 如果是叶子节点
         */
        if(node.isLeaf()){
            node.add(data);
            if(node.isFull()){
                split(node);
            }
        }
    }


    /**
     *
     * 三阶和四阶为例
     * @param node
     */
    private void split(Node<E> node) {
        int dataSize=node.getDataSize();
        int mid=0;
        mid=(dataSize%2==0)?(dataSize/2)-1:dataSize/2;
        int left=mid;
        int right=mid+1;
        //分裂的左边节点
        Node<E> leftNode=new Node<>(order,false,true,null);
        //分裂的右边节点
        Node<E> rightNode=new Node<>(order,false,true,null);
        for(int i=0;i<left;i++){
            leftNode.add(node.getData(i));
        }
        for(int i=right;i<dataSize;i++){
            rightNode.add(node.getData(i));
        }
    }

    static class Node<T> {
        /**
         * 数据的最大值+1
         */
        private int max=0;


        /**
         * 数据的最大个数
         */

        private int dataLength;


        /**
         * 节点的最大个数
         */

        private int nodeLength;


        private boolean root =false;



        private boolean leaf =false;
        /**
         *
         * 数据
         */
        private List<T> array;

        /**
         * 子节点
         */
        private List<Node> child;





        /**
         * 元素的数量
         */
        private int size=0;


        /**
         * 父节点
         */
        private Node<T> parent=null;


        /**
         *
         * 节点对应的树
         */
        private BplusTree tree;


        public Node(int max, boolean root, boolean leaf){
            this(max, root, leaf,null);
        }


        public T getData(int index){
            return array.get(index);
        }

        public Node<T> getChild(int index){
            return child.get(index);
        }

        public int getDataSize(){
            return array.size();
        }

        public int getChildSize(){
            return child.size();
        }




        public T removeData(int index){
            return array.remove(index);
        }

        public Node(int max, boolean root, boolean leaf, Node<T> parent){
            this.max=max;
            this.leaf = leaf;
            this.root = root;
            this.parent=parent;
            this.dataLength=max;
            this.nodeLength=max;
        }


        public boolean isLeaf() {
            return leaf;
        }

        public boolean isRoot() {
            return root;
        }

        public boolean isFull(){
            return array!=null&&array.size()==nodeLength;
        }
        /**
         *
         *
         * 该方法只负责插入 能来到这儿就可以插入一个节点到该节点对应的数组中
         * @param data
         */
        public void add(T data){
            if(array==null){
                array=new ArrayList<>();
            }
            int low=0;
            int high=array.size()-1;
            while(low<=high){
                int mid=(low+high)/2;
                T current = array.get(mid);
                if(current.equals(data)){
                    array.set(mid,data);
                }
                else if(((Comparable)current).compareTo(data)>0){
                    high=mid-1;
                }
                else{
                    low=mid+1;
                }
            }
            array.add(low,data);
        }

    }
}
