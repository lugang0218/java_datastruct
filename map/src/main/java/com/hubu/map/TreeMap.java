package com.hubu.map;
import com.hubu.tree.RedBlackTree;
import java.util.Comparator;
/**
 *
 * 用红黑树实现TreeMap
 * @param <K>
 * @param <V>
 */
public class TreeMap <K,V>{
    private Comparator<K> comparator;
    private RedBlackTree<K,V> redBlackTree;
    private int size;
    public TreeMap(Comparator<K> comparator){
        this.comparator=comparator;
    }

    public TreeMap(){
        this(null);
    }
    public void  put(K key,V value){
        if(redBlackTree==null){
            redBlackTree=new RedBlackTree<>(comparator);
        }
        redBlackTree.put(key,value);
    }
    public V get(K key){
        return redBlackTree.get(key);
    }
    public V set(K key,V value){
        return redBlackTree.set(key,value);
    }
    public void  remove(K key){
        redBlackTree.remove(key);
    }
    public boolean containsKey(K key){
        return redBlackTree.containsKey(key);
    }
    public boolean containsValue(V value){
        return redBlackTree.containsValue(value);
    }
}
