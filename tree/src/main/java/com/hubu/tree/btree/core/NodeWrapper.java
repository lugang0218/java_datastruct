package com.hubu.tree.btree.core;
import com.hubu.tree.btree.Node;
/**
 * 包装Node节点的信息
 */
public class NodeWrapper<K extends Comparable<K>,V> {
    /**
     * 包装原来的节点
     */
    com.hubu.tree.btree.Node<K, V> node;

    private int index;


    public void setIndex(int index) {
        this.index = index;
    }

    public void setNode(com.hubu.tree.btree.Node<K, V> node) {
        this.node=node;
    }

    public int getIndex() {
        return index;
    }

    public Node<K, V> getNode() {
        return node;
    }
}
