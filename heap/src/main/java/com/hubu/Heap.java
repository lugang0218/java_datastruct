package com.hubu;
public interface Heap<T> {
    /**
     * 判断堆是否为空
     * @return
     */
    boolean isEmpty();
    /**
     * 获取对顶元素
     *
     */
    T get();
    /**
     * 移除堆顶元素
     * @return
     */
    T remove();


    /**
     * 获取堆中元素的个数
     * @return
     */
    int size();


}
