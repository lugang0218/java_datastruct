package com.hubu.tree;
/**
 * 实现方式和B+树有点出入,当数据满之后,并不会立马分裂,
 * 而是新一轮添加的时候检测到满之后在分裂
 */
public class BPlusTree<K extends Comparable<K>, V> {

    // 根节点
    protected Node<K, V> root;

    // 阶数，M值
    protected int order;

    // 叶子节点的链表头
    protected Node<K, V> head;

    // 树高
    protected int height = 0;

    public Node<K, V> getHead() {
        return head;
    }

    public void setHead(Node<K, V> head) {
        this.head = head;
    }

    public Node<K, V> getRoot() {
        return root;
    }

    public void setRoot(Node<K, V> root) {
        this.root = root;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public V get(K key) {
        return root.get(key);
    }

    public V remove(K key) {
        return root.remove(key, this);
    }

    public void insertOrUpdate(K key, V value) {
        root.insertOrUpdate(key, value, this);
    }

    public BPlusTree(int order) {
        if (order < 3) {
            System.out.print("order must be greater than 2");
            System.exit(0);
        }
        this.order = order;
        root = new Node<K, V>(true, true);
        head = root;
    }

    public void printBPlusTree() {
        this.root.printBPlusTree(0);
    }
}