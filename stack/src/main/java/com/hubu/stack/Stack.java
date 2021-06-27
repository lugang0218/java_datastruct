package com.hubu.stack;
public interface Stack<T> {
    void push(T value);
    T pop();
    T peek();
    int size();
    boolean isEmpty();
    void clear();
}
