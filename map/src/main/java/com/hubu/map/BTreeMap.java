package com.hubu.map;
import com.hubu.tree.bplustree.BPlusTree;
import java.util.Comparator;
public class BTreeMap<K,V> {
    private BPlusTree<K,V> map;




    /////for disk
    private String writeToString; /////nett
    public BTreeMap(int order, Comparator<K> comparator){
        map = new BPlusTree<>(order,comparator);
    }
    public void put(K key,V value){
        map.put(key,value);
    }
    public V get(K key){
        return map.get(key);
    }
    public boolean contains(K key){
        return map.get(key) != null;
    }

    public static void main(String[] args) {
        BTreeMap<String,String> map = new BTreeMap<>(3, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        map.put("hello","world");
        map.put("world","hello");
        System.out.println(map.get("hello"));
        System.out.println(map.get("world"));
        System.out.println(map.get("value"));//not contains
    }
}
