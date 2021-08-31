package com.hubu.list;

import java.io.Serializable;

/**
 * 接口
 */
public interface List<T> extends Serializable {
    static final long serialVersionUID = -6849794470754667710L;
    int size();
    boolean isEmpty();
    void clear();
    boolean add(T value);
    T get(int index);
    T set(int index,T newValue);
    boolean add(int index,T value);
    T remove(int index);
}
