package com.hubu.tree.bplustree;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class Page<K, V> {
    boolean isLeaf;
    boolean isRoot;
    //数据放在Entry
    List<Map.Entry<K, V>> entryList;
    List<Page<K, V>> children;
    Page<K, V> parent;
    Page<K, V> prev;
    Page<K, V> next;
    public Page(boolean isRoot, boolean isLeaf) {
        this.isRoot = isRoot;
        this.isLeaf = isLeaf;
        entryList = new ArrayList<>();
        if (!isLeaf) {
            this.children = new ArrayList<>();
        }
    }
}

