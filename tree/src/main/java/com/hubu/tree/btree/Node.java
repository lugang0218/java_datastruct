package com.hubu.tree.btree;
import java.util.*;
class Node<K extends Comparable<K>, V> {
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



        protected void updateInsert(BPTree<K, V> tree) {
        if (children.size() > tree.getOrder()) {
           Node<K, V> left = new Node<>(false);
           Node<K, V> right = new Node<K, V>(false);
            int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2;
            int rightSize = (tree.getOrder() + 1) / 2;
            for (int i = 0; i < leftSize; i++) {
                left.children.add(children.get(i));
                children.get(i).parent = left;
            }
            for (int i = 0; i < rightSize; i++) {
                right.children.add(children.get(leftSize + i));
                children.get(leftSize + i).parent = right;
            }
            for (int i = 0; i < leftSize - 1; i++) {
                left.entries.add(entries.get(i));
            }
            for (int i = 0; i < rightSize - 1; i++) {
                right.entries.add(entries.get(leftSize + i));
            }
            if (parent != null) {
                int index = parent.children.indexOf(this);
                parent.children.remove(this);
                left.parent = parent;
                right.parent = parent;
                parent.children.add(index, left);
                parent.children.add(index + 1, right);
                parent.entries.add(index, entries.get(leftSize - 1));
                entries = null;
                children = null;
                //递归调整父节点
                parent.updateInsert(tree);
                parent = null;
            } else {
                isRoot = false;
                Node<K, V> parent = new Node<K, V>(false, true);
                tree.setRoot(parent);
                left.parent = parent;
                right.parent = parent;
                parent.children.add(left);
                parent.children.add(right);
                parent.entries.add(entries.get(leftSize - 1));
                entries = null;
                children = null;
            }
        }
    }
    public void  put(K key, V value, BPTree<K, V> tree) {
        if (isLeaf) {
            //如果节点的数量小于order,表示还可以添加
            if (entries.size() < tree.getOrder()) {
                 binarySearchPut(key, value);
                 return ;
            }
            Node<K, V> leftNode = new Node<K, V>(true, false);
            Node<K, V> rightNode = new Node<K, V>(true, false);
            AbstractMap.SimpleEntry<K, V> newEntry = new AbstractMap.SimpleEntry<K, V>(key, value);
            splitLeftAndRight(leftNode, rightNode, newEntry,tree.getOrder());
            if (parent != null) {
                    //获取当前节点在父节点中的索引
                int index = parent.children.indexOf(this);
                parent.children.remove(this);
                leftNode.parent = parent;
                rightNode.parent = parent;
                parent.children.add(index, leftNode);
                parent.children.add(index + 1, rightNode);
                parent.entries.add(rightNode.entries.get(0));
                this.parent = null;
                this.entries = null;
                this.children = null;
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


        private V binarySearchPut(K key, V value){
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
            entries.add(low,newEntry);
        }
        return null;
    }
    private void  splitLeftAndRight(Node<K, V> leftNode, Node<K, V> rightNode,
                                    AbstractMap.SimpleEntry<K, V> entry,
                                    int total) {
        int leftSize=((total)+1)/2+(total+1)%2;
        boolean flag=false;;
        for(int i=0;i<entries.size();i++){
            if(leftSize!=0){
                leftSize--;
                if(!flag){
                    int compare=((Comparable)entry.getKey()).compareTo(entries.get(i).getKey());
                    if(compare<0){
                        leftNode.entries.add(entry);
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
        }
    }
}