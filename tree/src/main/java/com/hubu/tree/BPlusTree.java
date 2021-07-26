package com.hubu.tree;
/**
 *
 *
 * 实现B+树
 * @param <T>
 * @param <V>
 */
public class BPlusTree <T, V extends Comparable<V>>{
    //B+树的阶
    private Integer bTreeOrder;
    //B+树的非叶子节点最小拥有的子节点数量（同时也是键的最小数量）
    //private Integer minNUmber;
    //B+树的非叶子节点最大拥有的节点数量（同时也是键的最大数量）
    private Integer maxNumber;

    private Node<T, V> root;

    private LeafNode<T, V> left;

    //无参构造方法，默认阶为3
    public BPlusTree(){
        this(3);
    }

    //有参构造方法，可以设定B+树的阶
    public BPlusTree(Integer bTreeOrder){
        this.bTreeOrder = bTreeOrder;
        //this.minNUmber = (int) Math.ceil(1.0 * bTreeOrder / 2.0);
        //因为插入节点过程中可能出现超过上限的情况,所以这里要加1


        /**
         *
         * 插入可能会向上溢出，先插入多一个，在分裂
         */
        this.maxNumber = bTreeOrder + 1;


        /**
         * 初始化root节点
         */
        this.root = new LeafNode<T, V>();
        this.left = null;
    }

    //查询
    public T find(V key){
        T t = this.root.find(key);
        if(t == null){
            System.out.println("不存在");
        }
        return t;
    }

    //插入
    public void insert(T value, V key){
        if(key == null)
            return;
        Node<T, V> t = this.root.insert(value, key);
        if(t != null)
            this.root = t;
        this.left = (LeafNode<T, V>)this.root.refreshLeft();
    }


    /**
     * 节点父类，因为在B+树中，非叶子节点不用存储具体的数据，只需要把索引作为键就可以了
     * 所以叶子节点和非叶子节点的类不太一样，但是又会公用一些方法，所以用Node类作为父类,
     * 而且因为要互相调用一些公有方法，所以使用抽象类
     *
     * @param <T> 同BPlusTree
     * @param <V>
     */
    abstract class Node<T, V extends Comparable<V>>{
        //父节点
        protected Node<T, V> parent;
        //子节点的指针
        protected Node<T, V>[] childs;
        //键（子节点）数量
        protected Integer size;
        //键
        protected Object keys[];

        //构造方法
        public Node(){
            this.keys = new Object[maxNumber];
            this.childs = new Node[maxNumber];
            this.size = 0;
            this.parent = null;
        }

        //查找
        abstract T find(V key);

        //插入
        abstract Node<T, V> insert(T value, V key);

        abstract LeafNode<T, V> refreshLeft();
    }


    /**
     * 非叶节点类
     * @param <T>
     * @param <V>
     */

    class BPlusNode <T, V extends Comparable<V>> extends Node<T, V>{
        public BPlusNode() {
            super();
        }
        /**
         * 递归查找,这里只是为了确定值究竟在哪一块,真正的查找到叶子节点才会查
         * @param key
         * @return
         */
        @Override
        T find(V key) {
            int i = 0;
            while(i < this.size){
                if(key.compareTo((V) this.keys[i]) <= 0)
                    break;
                i++;
            }
            if(this.size == i)
                return null;
            return this.childs[i].find(key);
        }
        @Override
        Node<T, V> insert(T value, V key) {
            int i = 0;
            while(i < this.size){
                if(key.compareTo((V) this.keys[i]) < 0)
                    break;
                i++;
            }
            if(key.compareTo((V) this.keys[this.size - 1]) >= 0) {
                i--;
            }
            return this.childs[i].insert(value, key);
        }

        @Override
        LeafNode<T, V> refreshLeft() {
            return this.childs[0].refreshLeft();
        }

        /**
         * 当叶子节点插入成功完成分解时,递归地向父节点插入新的节点以保持平衡
         * @param node1
         * @param node2
         * @param key
         */
        Node<T, V> insertNode(Node<T, V> node1, Node<T, V> node2, V key){
            V oldKey = null;


            /**
             *
             *
             * 如果有size，先获取旧的key
             */
            if(this.size > 0)
                oldKey = (V) this.keys[this.size - 1];


            /**
             *
             *
             * 如果传递进来的key为空或则size==0，表明是第一次插入到当前节点，
             * 将left和right的keys获取出来放到当前父节点的keys中
             */
            if(key == null || this.size <= 0){
                this.keys[0] = node1.keys[node1.size - 1];
                this.keys[1] = node2.keys[node2.size - 1];
                this.childs[0] = node1;
                this.childs[1] = node2;
                this.size += 2;
                return this;
            }
            int i = 0;
            while(key.compareTo((V)this.keys[i]) != 0){
                i++;
            }
            this.keys[i] = node1.keys[node1.size - 1];
            this.childs[i] = node1;
            Object tempKeys[] = new Object[maxNumber];
            Object tempChilds[] = new Node[maxNumber];
            System.arraycopy(this.keys, 0, tempKeys, 0, i + 1);
            System.arraycopy(this.childs, 0, tempChilds, 0, i + 1);
            System.arraycopy(this.keys, i + 1, tempKeys, i + 2, this.size - i - 1);
            System.arraycopy(this.childs, i + 1, tempChilds, i + 2, this.size - i - 1);
            tempKeys[i + 1] = node2.keys[node2.size - 1];
            tempChilds[i + 1] = node2;
            this.size++;
            if(this.size <= bTreeOrder){
                System.arraycopy(tempKeys, 0, this.keys, 0, this.size);
                System.arraycopy(tempChilds, 0, this.childs, 0, this.size);
                return null;
            }
            Integer middle = this.size / 2;
            BPlusNode<T, V> tempNode = new BPlusNode<T, V>();
            tempNode.size = this.size - middle;
            tempNode.parent = this.parent;
            if(this.parent == null) {
                BPlusNode<T, V> tempBPlusNode = new BPlusNode<>();
                tempNode.parent = tempBPlusNode;
                this.parent = tempBPlusNode;
                oldKey = null;
            }
            System.arraycopy(tempKeys, middle, tempNode.keys, 0, tempNode.size);
            System.arraycopy(tempChilds, middle, tempNode.childs, 0, tempNode.size);
            for(int j = 0; j < tempNode.size; j++){
                tempNode.childs[j].parent = tempNode;
            }
            this.size = middle;
            this.keys = new Object[maxNumber];
            this.childs = new Node[maxNumber];
            System.arraycopy(tempKeys, 0, this.keys, 0, middle);
            System.arraycopy(tempChilds, 0, this.childs, 0, middle);
            BPlusNode<T, V> parentNode = (BPlusNode<T, V>)this.parent;
            return parentNode.insertNode(this, tempNode, oldKey);
        }
    }

