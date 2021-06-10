package com.hubu.list.util;

import com.hubu.list.List;

public interface Printer<T> {
    /**
     * 打印方法
     */
    void print(T value);
}
