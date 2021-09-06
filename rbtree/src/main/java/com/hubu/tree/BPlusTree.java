package com.hubu.tree;
import java.util.*;
public class BPlusTree <K,V> {
    private Node<K, V> root;
    private int order;
    private int size;
    private Node<K, V> head;
    private Comparator<K> comparator;
    private boolean isValid;
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
                }
                compareResult = compare(key, node.entries.get(0).getKey());
            }catch (Exception e){
                e.printStackTrace();
            }

            if (compareResult < 0) {
                return put(node.children.get(0), key, value);
            }
            compareResult = compare(key, node.entries.get(node.entries.size() - 1).getKey());
            if (compareResult >= 0) {
                return put(node.children.get(node.children.size() - 1), key, value);
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
    private void afterPut(Node<K, V> node) {
        if(node.children.size()>order){
            Node<K,V> leftNode=new Node<>(false,false);
            Node<K,V> rightNode=new Node<>(false,false);
            int leftSize=(order+1)/2+(order+1)%2;
            int rightSize=(order+1)/2;
            for(int i=0;i<leftSize;i++){
                leftNode.children.add(node.children.get(i));
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
            }
            else{
                int index = node.parent.children.indexOf(node);
                node.parent.children.remove(node);
                leftNode.parent=node.parent;
                rightNode.parent=node.parent;
                node.parent.children.add(index,leftNode);
                node.parent.children.add(index+1,rightNode);
                node.parent.entries.add(node.entries.get(leftSize-1));
                afterPut(node.parent);
                node.parent=null;
                node.children=null;
                node.entries=null;
            }
        }
    }
    private V splitNode(Node<K, V> node,Node<K,V> leftNode,Node<K,V> rightNode,K key,V value) {
        int leftSize=(order)/2+(order)%2;
        boolean flag=false;
        V oldValue=null;
        for(int i=0;i<node.entries.size();i++){
            if(leftSize!=0){
                leftSize--;
                Map.Entry<K, V> entry = node.entries.get(i);
                int compare=compare(key,entry.getKey());
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
    public boolean contains(K key){
        checkKeyNotNull(key);
        if(root!=null){
            return contains(root,key);
        }
        return false;
    }
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
    private boolean contains(Node<K,V> node,K key){
        if(node.isLeaf){
            int low=0;
            int high=node.entries.size()-1;
            int compare=0;
            while(low<=high){
                int mid=(low+high)/2;
                compare=compare(key,node.entries.get(mid).getKey());
                if(compare>0){
                    low=mid+1;
                }
                else if(compare==0){
                    return true;
                }
                else{
                    high=mid-1;
                }
            }
            return false;
        }
        else{
            int low=0;
            int high=node.entries.size()-1;
            int compare=0;
            compare=compare(key,node.entries.get(0).getKey());
            if(compare<0){
                return contains(node.children.get(0),key);
            }
            compare=compare(key,node.entries.get(node.entries.size()-1).getKey());
            if(compare>=0){
                return contains(node.children.get(node.children.size()-1),key);
            }
            while(low<=high){
                int mid=(low+high)/2;
                compare=compare(key,node.entries.get(mid).getKey());
                if(compare>0){
                    low=mid+1;
                }
                else if(compare==0){
                    return contains(node.children.get(mid+1),key);
                }
                else{
                    high=mid-1;
                }
            }
            return contains(node.children.get(low),key);
        }
    }
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


    /**
     * 校验B+树是否合法
     *
     */
    public  boolean isValid(){
        isValid=true;
        isValid(root);
        return isValid;
    }
    private void isValid(Node<K,V> node){
        if(node.isLeaf){
            if(node.isRoot){
                for(int i=0;i<node.entries.size()-1;i++){
                    int compare=compare(node.entries.get(i).getKey(),node.entries.get(i+1).getKey());
                    if(compare>0){
                        isValid=false;

                    }
                }
            }
            else{
                if(node.entries.size()<minSize(order)||node.entries.size()>maxSize(order)){
                    isValid=false;

                }
                for(int i=0;i<node.entries.size()-1;i++){
                    int compare=compare(node.entries.get(i).getKey(),node.entries.get(i+1).getKey());
                    if(compare>0){

                        isValid=false;

                    }
                }
            }
        }
        else{
            //检查当前节点
            if(node.entries.size()+1!=node.children.size()){
                isValid=false;

            }
            if(!node.isRoot){
                if(node.entries.size()<minSize(order)||node.entries.size()>maxSize(order)){
                    isValid= false;
                }
            }

            if(node.children.size()>maxSize(order+1)){

                isValid= false;
            }
            for(int i=0;i<node.entries.size()-1;i++){
                int compare=compare(node.entries.get(i).getKey(),node.entries.get(i+1).getKey());
                if(compare>0){

                    isValid= false;
                }
            }

            for(Node<K,V> item:node.children){
                if(item.parent!=node){
                    isValid=false;
                    break;
                }
            }
            /**
             *
             *
             * 递归检查所有的子节点
             */
            for(Node item:node.children){
                isValid(item);
            }
        }
    }
    private int minSize(int order){
        return ((int) Math.ceil(order/2))-1;
    }
    private int maxSize(int order) {
        return order - 1;
    }
    public void levelOrder(){
        if(head!=null){
            Node<K,V> current=head;
            while(current!=null){
                for(Map.Entry<K,V> item:current.entries){
                    System.out.print(item.getKey()+"============>"+item.getValue()+" ");
                }
                System.out.println();
                current=current.next;
            }
        }
    }
    public V remove(K key){
        checkKeyNotNull(key);
        if(this.root ==null) return null;
        return remove(root,key);
    }
    private V remove(Node<K,V> node,K key){
        /**
         * 如果不包含key，直接返回
         */
        if(!contains(key)){
            return null;
        }
        if(node.isLeaf) {
            if(node.isRoot||canRemoveBySelf(node)){
                V result=remove0(node,key);
                return result;
            }
            //如果能够从前面节点借一个过来
            if(node.prev!=null&&node.prev.parent==node.parent&&canRemoveByNode(node.prev)) {
                //删除node前驱节点中的最后一个值
                Map.Entry<K, V> prevEntry = node.prev.entries.remove(node.prev.entries.size() - 1);
                //获取当前节点在父亲子节点中的位置
                V result = remove0(node, key);
                int parentIndex = node.parent.children.indexOf(node.prev);
                node.parent.entries.set(parentIndex,prevEntry);
                //将前驱节点移除的元素添加到当前节点的第0个位置
                node.entries.add(0, prevEntry);
                return result;
            }
            if(node.next!=null&&node.next.parent==node.parent&&canRemoveByNode(node.next)){
                Map.Entry<K, V> nextEntry= node.next.entries.remove(0);
                V result=remove0(node,key);
                node.entries.add(nextEntry);
                int index = node.parent.children.indexOf(node);
                /**
                 * 删除当前key在父节点中的位置
                 */
                node.parent.entries.set(index,node.next.entries.get(0));
                return result;
            }
            if(node.prev!=null&&canMergeWithNode(node,node.prev)){
                //核心就是将当前节点的前一个节点删除掉
                for(int i=0;i<node.entries.size();i++){
                    node.prev.entries.add(node.entries.get(i));
                }
                node.entries=node.prev.entries;
                node.prev.entries=null;
                node.prev.parent=null;
                node.parent.children.remove(node.prev);
                //处理连接关系
                if(node.prev.prev!=null){
                    Node<K,V> temp=node.prev;
                    node.prev.prev.next=node;
                    node.prev=node.prev.prev;
                    temp.prev=null;
                    temp.next=null;
                }else{
                    head=node;
                    node.prev.next=null;
                    node.prev=null;
                }
                V result=remove0(node,key);
                node.parent.entries.remove(node.parent.children.indexOf(node));
                afterRemove(node.parent);
                return result;
            }
            if(node.next!=null&&canMergeWithNode(node,node.next)){
                for(int i=0;i<node.next.entries.size();i++){
                    node.entries.add(node.next.entries.get(i));
                }
                node.next.entries=null;
                node.next.parent=null;
                node.parent.children.remove(node.next);
                //处理连接关系
                if (node.next.next != null) {
                    Node<K, V> temp = node.next;
                    node.next.next.prev=node;
                    node.next=node.next.next;
                    temp.prev = null;
                    temp.next = null;
                } else {
                    node.next.prev = null;
                    node.next = null;
                }
                V result=remove0(node,key);
                node.parent.entries.remove(node.parent.children.indexOf(node));
                afterRemove(node.parent);
                return result;
            }
        }
        int compare=0;
        compare=compare(key,node.entries.get(0).getKey());
        if(compare<0){
            return remove(node.children.get(0),key);
        }
        compare=compare(key,node.entries.get(node.entries.size()-1).getKey());
        if(compare>0){
            return remove(node.children.get(node.children.size()-1),key);
        }
        int low=0;
        int mid=0;
        int high=node.entries.size()-1;
        while(low<=high){
            mid=(low+high)/2;
            compare=compare(key,node.entries.get(mid).getKey());
            if(compare>0){
                low=mid+1;
            }
            else if(compare==0){
                return remove(node.children.get(mid+1),key);
            }
            else{
                high=mid-1;
            }
        }
        return remove(node.children.get(low),key);
    }
    private int minChildSize(int order){
        return (int)Math.ceil(order/2);
    }
    private void afterRemove(Node<K,V> node){
        //递归向上处理父节点的条件 孩子节点的数量小于阶/2向上取整
        if(node==null){
            return ;
        }
        if(node.children.size()<minChildSize(order)||node.children.size()<2){
            /**
             * 如果是根节点
             */
            if(node.isRoot){
                if(node.children.size()>=2){
                    return ;
                }
                Node<K,V> newRoot=node.children.get(0);
                newRoot.parent=null;
                newRoot.isRoot=true;
                node.children=null;
                node.parent=null;
                node.entries=null;
                root=newRoot;
            }
            else{
                //计算node节点的左后节点
                int currentIndex= node.parent.children.indexOf(node);
                int prevIndex=currentIndex-1;
                int nextIndex=currentIndex+1;
                Node<K,V> prev=null;
                Node<K,V> next=null;
                if(prevIndex>=0){
                    prev=node.parent.children.get(prevIndex);
                }
                if(nextIndex<node.parent.children.size()){
                    next=node.parent.children.get(nextIndex);
                }
                if(prev!=null&&prev.parent==node.parent&&canRemoveByNode(node)){
                    int index = prev.children.size() - 1;
                    Node<K, V> borrow = prev.children.remove(index);
                    borrow.parent = node;
                    node.children.add(0, borrow);
                    int parentIndex =node.parent.children.indexOf(prev);
                    node.entries.add(0, node.parent.entries.get(parentIndex));
                    node.parent.entries.set(parentIndex, prev.entries.remove(index - 1));
                    return;
                }
                else if (next != null&&next.parent==node.parent&&canRemoveByNode(node)) {
                    //后叶子节点首位添加到末尾
                    Node<K, V> borrow = next.children.get(0);
                    next.children.remove(0);
                    borrow.parent = node;
                    node.children.add(borrow);
                    int preIndex = node.parent.children.indexOf(this);
                    node.entries.add(node.parent.entries.get(preIndex));
                    node.parent.entries.set(preIndex, next.entries.remove(0));
                    return;
                }
                else if (prev != null
                        &&canMergeWithNode(node,prev)) {
                    for (int i = 0; i < node.children.size(); i++) {
                        prev.children.add(node.children.get(i));
                    }
                    for (int i = 0; i < prev.children.size(); i++) {
                        prev.children.get(i).parent = node;
                    }
                    int indexPre = node.parent.children.indexOf(prev);
                    prev.entries.add(node.parent.entries.get(indexPre));
                    for (int i = 0; i < node.entries.size(); i++) {
                        prev.entries.add(node.entries.get(i));
                    }
                    node.children = prev.children;
                    node.entries = prev.entries;

                    //更新父节点的关键字列表
                    node.parent.children.remove(prev);
                    prev.parent = null;
                    prev.children = null;
                    prev.entries = null;
                    node.parent.entries.remove(node.parent.children.indexOf(node));
                    afterRemove(node.parent);
                    return;
                }

                // 同后面节点合并
                else if (next != null&&next.parent==node.parent) {
                    for (int i = 0; i < next.children.size(); i++) {
                        Node<K, V> child = next.children.get(i);
                        node.children.add(child);
                        child.parent = node;
                    }
                    int index = node.parent.children.indexOf(node);
                    node.entries.add(node.parent.entries.get(index));
                    for (int i = 0; i < next.entries.size(); i++) {
                        node.entries.add(next.entries.get(i));
                    }
                    node.parent.children.remove(next);
                    next.parent = null;
                    next.children = null;
                    next.entries = null;
                    node.parent.entries.remove(index);
                    afterRemove(node.parent);
                    return;
                }
                else if(prev==null&&next==null){
                    System.out.println("prev=null and next=null");
                }
            }
        }
    }


    /**
     * 判断当前节点是否能和node节点合并
     * @param current
     * @param node
     * @return
     */
    private boolean canMergeWithNode(Node<K,V> current,Node<K,V> node){


        /**
         * 合并后的节点数量可以等于order，合并之后还会删除一个
         */

        if(node==null){

        }

        if(node.parent==current.parent&&current.entries.size()+node.entries.size()<=order){
            return true;
        }
        return false;
    }

    private boolean canRemoveByNode(Node<K, V> node) {
        /**
         * 每个节点元素的数量最少是阶/2向上取整-1 所以node可以删除的条件是node里面元素的数量必须大于阶/2向上取整-1
         *
         */
        int minNeedSize=(int)(Math.ceil(order/2))-1;
        return node.entries.size()>minNeedSize;
    }


    /**
     * 判断是否能够自己字节删除
     * @param node
     * @return
     */
    private boolean canRemoveBySelf(Node<K,V> node){
        return canRemoveByNode(node);
    }


    /**
     * 叶子节点的删除，只负责叶子节点的删除逻辑，不进行判断等操作，能来到这儿的就是合法的操作
     * @param key
     * @return
     */
    private V remove0(Node<K,V> node,K key){
        int low=0;
        int mid=0;
        int high=node.entries.size()-1;
        int compare=0;
        while(low<=high){
            mid=(low+high)/2;
            compare=compare(key,node.entries.get(mid).getKey());
            if(compare>0){
                low=mid+1;
            }
            else if(compare==0){
                return node.entries.remove(mid).getValue();
            }
            else{
                high=mid-1;
            }
        }
        return null;
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
            if(!isLeaf){
                this.children=new ArrayList<>();
            }
        }
    }
}
