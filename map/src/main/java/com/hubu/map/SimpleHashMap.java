package com.hubu.map;
/**
 * jdk7的实现
 */
public class SimpleHashMap<K,V> {
    private static final float DEFAULT_LOAD_FACTOR=0.75F;
    private static final int DEFAULT_CAPACITY=16;
    private static final Integer MAX_CAPACITY_VALUE=Integer.MAX_VALUE-3;
    private int capacity;
    private float loadFactor;
    private int size;
    /**
     * 阈值
     */
    private int threshold=0;
    //初始化table
    private Entry<K,V> [] table =null;
    public SimpleHashMap(){
        this(DEFAULT_CAPACITY,DEFAULT_LOAD_FACTOR);
    }
    public SimpleHashMap(int capacity, float loadFactor){
        if(capacity<0){
            throw new RuntimeException("capacity can not less than zero");
        }
        else if(loadFactor<=0){
            throw new RuntimeException("loadFactor can not less than zero or equal zero");
        }
        else if(capacity>MAX_CAPACITY_VALUE){
            capacity=MAX_CAPACITY_VALUE;
        }
        this.capacity=capacity;
        this.loadFactor=loadFactor;
        //计算阈值
        this.threshold=(int)(loadFactor*this.capacity);
    }

    public V get(K key){
        if(size==0){
            return null;
        }
        Entry<K, V> entry = getEntry(key);
        return entry==null?null:entry.value;
    }
    private Entry<K,V> getEntry(K key){
        if(size==0){
            return null;
        }
        int index = indexFor(hash(key), table.length);
        Entry<K,V> entry=table[index];
        while(entry!=null){
            if(entry.key==key||entry.key.equals(key)){
                return entry;
            }
            entry=entry.next;
        }
        return null;
    }


     Entry<K,V> removeEntryForKey(K  key) {
        if (size == 0) {
            return null;
        }
        int hash = (key == null) ? 0 : hash(key);
        int i = indexFor(hash, table.length);
        Entry<K,V> prev = table[i];
        Entry<K,V> e = prev;
        while (e != null) {
            Entry<K,V> next = e.next;
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k)))) {
                size--;
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                return e;
            }
            prev = e;
            e = next;
        }

        return e;
    }

    public V remove(K key){
        if(size==0){
            return null;
        }
        Entry<K, V> entry = removeEntryForKey(key);
        return entry==null?null:entry.value;
    }
    public V put(K key,V value){
        if(key==null){
            throw new RuntimeException("key can not be null");
        }
        int hash = hash(key);
        int index=indexFor(hash,capacity);
        if(table ==null){
            table =new Entry[this.capacity];
        }
        //检验相等的情况
        for(Entry<K,V> entry = table[index]; entry!=null; entry=entry.next){
            //如果hash值和key都相等或则key调用equals相等
            if(entry.hash==hash&&entry.key==key||entry.key.equals(key)){
                V oldValue= entry.value;
                entry.key=key;
                entry.value=value;
                entry.hash=hash;
                return oldValue;
            }
        }
        addEntry(hash,key,value,index);
        return null;//没有替代返回原来的值
    }
    //添加一个Entry
    void addEntry(int hash,K key,V value,int bucketIndex){
        //添加的过程中如果size>=阈值并且当前桶上还不为空，应该马上添加
        if(size>=threshold&&table[bucketIndex]!=null){
            reSize(2*this.capacity);
        }
        createEntry(hash,key,value,bucketIndex);
        size++;
    }
    private void reSize(int newCapacity) {
        int oldCapacity=this.capacity;
        if(newCapacity>MAX_CAPACITY_VALUE){
            newCapacity=MAX_CAPACITY_VALUE;
        }
        Entry<K,V> []newTable=new Entry[newCapacity];
        for(Entry<K,V> entry:table){
            while(entry!=null){
                //重新计算哈希值
                int i=indexFor(entry.hash,newCapacity);
                Entry<K,V> next= entry.next;
                entry.next=newTable[i];
                newTable[i]=entry;
                entry=next;
            }
        }
        this.capacity=newCapacity;
        this.table=newTable;
    }

    private void createEntry(int hash, K key, V value,int bucketIndex) {
        table[bucketIndex]=new Entry<>(key,value,hash, table[bucketIndex]);
    }

    public int indexFor(int hash,int tableLength){
        return hash&(tableLength-1);
    }
    //hash
    private int hash(K key){
        int h;
        //h>>>16 将高16位和低16位兑换，然后进运算
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    static class Entry<K,V>{
        K key;
        V value;
        int hash;
        Entry<K,V> next;
        public Entry(K key, V value, int hash, Entry<K,V> next){
            this.key=key;
            this.value=value;
            this.hash=hash;
            this.next=next;
        }
    }
}
