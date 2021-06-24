package com.hubu.list;
import com.hubu.list.util.Printer;
public class ArrayList <T> extends AbstractList<T> implements List<T>{
    public ArrayList(Printer printer) {
        super(printer);
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean add(T value) {
        return false;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(int index, T newValue) {
        return null;
    }

    @Override
    public boolean add(int index, T value) {
        return false;
    }

    @Override
    public T remove(int index) {
        return null;
    }
}
