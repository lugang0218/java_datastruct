package com.hubu.tree;
import java.util.*;
public class BPlusTree <K,V> {
    private Node<K, V> root;
    private int order;
    private int size;
    private Node<K, V> head;
    private Comparator<K> comparator;

    public BPlusTree(Comparator<K> comparator, int order) {
        this.comparator = comparator;
        this.order = order;
    }

    public V put(K key,V value){
        checkKeyNotNull(key);
        return put(root,key,value);
    }

    public BPlusTree() {
        this(null, 3);
    }

    /**
     * 比较函数 如果comparator不为空 调用comparator的比较逻辑 否则参与比较的数据需要实现Comparable接口
     *
     * @param key1
     * @param key2
     * @return
     */

    private int compare(K key1, K key2) {
        return comparator != null ? comparator.compare(key1, key2) : ((Comparable) key1).compareTo(key2);
    }

    private void checkKeyNotNull(K key) {
        if (key == null) {
            throw new RuntimeException("key must not be null");
        }
    }

    public V put(Node<K, V> node, K key, V value) {
        if (node == null) {
            root = new Node<>(true, true);
            node = root;
        }
        if (node.isLeaf) {
            if (isNotFull(node)) {
                return put0(node, key, value);
            }
            Node<K, V> leftNode = new Node<>(false, true);
            Node<K, V> rightNode = new Node(false, true);
            V oldValue = splitNode(node, leftNode, rightNode, key, value);
            leftNode.next = rightNode;
            rightNode.prev = leftNode;
            if (node.prev == null) {
                head = leftNode;

            } else {
                leftNode.prev = node.prev;
                node.prev.next = leftNode;
                rightNode.next = node.next;
                if (node.next != null) {
                    node.next.prev = rightNode;
                }
            }
            node.prev = null;
            node.next = null;
            if (node.parent == null) {
                node.isRoot=false;
                Node<K, V> parent = new Node(true, false);
                leftNode.parent = parent;
                rightNode.parent = parent;
                parent.children.add(leftNode);
                parent.children.add(rightNode);
                parent.entries.add(rightNode.entries.get(0));
                node.entries = null;
                node.children = null;
                root = parent;
            } else {
                int index = node.parent.children.indexOf(node);
                leftNode.parent = node.parent;
                rightNode.parent = node.parent;
                node.parent.children.remove(node);
                node.parent.children.add(index, leftNode);
                node.parent.children.add(index + 1, rightNode);
                node.parent.entries.add(rightNode.entries.get(0));
                afterPut(node.parent);
                node.parent = null;
                node.entries = null;
                node.children = null;

            }
            return oldValue;
        } else {
            int compareResult = 0;
            try{
                if(node.entries==null){
                    System.out.println("hahaha");
                    System.out.println(node.parent);
                    System.out.println(node.children);
                    System.out.println("world");
                    System.out.println(key);
                }
                compareResult = compare(key, node.entries.get(0).getKey());
            }catch (Exception e){
                System.out.println(node.isLeaf);
                System.out.println(node.isRoot);

                System.out.println(node);
                System.out.println(node.entries);
                e.printStackTrace();
            }

            if (compareResult < 0) {
                return put(node.children.get(0), key, value);
            }
            compareResult = compare(key, node.entries.get(node.entries.size() - 1).getKey());
            if (compareResult >= 0) {
                return put(node.children.get(node.children.size() - 1), key, value);
                //如果key大于节点最右边的key，递归往最右边的子节点插入
            }
            int low = 0, high = node.entries.size() - 1, mid = 0;
            int compare;
            while (low <= high) {
                mid = (low + high) / 2;
                compare = compare(node.entries.get(mid).getKey(),key);
                if (compare == 0) {
                    return put(node.children.get(mid+1),key,value);
                } else if (compare < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return put(node.children.get(low),key,value);
        }
    }


    /**
     *
     *
     * 父系节点添加之后做出调整
     * @param node
     */
    private void afterPut(Node<K, V> node) {
        //如果孩子节点的数量比阶数还要大
        if(node.children.size()>order){
            Node<K,V> leftNode=new Node<>(false,false);
            Node<K,V> rightNode=new Node<>(false,false);
            int leftSize=(order+1)/2+(order+1)%2;
            int rightSize=(order+1)/2;
            for(int i=0;i<leftSize;i++){
                leftNode.children.add(node.children.get(i));
                //当前节点的子节点与leftNode关联
                node.children.get(i).parent=leftNode;
            }
            for(int i=0;i<rightSize;i++) {
                rightNode.children.add(node.children.get(leftSize + i));
                node.children.get(leftSize + i).parent = rightNode;
            }

            for (int i = 0; i < leftSize - 1; i++) {
                leftNode.entries.add(node.entries.get(i));
            }
            for (int i = 0; i < rightSize - 1; i++) {
                rightNode.entries.add(node.entries.get(leftSize + i));
            }
            //如果拷贝到根节点
            if(node.parent==null){
                Node<K,V> newParent=new Node<>(true,false);
                root=newParent;
                leftNode.parent=newParent;
                rightNode.parent=newParent;
                newParent.children.add(leftNode);
                newParent.children.add(rightNode);
                newParent.entries.add(node.entries.get(leftSize-1));
                node.entries=null;
                node.children=null;
                //当前节点指向的父节点为空
            }
            //如果父节点不为空
            else{
                //父节点将自己删除掉
                int index = node.parent.children.indexOf(node);
                node.parent.children.remove(node);
                leftNode.parent=node.parent;
                rightNode.parent=node.parent;
                node.parent.children.add(index,leftNode);
                node.parent.children.add(index+1,rightNode);
                node.parent.entries.add(node.entries.get(leftSize-1));

                //沿着父节点继续向上递归修改
                afterPut(node.parent);
                //来到这儿将parent置空
                node.parent=null;
                node.children=null;
                node.entries=null;
            }
        }
    }
    /**
     * 拆分node节点到左右节点
     * @param node
     * @param key
     * @param value
     * @return
     */


    private V splitNode(Node<K, V> node,Node<K,V> leftNode,Node<K,V> rightNode,K key,V value) {
        int leftSize=(order)/2+(order)%2;
        boolean flag=false;
        V oldValue=null;
        for(int i=0;i<node.entries.size();i++){
            if(leftSize!=0){
                leftSize--;
                Map.Entry<K, V> entry = node.entries.get(i);
                int compare=compare(key,entry.getKey());
                //如果key比当前集合中的最左边还小并且当前元素还没有添加进去
                if(!flag&&compare<0){
                    AbstractMap.SimpleEntry<K, V> newEntry = new AbstractMap.SimpleEntry<>(key, value);
                    leftNode.entries.add(newEntry);
                    flag=true;
                    i--;
                }
                else if(compare==0){
                    oldValue = node.entries.get(i).getValue();
                    node.entries.get(i).setValue(value);
                    leftNode.entries.add(node.entries.get(i));
                    flag=true;
                }
                else{
                    leftNode.entries.add(node.entries.get(i));
                }

            }else{
                Map.Entry<K, V> entry = node.entries.get(i);
                int compare=compare(key,entry.getKey());
                if(!flag&&compare<0){
                    AbstractMap.SimpleEntry<K, V> newEntry = new AbstractMap.SimpleEntry<>(key, value);
                    rightNode.entries.add(newEntry);
                    flag=true;
                    i--;
                }
                else if(compare==0){
                    oldValue = node.entries.get(i).getValue();
                    node.entries.get(i).setValue(value);
                    rightNode.entries.add(node.entries.get(i));
                    flag=true;
                }
                else{
                    rightNode.entries.add(node.entries.get(i));
                }
            }
        }
        if(!flag) {
            AbstractMap.SimpleEntry<K, V> newEntry = new AbstractMap.SimpleEntry<>(key, value);
            rightNode.entries.add(newEntry);
        }
        return oldValue;
    }
    /**
     * 将key value 组装到node节点中
     * @param node
     * @param key
     * @param value
     */
    private V  put0(Node<K,V> node,K key,V value){
        int low=0;
        int high=node.entries.size()-1;
        int compare=0;
        while (low <= high) {
            int mid = (low + high) / 2;
            compare = compare(key, node.entries.get(mid).getKey());
            if (compare > 0) {
                low=mid+1;
            } else if (compare == 0) {
                V oldValue=node.entries.get(mid).getValue();
                node.entries.get(mid).setValue(value);
                return oldValue;
            } else {
                high=mid-1;
            }
        }
        AbstractMap.SimpleEntry<K,V> newEntry=new AbstractMap.SimpleEntry<>(key,value);
        node.entries.add(low,newEntry);
        return null;
    }
    public V get(K key){
        checkKeyNotNull(key);
        if(root==null){
            return null;
        }
        return search(root,key);
    }
    /**
     *
     *
     * 递归二分查找节点
     *
     */
    private  V search(Node<K,V> node,K key){
        if(node.isLeaf) {
            int low = 0;
            int high = node.entries.size() - 1;
            int compare = 0;
            while (low <= high) {
                int mid = (low + high) / 2;
                compare = compare(key, node.entries.get(mid).getKey());
                if (compare > 0) {
                   low=mid+1;
                } else if (compare == 0) {
                    return node.entries.get(mid).getValue();
                } else {
                    high=mid-1;
                }
            }
            return null;
        }
        else{
            int compare=0;
            compare=compare(key,node.entries.get(0).getKey());
            if(compare<0){
                return search(node.children.get(0),key);
            }
            compare=compare(key,node.entries.get(node.entries.size()-1).getKey());
            if(compare>=0){
                return search(node.children.get(node.children.size()-1),key);
            }
            int low=0;
            int high=node.entries.size()-1;
            while (low <= high) {
                int mid = (low + high) / 2;
                compare = compare(key, node.entries.get(mid).getKey());
                if (compare > 0) {
                    low=mid+1;
                } else if (compare == 0) {
                    return search(node.children.get(mid+1),key);
                } else {
                    high=mid-1;
                }
            }
            return search(node.children.get(low),key);
        }
    }
    private boolean isNotFull(Node<K,V> node){
        return node.entries.size()<order-1;
    }
    static class Node<K,V>{
        boolean isRoot;
        boolean isLeaf;


        Node<K,V> parent;

        Node<K,V> prev;

        Node<K,V> next;
        List<Map.Entry<K,V>> entries;
        List<Node<K,V>> children;

        public Node(boolean isRoot,boolean isLeaf){
            this.isRoot=isRoot;
            this.isLeaf=isLeaf;
            this.entries=new ArrayList<>();
            /**
             * 如果不是叶子节点，就为其创建子节点
             */
            if(!isLeaf){
                this.children=new ArrayList<>();
            }
        }
    }
}
