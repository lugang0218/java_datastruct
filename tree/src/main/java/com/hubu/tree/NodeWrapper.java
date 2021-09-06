package com.hubu.tree;

/**
 * 包装Node节点的信息
 */
public class NodeWrapper<K,V> {
    /**
     * 包装原来的节点
     */
    BPlusTree.Node<K, V> node;

    private int index;


    public void setIndex(int index) {
        this.index = index;
    }

    public void setNode(BPlusTree.Node<K, V> node) {
        this.node=node;
    }

    public int getIndex() {
        return index;
    }

    public BPlusTree.Node<K, V> getNode() {
        return node;
    }
}
