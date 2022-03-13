package com.hubu.tree.bplustree;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
public class BPageTree<K,V>{
    private int order;
    private Comparator<K> comparator;
    private Page<K,V> root=null;
    private Page<K,V> head=null;
    private int size;
    private boolean isValid=true;
    public BPageTree(int order, Comparator<K> comparator){
        this.order=order;
        this.comparator=comparator;
    }
    public BPageTree(int order){
        this(order,null);
    }
    public BPageTree(Comparator<K> comparator){
        this(3,comparator);
    }
    public BPageTree() {
        this(3, null);
    }
    public V put(K key, V value){
        Page<K,V> page =root;
        if(page ==null){
            root=new Page<>(true,true);
            page =root;
        }
        return put(page,key,value);
    }

    private boolean isFull(Page<K,V> page){
        return page.entryList.size()== order;
    }
    private V put(Page<K,V> page, K key, V value){
        if(page.isLeaf){
            //先一个之后到满 后分裂
            V result=add0(page,key,value);
            if(!isFull(page)){
                return result;
            }
            //左边元素个数等于总的个数除以2向下取整
            int leftSize=order/2;
            //右边数等于总的个数除以2向上取整
            int rightSize=(int)(Math.ceil((double) order/2));
            Page<K,V> leftPage =new Page(false,true);
            Page<K,V> rightPage =new Page<>(false,true);
            for(int i=0;i<leftSize;i++){
                leftPage.entryList.add(page.entryList.get(i));
            }
            for(int j=0;j<rightSize;j++){
                rightPage.entryList.add(page.entryList.get(leftSize+j));
            }
            Page<K,V> newParent= page.parent;
            int index=0;
            leftPage.next= rightPage;
            rightPage.prev= leftPage;
            Page<K,V> temp= page;
            if(page.prev==null){
                head= leftPage;
            }
            else{
                leftPage.prev= page.prev;
                page.prev.next= leftPage;
            }
            //这一步必须要这么做才可以

            rightPage.next= page.next;
            if(page.next!=null) {
                page.next.prev = rightPage;
            }
            temp.prev=null;
            temp.next=null;
            if(newParent==null){
                newParent = new Page<>(true, false);
                root = newParent;
            }
            else{
                index= page.parent.children.indexOf(page);
                page.parent.children.remove(page);
            }
            leftPage.parent = newParent;
            rightPage.parent = newParent;
            newParent.children.add(index, leftPage);
            newParent.children.add(index+1, rightPage);
            //不能直接添加，需要有序添加
            //把这个entry添加到父节点
            Map.Entry<K,V> entry= rightPage.entryList.get(0);
            addEntryToParent(newParent,entry.getKey(),entry);
            afterPut(page.parent);
            page.children=null;
            page.entryList=null;
            page.parent=null;
            return result;
        }
        int compareResult=0;
        compareResult=compare(key, page.entryList.get(0).getKey());
        if(compareResult<0){
            return put(page.children.get(0),key,value);
        }
        compareResult=compare(key, page.entryList.get(page.entryList.size()-1).getKey());
        if(compareResult>=0){
            return put(page.children.get(page.children.size()-1),key,value);
        }
        int low=0;
        int mid=0;

        int high= page.entryList.size()-1;
        while(low<=high){
            mid=(low+high)/2;
            compareResult=compare(key, page.entryList.get(mid).getKey());
            if(compareResult>0){
                low=mid+1;
            }
            else if(compareResult==0){
                return put(page.children.get(mid+1),key,value);
            }
            else{
                high=mid-1;
            }
        }
        return put(page.children.get(low),key,value);
    }
    private void afterPut(Page<K, V> page) {
        if(page !=null&& page.children.size()>order){
            int leftSize=(int)Math.ceil((double) page.children.size()/2);
            int rightSize= page.children.size()/2;
            Page<K,V> leftPage =new Page<>(false,false);
            Page<K,V> rightPage =new Page<>(false,false);
            for(int i=0;i<leftSize;i++){
                leftPage.children.add(page.children.get(i));
                page.children.get(i).parent= leftPage;
            }
            for(int i=0;i<rightSize;i++){
                rightPage.children.add(page.children.get(leftSize+i));
                page.children.get(leftSize+i).parent= rightPage;
            }
            for(int i=0;i<leftSize-1;i++){
                leftPage.entryList.add(page.entryList.get(i));
            }
            for(int i=0;i<rightSize-1;i++){
                rightPage.entryList.add(page.entryList.get(leftSize+i));
            }

            Page<K,V> newParent= page.parent;
            int index=0;
            if(newParent==null){
                newParent=new Page(true,false);
                root=newParent;
            }
            else {
                index=newParent.children.indexOf(page);
                newParent.children.remove(page);
            }
            leftPage.parent=newParent;
            rightPage.parent=newParent;
            newParent.children.add(index, leftPage);
            newParent.children.add(index+1, rightPage);
            Map.Entry<K,V> entry= page.entryList.get(leftSize-1);
            addEntryToParent(newParent,entry.getKey(),entry);
            afterPut(page.parent);
            page.entryList=null;
            page.parent=null;
            page.children=null;
        }
    }

