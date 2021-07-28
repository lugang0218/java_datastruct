package com.hubu.list;


import java.util.Comparator;

public class BTree<K,V>{
    /**
     *
     *
     * B树的阶
     */
    private int order;



    /**
     *
     *
     * 根节点
     */

    private BTreeNode<K,V> root;

    /**
     *
     *
     *
     * 根据key查找
     */
    public V search(K key) {

        return null;
    }

    public void add(K key,V value){


    }

    public void delete(K key){


    }
    private static class BTreeNode<K, V> {
        /**
         * 节点的项，按键非降序存放
         */
        private List<Entry<K,V>> entries;



        /**
         * 内节点的子节点
         */
        private List<BTreeNode<K, V>> children;
        /**
         * 是否为叶子节点
         */
        private boolean leaf;
        /**
         * 键的比较函数对象
         */
        private Comparator<K> kComparator;

        private BTreeNode() {
            entries = new ArrayList<>();
            children = new ArrayList<>();
            leaf = false;
        }
        private static class Entry<K, V> {
            private K key;
            private V value;

            public Entry(K k, V v) {
                this.key = k;
                this.value = v;
            }

            // getter/setter

            @Override
            public String toString() {
                return key + ":" + value;
            }
        }
    }


    public static void main(String[] args) {
        BTree<Integer,Integer> tree=new BTree<>();
        tree.add(12,13);
        tree.add(13,14);
        tree.add(14,15);
    }

}
