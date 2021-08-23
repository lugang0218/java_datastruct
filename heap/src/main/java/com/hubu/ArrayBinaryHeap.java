package com.hubu;
import java.util.Comparator;
public class ArrayBinaryHeap <T> extends AbstractBinaryHeap<T> implements Heap<T>{
    private T elementData[]=null;
    private int capacity;
    private static final int DEFAULT_CAPACITY=10;
    public ArrayBinaryHeap(Comparator<T> comparator) {

        super(comparator);
        this.capacity=DEFAULT_CAPACITY;
    }
    @Override
    public void show() {
        for(int i=0;i<size;i++){
            System.out.println(elementData[i]);
        }
    }
    public ArrayBinaryHeap(Comparator<T> comparator,int capacity){
        super(comparator);
        this.capacity=capacity;

    }
    public void add(T data){
        if(elementData==null){
            elementData=(T[])new Object[this.capacity];
        }
        elementData[size]=data;
        int index=size;
        int compareResult=0;
        while(index!=0){
            //调整
            int parentIndex=index/2;
            T parentData=elementData[parentIndex];
            compareResult=compare(elementData[index],parentData);
            if(compareResult>0){
                T temp=elementData[index];
                elementData[index]=parentData;
                elementData[parentIndex]=temp;
            }
            index=parentIndex;
        }

        size++;
    }
    @Override
    public T get() {
        return elementData!=null&&size>0?elementData[0]:null;
    }
    //删除堆中的最大值
    @Override
    public T remove() {
        if(elementData==null||size==0){
            return null;
        }
        T value=elementData[0];
        return value;
    }
}