    private void addEntryToParent(Page<K,V> page, K key, Map.Entry<K,V> entry){
        int low=0;
        int high= page.entryList.size()-1;
        int compareResult=0;

        while(low<=high){
            int mid=(low+high)/2;
            compareResult=compare(key, page.entryList.get(mid).getKey());
            if(compareResult>0){
                low=mid+1;
            }
            else{
                high=mid-1;
            }
        }
        page.entryList.add(low,entry);
    }
    public V remove(K key){
        if(root==null) return null;
        checkKeyNotNull(key);
        return remove(root,key);
    }
    private boolean canRemoveBySelf(Page<K,V> self){
        if(self==null) return false;
        //最小需要的节点数是order/2向上取整-1
        int minSize=((int)Math.ceil((double)order/2))-1;
        return self.entryList.size()>minSize;
    }
    private boolean canBorrowFromNode(Page<K,V> page){
        if(page ==null) return false;
        int minSize=((int)Math.ceil((double)order/2))-1;
        return page.entryList.size()>minSize;
    }
    private boolean canMerge(Page<K,V> page1, Page<K,V> page2){
        if(page1 ==null|| page2 ==null){
            return false;
        }
        return page1.entryList.size()+ page2.entryList.size()<=order-1;
    }
    private V remove(Page<K,V> page, K key){
        if(page ==null) {
            System.out.println("hello world");
            return null;
        }
        else if(page.isLeaf){
            /**
             *
             * 自己能够删除的时候，需要删除父节点一样的值，否则会出现自己删除了，但父节点还保留一份
             */
            if(page.isRoot||canRemoveBySelf(page)){
                V v = remove0(page, key);
                if(v.equals(961)){
                    System.out.println("haha");
                }
                Page<K,V> parent= page.parent;

                if(parent!=null&&v!=null){
                    int index=parent.children.indexOf(page);
                    int parentIndex=index-1;
                    if(parent.entryList!=null&&parent.entryList.size()>0){
                        if(parentIndex>=0&&parentIndex<parent.entryList.size()){
                            Map.Entry<K, V> entry = parent.entryList.get(parentIndex);
                            //如果自己能够删除，并且是第一个，需要用现在的第一个节点去替换掉父节点中相等的值
                            if(entry.getKey().equals(key)){
                                Map.Entry<K, V> entry1 = page.entryList.get(0);
                                parent.entryList.set(parentIndex,entry1);
                            }
                        }
                    }
                }
                if(v==null){
                    System.out.println("hello null");
                }
                return v;
            }
            if(page.prev!=null&& page.prev.parent== page.parent&&canBorrowFromNode(page.prev)){
                int index= page.parent.children.indexOf(page.prev);
                Map.Entry<K, V> prevEntry = page.prev.entryList.remove(page.prev.entryList.size() - 1);
                V result=remove0(page,key);
                if(result==null){
                    System.out.println("world null");
                }
                page.entryList.add(0,prevEntry);
                page.parent.entryList.set(index,prevEntry);
                return result;
            }
            if(page.next!=null&& page.next.parent== page.parent&&canBorrowFromNode(page.next)){
                int index= page.parent.children.indexOf(page);
                Map.Entry<K, V> nextEntry= page.next.entryList.remove(0);
                V result=remove0(page,key);
                page.entryList.add(nextEntry);
                page.parent.entryList.set(index, page.next.entryList.get(0));
                return result;
            }
            if(page.prev!=null&& page.prev.parent== page.parent&&canMerge(page.prev, page)){
                for(int i = 0; i< page.entryList.size(); i++){
                    page.prev.entryList.add(page.entryList.get(i));
                }
                page.entryList= page.prev.entryList;
                page.prev.entryList=null;
                page.prev.parent=null;
                page.parent.children.remove(page.prev);
                if(page.prev.prev!=null){
                    Page<K,V> temp= page.prev;
                    page.prev.prev.next= page;
                    page.prev= page.prev.prev;
                    temp.prev=null;
                    temp.next=null;
                }else{
                    head= page;
                    page.prev.next=null;
                    page.prev=null;
                }
                V result=remove0(page,key);
                if(result==null){
                    System.out.println("world3 null");
                }
                int index= page.parent.children.indexOf(page);
                page.parent.entryList.remove(index);
                afterRemove(page.parent);
                return result;
            }
            if(page.next!=null&& page.next.parent== page.parent&&canMerge(page.next, page)){
                for(int i = 0; i< page.next.entryList.size(); i++){
                    page.entryList.add(page.next.entryList.get(i));
                }
                page.next.entryList=null;
                page.next.parent=null;
                page.parent.children.remove(page.next);

                if (page.next.next != null) {
                    Page<K, V> temp = page.next;
                    page.next.next.prev= page;
                    page.next= page.next.next;
                    temp.prev = null;
                    temp.next = null;
                } else {
                    page.next.prev = null;
                    page.next = null;
                }
                V result=remove0(page,key);
                if(result==null){
                    System.out.println("world4 null");
                }
                int index= page.parent.children.indexOf(page);
                page.parent.entryList.remove(index);
                afterRemove(page.parent);
                return result;
            }
        }
        int compareResult=0;
        compareResult=compare(key, page.entryList.get(0).getKey());
        if(compareResult<0){
            return remove(page.children.get(0),key);
        }
        compareResult=compare(key, page.entryList.get(page.entryList.size()-1).getKey());
        if(compareResult>=0){
            return remove(page.children.get(page.children.size()-1),key);
        }

        int low=0;
        int high= page.entryList.size()-1;
        int mid=0;
        while(low<=high){
            mid=(low+high)/2;
            compareResult=compare(key, page.entryList.get(mid).getKey());
            if(compareResult>0){
                low=mid+1;
            }
            else if(compareResult==0){
                return remove(page.children.get(mid+1),key);
            }
            else{
                high=mid-1;
            }
        }
        return remove(page.children.get(low),key);
    }
    private int minChildSize(int order){
        return (int)Math.ceil((double) order/2);
    }
    private void afterRemove(Page<K,V> page){
        //递归向上处理父节点的条件 孩子节点的数量小于阶/2向上取整
        if(page ==null){
            return ;
        }
        if(page.children.size()<minChildSize(order)|| page.children.size()<2){
            /**
             * 如果是根节点
             */
            if(page.isRoot){
                if(page.children.size()>=2){
                    return ;
                }
                Page<K,V> newRoot= page.children.get(0);
                newRoot.parent=null;
                newRoot.isRoot=true;
                page.children=null;
                page.parent=null;
                page.entryList=null;
                root=newRoot;
            }
            else{
                //计算node节点的左后节点
                int currentIndex= page.parent.children.indexOf(page);
                int prevIndex=currentIndex-1;
                int nextIndex=currentIndex+1;
                Page<K,V> prev=null;
                Page<K,V> next=null;
                if(prevIndex>=0){
                    prev= page.parent.children.get(prevIndex);
                }
                if(nextIndex< page.parent.children.size()){
                    next= page.parent.children.get(nextIndex);
                }
                if(prev!=null&&prev.parent== page.parent&&canRemoveBySelf(page)){
                    int index = prev.children.size() - 1;
                    Page<K, V> borrow = prev.children.remove(index);
                    borrow.parent = page;
                    page.children.add(0, borrow);
                    int parentIndex = page.parent.children.indexOf(prev);
                    page.entryList.add(0, page.parent.entryList.get(parentIndex));
                    page.parent.entryList.set(parentIndex, prev.entryList.remove(index - 1));
                    return;
                }
                else if (next != null&&next.parent== page.parent&&canRemoveBySelf(page)) {
                    //后叶子节点首位添加到末尾
                    Page<K, V> borrow = next.children.get(0);
                    next.children.remove(0);
                    borrow.parent = page;
                    page.children.add(borrow);
                    int preIndex = page.parent.children.indexOf(this);
                    page.entryList.add(page.parent.entryList.get(preIndex));
                    page.parent.entryList.set(preIndex, next.entryList.remove(0));
                    return;
                }
                else if (prev != null
                        &&canMerge(page,prev)) {
                    for (int i = 0; i < page.children.size(); i++) {
                        prev.children.add(page.children.get(i));
                    }
                    for (int i = 0; i < prev.children.size(); i++) {
                        prev.children.get(i).parent = page;
                    }
                    int indexPre = page.parent.children.indexOf(prev);
                    prev.entryList.add(page.parent.entryList.get(indexPre));
                    for (int i = 0; i < page.entryList.size(); i++) {
                        prev.entryList.add(page.entryList.get(i));
                    }
                    page.children = prev.children;
                    page.entryList = prev.entryList;

                    //更新父节点的关键字列表
                    page.parent.children.remove(prev);
                    prev.parent = null;
                    prev.children = null;
                    prev.entryList= null;
                    page.parent.entryList.remove(page.parent.children.indexOf(page));
                    afterRemove(page.parent);
                    return;
                }

                // 同后面节点合并
                else if (next != null&&next.parent== page.parent) {
                    for (int i = 0; i < next.children.size(); i++) {
                        Page<K, V> child = next.children.get(i);
                        page.children.add(child);
                        child.parent = page;
                    }
                    int index = page.parent.children.indexOf(page);
                    page.entryList.add(page.parent.entryList.get(index));
                    for (int i = 0; i < next.entryList.size(); i++) {
                        page.entryList.add(next.entryList.get(i));
                    }
                    page.parent.children.remove(next);
                    next.parent = null;
                    next.children = null;
                    next.entryList = null;
                    page.parent.entryList.remove(index);
                    afterRemove(page.parent);
                    return;
                }
                else if(prev==null&&next==null){
                    System.out.println("prev=null and next=null");
                }
            }
        }
    }

