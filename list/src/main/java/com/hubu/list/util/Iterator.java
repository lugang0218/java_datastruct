package com.hubu.list.util;

/**
 * 迭代器接口
 * 用于迭代元素
 */
public interface Iterator<T> {


    /**
     * 判断是否有下一个元素
     * @return
     */
    boolean hasNext();


    /**
     * 获取下一个元素
     * @return
     */
    T next();
}