    /**
     * 叶节点类
     * @param <V>
     * @param <K>
     */
    class LeafNode <V, K extends Comparable<K>> extends Node<V, K> {

        protected Object values[];
        protected LeafNode left;
        protected LeafNode right;

        public LeafNode(){
            super();
            this.values = new Object[maxNumber];
            this.left = null;
            this.right = null;
        }
        @Override
        V find(K key) {
            if(this.size <=0)
                return null;

//            System.out.println("叶子节点查找");

            Integer left = 0;
            Integer right = this.size;

            Integer middle = (left + right) / 2;

            while(left < right){
                K middleKey = (K) this.keys[middle];
                if(key.compareTo(middleKey) == 0)
                    return (V) this.values[middle];
                else if(key.compareTo(middleKey) < 0)
                    right = middle;
                else
                    left = middle;
                middle = (left + right) / 2;
            }
            return null;
        }
        @Override
        Node<V, K> insert(V value, K key) {
            K oldKey = null;


            /**
             * 判断是否是第一次添加，如果size>0,判断之前是否添加过
             */
            if(this.size > 0)
                oldKey = (K) this.keys[this.size - 1];
            int i = 0;
            while(i < this.size){
                if(key.compareTo((K) this.keys[i]) < 0){
                    break;
                }
                    i++;
            }
            Object tempKeys[] = new Object[maxNumber];
            Object tempValues[] = new Object[maxNumber];
            System.arraycopy(this.keys, 0, tempKeys, 0, i);
            System.arraycopy(this.values, 0, tempValues, 0, i);
            System.arraycopy(this.keys, i, tempKeys, i + 1, this.size - i);
            System.arraycopy(this.values, i, tempValues, i + 1, this.size - i);
            tempKeys[i] = key;
            tempValues[i] = value;
            this.size++;


            /**
             *
             *
             * 如果还不需要合并，则拷贝数组
             */
            if(this.size <= bTreeOrder){
                System.arraycopy(tempKeys, 0, this.keys, 0, this.size);
                System.arraycopy(tempValues, 0, this.values, 0, this.size);
                Node node = this;
                while (node.parent != null){
                    K tempkey = (K)node.keys[node.size - 1];
                    if(tempkey.compareTo((K)node.parent.keys[node.parent.size - 1]) > 0){
                        node.parent.keys[node.parent.size - 1] = tempkey;
                        node = node.parent;
                    }
                    else {
                    	break;
                    }
                }
                return null;
            }

            /**
             *
             *
             * size==maxNumber则需要合并
             */
            Integer middle = this.size / 2;
            LeafNode<V, K> rightNode = new LeafNode<V, K>();
            rightNode.size = this.size - middle;
            rightNode.parent = this.parent;


            /**
             *
             *
             * 创建一个父节点
             */
            if(this.parent == null) {
                BPlusNode<V, K> parent = new BPlusNode<>();
                rightNode.parent = parent;
                this.parent = parent;
                oldKey = null;
            }
            System.arraycopy(tempKeys, middle, rightNode.keys, 0, rightNode.size);
            System.arraycopy(tempValues, middle, rightNode.values, 0, rightNode.size);


            /**
             *
             *
             *
             * 重新更新当前节点的值
             */
            this.size = middle;
            this.keys = new Object[maxNumber];
            this.values = new Object[maxNumber];


            /**
             *
             *
             * 拷贝原来的值到新的节点之中
             */
            System.arraycopy(tempKeys,0,this.keys,0,middle);
            System.arraycopy(tempValues, 0, this.values, 0, middle);
            /**
             *
             *
             * 连接左右节点，形成双向链表
             */
            this.right = rightNode;
            rightNode.left = this;
            BPlusNode<V, K> parentNode = (BPlusNode<V, K>)this.parent;
            /**
             *
             * 与父节点建立关系，将左右节点和oldKey传递进去
             */
            return parentNode.insertNode(this, rightNode, oldKey);
        }
        @Override
        LeafNode<V, K> refreshLeft() {
            if(this.size <= 0)
                return null;
            return this;
        }
    }
}