    public int size() {
        return size;
    }
    private void checkKeyNotNull(K key){
        if(key==null) throw new RuntimeException("key must not be null");
    }
    private boolean isNotFull(Page<K,V> page){
        return page.entryList.size()<order-1;
    }
    private V add0(Page<K,V> page, K key, V value){
        int low=0;
        int high= page.entryList.size()-1;
        int compareResult=0;
        while(low<=high){
            int mid=(low+high)/2;
            compareResult=compare(key, page.entryList.get(mid).getKey());
            if(compareResult>0){
                low=mid+1;
            }
            else if(compareResult==0){
                V oldValue= page.entryList.get(mid).getValue();
                page.entryList.get(mid).setValue(value);
                return oldValue;
            }
            else{
                high=mid-1;
            }
        }
        size++;
        page.entryList.add(low,new AbstractMap.SimpleEntry<K,V>(key,value));
        return null;
    }
    public V get(K key){
        if(root==null) return null;
        return search(root,key);
    }
    public V search(Page<K,V> page, K key){
        if(page ==null) return null;
        if(page.isLeaf){
            int low=0;
            int high= page.entryList.size()-1;
            int compareResult=0;
            while(low<=high){
                int mid=(low+high)/2;
                compareResult=compare(key, page.entryList.get(mid).getKey());
                if(compareResult>0){
                    low=mid+1;
                }
                else if(compareResult==0){
                    return page.entryList.get(mid).getValue();
                }
                else{
                    high=mid-1;
                }
            }
            return null;
        }
        else{
            int compareResult=0;
            compareResult=compare(key, page.entryList.get(0).getKey());
            if(compareResult<0){
                return search(page.children.get(0),key);
            }
            compareResult=compare(key, page.entryList.get(page.entryList.size()-1).getKey());
            if(compareResult>=0){
                return search(page.children.get(page.children.size()-1),key);
            }
            int low=0;
            int high= page.entryList.size()-1;
            int mid=0;
            while(low<=high){
                mid=(low+high)/2;
                compareResult=compare(key, page.entryList.get(mid).getKey());
                if(compareResult>0){
                    low=mid+1;
                }
                else if(compareResult==0){
                    return search(page.children.get(mid+1),key);
                }
                else{
                    high=mid-1;
                }
            }
            return search(page.children.get(low),key);
        }
    }
    private V remove0(Page<K,V> page, K key){
        if(page ==null) return null;
        int compareResult=0;
        int low=0;
        int mid=0;
        int high= page.entryList.size()-1;
        while(low<=high){
            mid=(low+high)/2;
            compareResult=compare(key, page.entryList.get(mid).getKey());
            if(compareResult>0){
                low=mid+1;
            }
            else if(compareResult==0){
                return page.entryList.remove(mid).getValue();
            }
            else{
                high=mid-1;
            }
        }
        return null;
    }
    private int compare(K key1,K key2){
        return comparator!=null?comparator.compare(key1,key2):((Comparable)key1).compareTo(key2);
    }
    private int minSize(int order){
        return ((int) Math.ceil(order/2))-1;
    }
    private int maxSize(int order) {
        return order - 1;
    }
    public  boolean isValid() {
        isValid = true;
        isValid(root);
        return isValid;
    }
    public boolean isBPlusTree(){
        return isBPlusTree(root);
    }

