package com.hubu.list;


/**
 * 接口
 */
public interface List<T> {
    int size();
    boolean isEmpty();
    void clear();
    boolean add(T value);
    T get(int index);
    T set(int index,T newValue);
    boolean add(int index,T value);
    T remove(int index);
}
