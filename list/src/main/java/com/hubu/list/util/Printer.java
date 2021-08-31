package com.hubu.list.util;

import com.hubu.list.List;

import java.io.Serializable;

public interface Printer<T> extends Serializable {

    /**
     * 打印方法
     */
    void print(T value);
}