    /**
     *
     * 递归检查该树是否满足B+树的条件
     * @param page
     * @return
     */
    private boolean isBPlusTree(Page<K,V> page){
        if(page ==null) return false;
        if(page.isLeaf){
            if(page.isRoot){
                for(int i = 0; i< page.entryList.size()-1; i++){
                    int compare=compare(page.entryList.get(i).getKey(), page.entryList.get(i+1).getKey());
                    if(compare>0) {
                        return false;
                    }
                }
                return true;
            }
            else{
                if(page.entryList.size()<minSize(order)|| page.entryList.size()>maxSize(order)){
                    return false;
                }
                for(int i = 0; i< page.entryList.size()-1; i++){
                    int compare=compare(page.entryList.get(i).getKey(), page.entryList.get(i+1).getKey());
                    if(compare>0){

                        isValid=false;

                    }
                }
                return true;
            }
        }
        else{
            if(page.entryList.size()+1!= page.children.size()){
                return false;
            }
            if(!page.isRoot){
                if(page.entryList.size()<minSize(order)|| page.entryList.size()>maxSize(order)){
                    return false;
                }
            }
            if(page.children.size()>maxSize(order+1)){
                return false;
            }
            for(int i = 0; i< page.entryList.size()-1; i++){
                int compare=compare(page.entryList.get(i).getKey(), page.entryList.get(i+1).getKey());
                if(compare>0){
                    return false;
                }
            }

            for(Page<K,V> item: page.children){
                if(item.parent!= page){
                    return false;
                }
            }
            /**
             * 递归检查所有的子节点
             */
            for(Page item: page.children){
                if(!isBPlusTree(item)){
                    return false;
                }
            }
            return true;
        }
    }
    private void isValid(Page<K,V> page){
        if(page.isLeaf){
            if(page.isRoot){
                for(int i = 0; i< page.entryList.size()-1; i++){
                    int compare=compare(page.entryList.get(i).getKey(), page.entryList.get(i+1).getKey());
                    if(compare>0){
                        isValid=false;
                    }
                }
            }
            else{
                if(page.entryList.size()<minSize(order)|| page.entryList.size()>maxSize(order)){
                    isValid=false;
                }
                for(int i = 0; i< page.entryList.size()-1; i++){
                    int compare=compare(page.entryList.get(i).getKey(), page.entryList.get(i+1).getKey());
                    if(compare>0){

                        isValid=false;

                    }
                }
            }
        }
        else{
            //检查当前节点
            if(page.entryList.size()+1!= page.children.size()){
                isValid=false;
            }
            if(!page.isRoot){
                if(page.entryList.size()<minSize(order)|| page.entryList.size()>maxSize(order)){
                    isValid= false;
                }
            }
            if(page.children.size()>maxSize(order+1)){

                isValid= false;
            }
            for(int i = 0; i< page.entryList.size()-1; i++){
                int compare=compare(page.entryList.get(i).getKey(), page.entryList.get(i+1).getKey());
                if(compare>0){
                    System.out.println("error number");
                    isValid= false;
                    break;
                }
            }
            for(Page<K,V> item: page.children){
                if(item.parent!= page){
                    isValid=false;
                    break;
                }
            }
            /**
             * 递归检查所有的子节点
             */
            for(Page item: page.children){
                isValid(item);
            }
        }
    }
}