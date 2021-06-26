package com.hubu.list;

import com.hubu.list.util.InsertPolicy;
import com.hubu.list.util.Printer;

public abstract class AbstractList<T> implements List<T> {

    //插入策略 头插法和尾插法
    private InsertPolicy insertPolicy;

    protected Printer<T> printer;

    public AbstractList(Printer printer){
        this.printer=printer;
    }


    public AbstractList(Printer<T> printer,InsertPolicy insertPolicy){
        this.printer=printer;
        this.insertPolicy=insertPolicy;
    }
    protected  int size;
    @Override
    public int size() {
        return size;
    }

    public void setInsertPolicy(InsertPolicy insertPolicy) {
        this.insertPolicy = insertPolicy;
    }

    public InsertPolicy getInsertPolicy() {
        return insertPolicy;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
}
