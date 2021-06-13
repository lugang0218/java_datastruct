package com.hubu.tree;

public interface Tree <T>{
    void add(T value);
    void clear();
    int size();
    void remove(T value);
    void preOrder();
    void midOrder();
    void postOrder();
    boolean isEmpty();
    void print(T value);
}
