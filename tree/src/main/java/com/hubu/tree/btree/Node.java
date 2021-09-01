package com.hubu.tree.btree;
import com.hubu.tree.btree.core.NodeWrapper;
import java.util.*;
public class Node<K extends Comparable<K>, V> {
        // 是否为叶子节点
        protected boolean isLeaf;

        // 是否为根节点
        protected boolean isRoot;

        // 父节点
        protected Node<K, V> parent;

        // 叶节点的前节点
        protected Node<K, V> prev;

        // 叶节点的后节点
        protected Node<K, V> next;

        // 节点的关键字列表
        protected List<Map.Entry<K, V>> entries;

        // 子节点列表
        protected List<Node<K, V>> children;

        public Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
            entries = new ArrayList();

            if (!isLeaf) {
                children = new ArrayList();
            }
        }

        public Node(boolean isLeaf, boolean isRoot) {
            this(isLeaf);
            this.isRoot = isRoot;
        }
        public V binarySearch(K key) {
            if (isLeaf) {
                int low = 0;
                int high = entries.size() - 1;
                while (low <= high) {
                    int mid = (low + high) / 2;
                    Map.Entry<K, V> entry = entries.get(mid);
                    int compareResult=((Comparable)key).compareTo(entry.getKey());
                    if (compareResult< 0) {
                        high=mid-1;
                    } else if (compareResult == 0) {
                        return entry.getValue();
                    } else {
                        low=mid+1;
                    }
                }
                return null;
            } else {

                int compareResult=((Comparable)key).compareTo(entries.get(0).getKey());
                if(compareResult<0) {
                    return children.get(0).binarySearch(key);
                } else if (((Comparable)key).compareTo( entries.get(entries.size() - 1).getKey()) > 0) {
                    return children.get(children.size()-1).binarySearch(key);
                } else {
                    int low = 0;
                    int high = entries.size() - 1;
                    while (low <= high) {
                        int mid = (low + high) / 2;
                        Map.Entry<K, V> entry = entries.get(mid);
                        int compare=((Comparable)key).compareTo(entry.getKey());
                        if (compare< 0) {
                            low = mid + 1;
                        } else if (compare == 0) {
                            return children.get(mid + 1).binarySearch(key);
                        } else {
                            high = mid - 1;
                        }
                    }
                    return entries.get(low).getValue();
                }
            }
        }
    /**
     *
     * 范围查询
     */
    //寻找指定key对应的value
    public V querySearchEq(K key) {
        return binarySearch(key);
    }


    /**
     * 返回值等于key的叶子节点
     * @param key
     * @return
     */
    public NodeWrapper<K,V> searchNode(K key){
        /**
         * 如果是叶子节点
         */
        if(isLeaf) {
            int low = 0;
            int high = entries.size() - 1;
            int compare = 0;
            NodeWrapper<K, V> nodeWrapper = null;
            while (low <= high) {
                int mid = (low + high) / 2;
                compare = ((Comparable) key).compareTo(entries.get(mid).getKey());
                if (compare == 0) {
                    nodeWrapper=new NodeWrapper<>();
                    nodeWrapper.setNode(this);
                    nodeWrapper.setIndex(mid);
                    return nodeWrapper;
                } else if (compare > 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return nodeWrapper;
        }
        else {

            if(((Comparable)key).compareTo(entries.get(0).getKey())<0){
                return children.get(0).searchNode(key);
            }
            else if(((Comparable)key).compareTo(entries.get(entries.size()-1).getKey())>=0){
                return children.get(children.size()-1).searchNode(key);
            }
            int low=0;
            int high=entries.size()-1;
            int compare=0;
            while(low<=high){
                int mid=(low+high)/2;
                compare=((Comparable)key).compareTo(entries.get(mid).getKey());
                if(compare>0){
                    low=mid+1;
                }
                else if(compare<0){
                    high=mid-1;
                }
                else{
                    return children.get(mid+1).searchNode(key);
                }
            }
            return children.get(low).searchNode(key);
        }
    }
    public void updateNotLeafNode(BPlusTree<K,V> tree){
            /**
             *
             *
             * 5 阶级 那么子节点的个数最多是5
             * 如果子节点的个数比order大
             */
            if(children.size()>tree.getOrder()){
                Node<K,V> leftNode=new Node<>(false);
                Node<K,V> rightNode=new Node<>(false);
                int leftSize=(tree.getOrder()+1)/2+(tree.getOrder()+1)%2;
                int rightSize=(tree.getOrder()+1)/2;
                for(int i=0;i<leftSize;i++){
                    leftNode.children.add(children.get(i));

                    //当前节点的子节点与leftNode关联
                    children.get(i).parent=leftNode;
                    if(i<leftSize-1){
                        leftNode.entries.add(entries.get(i));
                    }
                }
                for(int i=0;i<rightSize;i++) {
                    rightNode.children.add(children.get(leftSize + i));
                    children.get(leftSize + i).parent = rightNode;
                    if (i < rightSize - 1) {
                        rightNode.entries.add(entries.get(leftSize + i));
                    }
                }
                //如果拷贝到根节点
                if(parent==null){
                    isRoot=false;
                    Node<K,V> newParent=new Node<>(false,true);
                    tree.setRoot(newParent);
                    leftNode.parent=newParent;
                    rightNode.parent=newParent;
                    newParent.children.add(leftNode);
                    newParent.children.add(rightNode);
                    newParent.entries.add(entries.get(leftSize-1));
                    entries=null;
                    children=null;
                    //当前节点指向的父节点为空
                }
                //如果父节点不为空
                else{
                    //父节点将自己删除掉
                    int index = parent.children.indexOf(this);
                    parent.children.remove(this);
                    leftNode.parent=parent;
                    rightNode.parent=parent;
                    parent.children.add(index,leftNode);
                    parent.children.add(index+1,rightNode);
                    parent.entries.add(entries.get(leftSize-1));
                    children=null;
                    entries=null;
                    //沿着父节点继续向上递归修改
                    parent.updateNotLeafNode(tree);



                    //来到这儿将parent置空

                    parent=null;
                }
            }
        }
    /**
     *
     *
     * 添加的时候是先判断在添加 比如数组中最多只能容纳5个元素，
     * 4个的时候已经可以确定满了，因为添加进去就是5个，此时先将4个分裂，在将5添加进去
     * @param key
     * @param value
     * @param tree
     */
    public void  put(K key, V value, BPlusTree<K, V> tree) {
        if (isLeaf) {
            //如果节点的数量小于order,表示还可以添加
            if (entries.size() < tree.getMaxValue()) {
                 binarySearchPut(key, value,tree);
                 return;
            }
            Node<K, V> leftNode = new Node<K, V>(true, false);
            Node<K, V> rightNode = new Node<K, V>(true, false);
            //如果当前节点有前驱节点
            if (prev != null) {
                prev.next = leftNode;
                leftNode.prev = prev;
            }

            //如果当前节点有后继节点
            if (next != null) {
                next.prev = rightNode;
                rightNode.next = next;
            }

            //说明是第一次
            if (prev == null) {
                tree.setHead(leftNode);
            }


            /**
             * 将左右节点连接上
             */
            leftNode.next = rightNode;
            rightNode.prev = leftNode;



            /**
             * 将当前节点的prev和next断开，因为当前节点将会被舍弃
             */
            prev = null;
            next = null;




            AbstractMap.SimpleEntry<K, V> newEntry = new AbstractMap.SimpleEntry<K, V>(key, value);


            splitLeftAndRight(leftNode, rightNode, newEntry,tree.getMaxValue(),tree);

            if (parent != null) {
                    //获取当前节点在父节点中的索引
                int index = parent.children.indexOf(this);
                parent.children.remove(this);
                leftNode.parent = parent;
                rightNode.parent = parent;
                parent.children.add(index, leftNode);
                parent.children.add(index + 1, rightNode);
                parent.entries.add(rightNode.entries.get(0));

                this.entries = null;
                this.children = null;
                //沿着父节点分裂

                parent.updateNotLeafNode(tree);
                parent=null;
            } else {
                //当前节点不在是根节点
                isRoot = false;
                Node<K, V> parent = new Node<K, V>(false, false);
                tree.setRoot(parent);
                parent.children = new ArrayList<>();
                leftNode.parent = parent;
                rightNode.parent = parent;
                parent.children.add(leftNode);
                parent.children.add(rightNode);
                parent.entries.add(rightNode.entries.get(0));
                children = null;
                entries = null;
            }
        }

        else{
            if (key.compareTo(entries.get(0).getKey()) < 0) {
                children.get(0).put(key, value, tree);
                //如果key大于节点最右边的key，递归往最右边的子节点插入
            } else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
                children.get(children.size() - 1).put(key, value, tree);
                //二分查找一个节点插入
            } else {
                int low = 0, high = entries.size() - 1, mid = 0;
                int comp;
                while (low <= high) {
                    mid = (low + high) / 2;
                    comp = entries.get(mid).getKey().compareTo(key);
                    if (comp == 0) {
                        children.get(mid + 1).put(key, value, tree);
                        break;
                    } else if (comp < 0) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
                if (low > high) {
                    children.get(low).put(key, value, tree);
                }
            }
        }
    }

    public V get(K key) {
        //如果是叶子节点
        if (isLeaf) {
            int low = 0, high = entries.size() - 1, mid;
            int comp;
            while (low <= high) {
                mid = (low + high) / 2;
                comp = entries.get(mid).getKey().compareTo(key);
                if (comp == 0) {
                    return entries.get(mid).getValue();
                } else if (comp < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            //未找到所要查询的对象
            return null;
        }
        //如果不是叶子节点
        //如果key小于节点最左边的key，沿第一个子节点继续搜索
        if (key.compareTo(entries.get(0).getKey()) < 0) {
            return children.get(0).get(key);
            //如果key大于等于节点最右边的key，沿最后一个子节点继续搜索
        } else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
            return children.get(children.size() - 1).get(key);
            //否则沿比key大的前一个子节点继续搜索
        }
        else {
            int low = 0, high = entries.size() - 1, mid = 0;
            int comp;
            while (low <= high) {
                mid = (low + high) / 2;
                comp = entries.get(mid).getKey().compareTo(key);
                if (comp == 0) {
                    return children.get(mid + 1).get(key);
                } else if (comp < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return children.get(low).get(key);
        }
    }


        private V binarySearchPut(K key, V value,BPlusTree<K,V> tree){

        int low=0;
        int high=entries.size()-1;
        int compareResult=0;
        while(low<=high){
            int mid=(low+high)/2;
            compareResult=((Comparable<K>)key).compareTo(entries.get(mid).getKey());
            if(compareResult>0){
                low=mid+1;
            }
            else if(compareResult==0){
                V oldValue = entries.get(mid).getValue();
                entries.get(mid).setValue(value);
                return oldValue;
            }
            else{
                high=mid-1;
            }
        }
        AbstractMap.SimpleEntry<K, V> newEntry=new AbstractMap.SimpleEntry<>(key,value);
        if(low>high){
            //添加节点
            tree.setSize(tree.size()+1);
            entries.add(low,newEntry);
        }
        return null;
    }
    private void  splitLeftAndRight(Node<K, V> leftNode, Node<K, V> rightNode, AbstractMap.SimpleEntry<K, V> entry, int total,BPlusTree<K,V> tree) {
        int leftSize=((total)+1)/2+(total+1)%2;
        boolean flag=false;;
        for(int i=0;i<entries.size();i++){
            if(leftSize!=0){
                leftSize--;
                if(!flag){
                    int compare=((Comparable)entry.getKey()).compareTo(entries.get(i).getKey());
                    if(compare<0){
                        //
                        leftNode.entries.add(entry);
                        tree.setSize(tree.size()+1);
                        i--;
                        flag=true;
                    }
                    else if(compare==0){
                        entries.get(i).setValue(entry.getValue());
                        leftNode.entries.add(entries.get(i));
                    }
                    else{
                        leftNode.entries.add(entries.get(i));
                    }
                }
                else{
                    leftNode.entries.add(entries.get(i));
                }
            }else{
                if(!flag){
                    int compare=((Comparable)entry.getKey()).compareTo(entries.get(i).getKey());
                    if(compare<0){

                        rightNode.entries.add(entry);
                        tree.setSize(tree.size()+1);
                        i--;
                        flag=true;
                    }
                    else if(compare==0){
                        entries.get(i).setValue(entry.getValue());
                        rightNode.entries.add(entries.get(i));
                    }
                    else{
                        rightNode.entries.add(entries.get(i));
                    }
                }else{
                    rightNode.entries.add(entries.get(i));
                }
            }
        }
        if(!flag){
            rightNode.entries.add(entry);
            tree.setSize(tree.size()+1);
        }
    }
    /**
     * 二分删除关键字key
     * @param key
     * @return
     */
    private V binarySearchRemove(K key) {
        int low = 0;
        int high = entries.size() - 1;
        int compareResult = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            compareResult = ((Comparable) key).compareTo(entries.get(mid).getKey());
            if (compareResult > 0) {
                low = mid + 1;
            } else if (compareResult == 0) {
                return entries.remove(mid).getValue();
            } else {
                high = mid - 1;
            }
        }
        return null;
    }



    /**
     *
     *
     * 删除节点
     * @param
     * @return
     */
    public V remove(K key,BPlusTree<K,V> tree){
        if(binarySearch(key)==null){
            return null;
        }
        if(isLeaf){
            if(isRoot){
                return binarySearchRemove(key);
            }else if(isValid(tree.getOrder())){
                return binarySearchRemove(key);
            }else if(prev!=null&&prev.parent==parent&&prev.isValid(tree.getOrder())){
                Map.Entry<K, V> entry = prev.entries.remove(prev.entries.size()-1);
                entries.add(0, entry);
                int index = parent.children.indexOf(prev);
                parent.entries.set(index, entries.get(0));
                return binarySearchRemove(key);
            }else if(next!=null&&next.parent==parent&&next.isValid(tree.getOrder())){
                Map.Entry<K, V> entry = next.entries.remove(0);
                int index = parent.children.indexOf(this);
                parent.entries.set(index,next.entries.get(0));
                entries.add(entry);
                return binarySearchRemove(key);
            }else if(prev!=null&&prev.parent==parent&&!isValid(tree.getOrder())){
                V result = binarySearchRemove(key);

                 for(int i=0;i<entries.size();i++){
                     prev.entries.add(entries.get(i));
                 }
                 parent.children.remove(prev);
                 entries=prev.entries;
                 prev.parent=null;
                 prev.entries=null;

                 if(prev.prev!=null){
                     Node<K,V> temp=prev;

                     temp.prev.next=this;

                     this.prev=temp.prev;

                     //断开这两个指向
                     prev.prev=null;
                     prev.next=null;
                 }else{
                     tree.setHead(this);
                     prev.next=null;
                     prev=null;
                 }
                 //父节点中重复的数据也删除掉
                parent.entries.remove(parent.children.indexOf(this));

                if(isValid(parent.entries.size(),tree.getOrder())){
                    return result;
                }
                parent.afterRemove(tree);
                return result;

            }else if(next!=null&&next.parent==parent&&!isValid(tree.getOrder())){
                //同后面节点合并
                //当前节点直接删除key
                V result = binarySearchRemove(key);

                //将当前节点的数据添加到prev
                for(int i=0;i<entries.size();i++){
                    next.entries.add(entries.get(i));
                }
                parent.children.remove(next);
                entries=next.entries;
                next.parent=null;
                next.entries=null;

                if(next.next!=null){
                    Node<K,V> temp=next;

                    temp.next.prev=this;

                    this.next=temp.next;

                    //断开这两个指向
                    next.next=null;
                    next.prev=null;
                }else{
                    tree.setHead(this);
                    next.prev=null;
                    next=null;
                }
                //父节点中重复的数据也删除掉
                parent.entries.remove(parent.children.indexOf(this));

                //check parent

                if(isValid(parent.entries.size(),tree.getOrder())){
                    return result;
                }
                parent.afterRemove(tree);
                return result;
            }
        }
        if (key.compareTo(entries.get(0).getKey()) < 0) {
            return children.get(0).remove(key, tree);
            //如果key大于节点最右边的key，沿最后一个子节点继续搜索
        } else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
            return children.get(children.size() - 1).remove(key, tree);
            //否则沿比key大的前一个子节点继续搜索
        } else {
            int low = 0, high = entries.size() - 1, mid = 0;
            int comp;
            while (low <= high) {
                mid = (low + high) / 2;
                comp = entries.get(mid).getKey().compareTo(key);
                if (comp == 0) {
                    return children.get(mid + 1).remove(key, tree);
                } else if (comp < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return children.get(low).remove(key, tree);
        }
    }
    /**
     *
     *
     * 非叶子节点进行调整
     */

    private void afterRemove(BPlusTree<K,V> tree) {
        /**
         * 如果当前节点的关键字是合法的,直接返回
         */
        if(isValid(entries.size(),tree.getOrder())){
            return ;
        }
    }
    //判断当前节点是否可以直接删除
    private boolean isValid(int order){
        //B 树中的数据项至少要有 (Math.ceil(order/2)-1)才合法(根节点除外)
        int needMinSize= (int) (Math.ceil(order/2)-1);
        return entries.size()>needMinSize;
    }
    private boolean isValid(int size,int order){
        int needMinSize= (int) (Math.ceil(order/2)-1);
        return size>needMinSize;
    }


    /**
     * 校验这颗B+树是否合法
     * @return
     */



    private boolean isValid=true;
    public int minSize(int order){
        return ((int) Math.ceil(order/2))-1;
    }

    public int maxSize(int order) {
        return order - 1;
    }


    public boolean isValid(BPlusTree<K,V> tree){
        doIsValid(tree);
        return isValid;
    }
    public void doIsValid(BPlusTree<K,V> tree){
        if(isLeaf){
            if(isRoot){
                for(int i=0;i<entries.size()-1;i++){
                    int compare=((Comparable)entries.get(i).getKey()).compareTo(entries.get(i+1).getKey());
                    if(compare>0){
                        System.out.println("java");
                        isValid=false;
                    }
                }
            }
            else{
                if(entries.size()<minSize(tree.getOrder())||entries.size()>maxSize(tree.getOrder())){
                    System.out.println("world hello");
                    isValid=false;
                }
                for(int i=0;i<entries.size()-1;i++){
                    int compare=((Comparable)entries.get(i).getKey()).compareTo(entries.get(i+1).getKey());
                    if(compare>0){
                        System.out.println("hello world");
                        isValid=false;
                    }
                }
            }
        }
        else{
            //检查当前节点
            if(entries.size()+1!=children.size()){
                System.out.println("heeeeeeee");
                isValid=false;
            }
            if(!isRoot){
                if(entries.size()<minSize(tree.getOrder())||entries.size()>maxSize(tree.getOrder())){
                    System.out.println("heeee");
                    isValid= false;
                }
            }

            if(children.size()>maxSize(tree.getOrder())+1){
                System.out.println("hee");
                isValid= false;
            }
            for(int i=0;i<entries.size()-1;i++){
                int compare=((Comparable)entries.get(i).getKey()).compareTo(entries.get(i+1).getKey());
                if(compare>0){
                    System.out.println("he");
                    isValid= false;
                }
            }
            for(Node<K,V> node:children){
                if(node.parent!=this){
                    System.out.println("h");
                    isValid= false;
                }
            }
            for(Node node:children){
                node.doIsValid(tree);
            }
        }
    }
}